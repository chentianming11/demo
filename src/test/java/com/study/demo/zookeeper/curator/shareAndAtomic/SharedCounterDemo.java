package com.study.demo.zookeeper.curator.shareAndAtomic;

import com.beust.jcommander.internal.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SharedCounterDemo implements SharedCountListener {

    private static final int QTY = 5;
    private static final String PATH = "/examples/counter";

    /**
     * 在这个例子中，我们使用baseCount来监听计数值(addListener方法来添加SharedCountListener )。
     * 任意的SharedCount， 只要使用相同的path，都可以得到这个计数值。
     * 然后我们使用5个线程为计数值增加一个10以内的随机数。
     * 相同的path的SharedCount对计数值进行更改，将会回调给baseCount的SharedCountListener。
     * @param args
     * @throws IOException
     * @throws Exception
     */
    public static void main(String[] args) throws IOException, Exception {
        final Random rand = new Random();
        SharedCounterDemo example = new SharedCounterDemo();
        try (TestingServer server = new TestingServer()) {
            CuratorFramework client = CuratorFrameworkFactory.newClient(server.getConnectString(), new ExponentialBackoffRetry(1000, 3));
            client.start();

            // 创建一个shareCount
            SharedCount baseCount = new SharedCount(client, PATH, 0);
            baseCount.addListener(example);
            baseCount.start();

            List<SharedCount> examples = Lists.newArrayList();
            ExecutorService service = Executors.newFixedThreadPool(QTY);
            for (int i = 0; i < QTY; ++i) {
                final SharedCount count = new SharedCount(client, PATH, 0);
                examples.add(count);
                Callable<Void> task = () -> {
                    count.start();
                    Thread.sleep(rand.nextInt(10000));
                    System.out.println("Increment:" + count.trySetCount(count.getVersionedValue(), count.getCount() + rand.nextInt(10)));
                    return null;
                };
                service.submit(task);
            }

            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);

            for (int i = 0; i < QTY; ++i) {
                examples.get(i).close();
            }
            baseCount.close();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Override
    public void stateChanged(CuratorFramework arg0, ConnectionState arg1) {
        System.out.println("State changed: " + arg1.toString());
    }

    @Override
    public void countHasChanged(SharedCountReader sharedCount, int newCount) throws Exception {
        System.out.println("Counter's value is changed to " + newCount);
    }
}
