package com.study.demo.classloader.c3;

/**
 * @author 陈添明
 * @date 2018/10/13
 */
public class MyObject {

    static {
        System.out.println("MyObject static ...");
    }

    public String hello(){
        return "hello world ";
    }
}
