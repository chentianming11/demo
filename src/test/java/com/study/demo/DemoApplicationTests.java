package com.study.demo;

import com.study.demo.entity.Emp;
import com.study.demo.entity.EmpQuery;
import com.study.demo.entity.blog.BlogArticle;
import com.study.demo.entity.blog.BlogCollection;
import com.study.demo.entity.view.EmpView;
import com.study.demo.mapper.EmpMapper;
import com.study.demo.mapper.blog.BlogArticleMapper;
import com.study.demo.mapper.blog.BlogCollectionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    EmpMapper empMapper;
    @Autowired
    Jedis jedis;
    @Autowired
    BlogCollectionMapper blogCollectionMapper;

    @Autowired
    BlogArticleMapper blogArticleMapper;



    @Test
    public void testJedis() {
        jedis.set("store", "1000");
    }


    @Test
    public void testSelectAll() {
        System.out.println("启动完成");
    }


    /**
     * 测试java8中将list转为map
     */
    @Test
    public void test4() {
        EmpQuery empQuery = new EmpQuery();
        empQuery.setPageNum(1);
        empQuery.setPageSize(50);
        List<EmpView> empViewList = empMapper.findEmpJoinDeptAndJob(empQuery);
        Map<Long, String> map = empViewList.stream().collect(Collectors.toMap(Emp::getId, Emp::getName));
        System.out.println(map);
    }


    /**
     * 10w条  批量插入   9156  ms
     * 10w条  单个插入   161320  ms
     * 测试通用Mapper的批量插入
     */
    @Test
    public void test6() throws Exception {

        long start = System.currentTimeMillis();

//        List<BlogCollection> list = new ArrayList<>();
//
//
//        for (int i = 0; i < 100000; i++) {
//
//            list.add(BlogCollection.builder().name("test")
//                    .description("desc").userId(1001).build());
//        }
//
//        blogCollectionMapper.insertBatch(list);

        for (int i = 0; i < 100000; i++) {

            BlogCollection build = BlogCollection.builder().name("test")
                    .description("desc").userId(1001).build();
            blogCollectionMapper.insertSelective(build);
        }

        long end = System.currentTimeMillis();

        System.out.println("执行时间：" + (end-start) + "  ms");
    }


    @Test
    public void test7(){
        List<BlogArticle> list = new ArrayList<>();

        list.add(new BlogArticle(null, 4, "title", "content", new Date(), 1));


        blogArticleMapper.insertAll(list);
    }

}
