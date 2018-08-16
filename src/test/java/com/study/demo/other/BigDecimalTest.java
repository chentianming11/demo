package com.study.demo.other;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author 陈添明
 * @date 2018/8/14
 */
public class BigDecimalTest {

    @Test
    public void test1(){
        BigDecimal total = new BigDecimal(0);
        BigDecimal a = new BigDecimal(1.0);
        BigDecimal b = new BigDecimal(5.0);
        BigDecimal c = new BigDecimal(3.0);

        total = total.add(a);
        total.add(b);
        total.add(c);

        System.out.println(total);
    }

    @Test
    public void test2(){
        String randomNumeric = RandomStringUtils.randomNumeric(4);

        System.out.println(randomNumeric);


        String random = RandomStringUtils.randomAlphabetic(8);
        System.out.println(random);
    }
}
