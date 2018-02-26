package com.lianjia.guava.collectionutils;

import com.google.common.collect.Collections2;
import com.lianjia.entity.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chen on 2018/2/25.
 */
public class TestCollections2 {

    @Test
    public void test1(){

        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("张三",20,9000));
        employees.add(new Employee("李四",24,10000));
        employees.add(new Employee("王五",21,8000));
        employees.add(new Employee("赵六",28,13000));
        employees.add(new Employee("田七",32,17000));

        // filter过滤  过滤掉年龄大于25岁的员工
        Collection<Employee> collection = Collections2.filter(employees, (employee -> employee.getAge() > 25));
        System.out.println(collection);

        // permutation置换
        Collection<List<Employee>> lists = Collections2.permutations(employees);
        System.out.println(lists);

    }
}
