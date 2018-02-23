package com.lianjia.java8.stream;

import com.lianjia.entity.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 一、Stream的三个步骤：
 * 1. 创建Stream
 * 2. 中间操作
 * 3. 终止操作
 * Created by chen on 2018/2/13.
 */
public class TestStream1 {

    // 创建Stream
    @Test
    public  void test1(){
        // 1. 可以通过Collection系列的集合的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); // 方式1

        //2. 通过Arrays中的静态方法可以获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);

        // 3. 通过Stream类中的静态方法of()
        Stream<String> stream2 = Stream.of("aa", "bb", "c");

        // 4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2);
        // 生成
        Stream<Double> stream4 = Stream.generate(() -> Math.random());

    }
}
