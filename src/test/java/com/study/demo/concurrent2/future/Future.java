package com.study.demo.concurrent2.future;

/**
 * @author 陈添明
 * @date 2018/9/22
 */
public interface Future<T> {

    T get() throws InterruptedException;
}
