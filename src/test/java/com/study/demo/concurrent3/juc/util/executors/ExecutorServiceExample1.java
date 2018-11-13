package com.study.demo.concurrent3.juc.util.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈添明
 * @date 2018/11/13
 */
public class ExecutorServiceExample1 {

    public static void main(String[] args) {
//        isShutdown();

        isTerminrated();
    }


    /**
     * 问题：
     * 1. 当调用shutdown()之后，可以再去执行runnable吗？
     *      不能，RejectedExecutionException
     */
    private static void isShutdown() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // 是否执行关闭
        System.out.println(executorService.isShutdown());
        // 关闭线程池，不阻塞
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        executorService.execute(() -> {
            System.out.println("hahahah");
        });

    }


    private static void isTerminrated(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        // 是否关闭  关闭是一个动作
        System.out.println("是否关闭：" + executorService.isShutdown());
        // 是否已经终结  终结是一个状态
        System.out.println("是否已经终结：" + executorService.isTerminated());

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
        System.out.println("是否正在终结：" + threadPoolExecutor.isTerminating());

    }

}
