package com.study.demo;

import com.study.demo.entity.Emp;
import com.study.demo.entity.EmpQuery;
import com.study.demo.entity.view.EmpView;
import com.study.demo.mapper.DeptMapper;
import com.study.demo.mapper.EmpMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

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
    private DeptMapper deptMapper;

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
     * 1w条  批量插入  5880ms
     * 1w条  全量插入  1538ms
     * 测试通用Mapper的批量插入
     */
    @Test
    public void test6() throws Exception {
//		List<Emp> emps = new ArrayList<>();
//		for (int i = 0; i < 10000; i++) {
//			emps.add(Emp.builder().name("111").status(1).build());
//		}
//		long start = System.currentTimeMillis();
//		empMapper.insertBatchSkipId(emps);
//		long end = System.currentTimeMillis();
//		System.out.println("耗时：" + (end - start) + "ms");
//		Emp emp = Emp.builder().name("111").status(1).build();
//		int i = empMapper.insertSelective(emp);
//		System.out.println(i + "    " + emp.getId());

    }

}
