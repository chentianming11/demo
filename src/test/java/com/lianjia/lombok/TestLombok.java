package com.lianjia.lombok;

import com.lianjia.entity.Employee;
import com.lianjia.entity.LombokEntity;
import org.junit.Test;
import strman.Strman;

import java.util.ArrayList;

/**
 * Created by chen on 2018/3/22.
 */
public class TestLombok {


    @Test
    public void test1(){

        LombokEntity entity = LombokEntity.builder()
                .age(11)
                .name("chen")
                .build();
        ArrayList<Employee> employees = new ArrayList<>();
        employees.add(new Employee("张三",20,9000));
        employees.add(new Employee("张三",20,9000));
        entity.setEmployees(employees);

//        entity.clear();
//        entity.add(new Employee("xxx", 12, 120000000));
//        entity.remove(0);

        entity.getEmployees().forEach(employee -> System.out.println(employee));
        String aaaa = Strman.encode("aaaa", 10, 2);
        System.out.println(aaaa);
        System.out.println(entity);



    }
}
