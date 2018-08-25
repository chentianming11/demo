package com.study.demo.other.strman;

import org.junit.Test;
import strman.Strman;

/**
 * @author 陈添明
 * @date 2018/8/24
 */
public class StrmanTest {

    @Test
    public void  test(){

        // 字符串转成驼峰  isTest
        String is_emp_test = Strman.toCamelCase("IS_test".toLowerCase());
        System.out.println(is_emp_test);

        // 字符串转羊肉串（中杆）  is-test
        String isTest = Strman.toKebabCase("isTest");
        System.out.println(isTest);

        // 字符串转蛇型（下划线） is_test
        String s = Strman.toSnakeCase("isTest");
        System.out.println(s);

        // 字符串转大驼峰式  IsTest
        String studlyCase = Strman.toStudlyCase("is_test");
        System.out.println(studlyCase);

    }
}
