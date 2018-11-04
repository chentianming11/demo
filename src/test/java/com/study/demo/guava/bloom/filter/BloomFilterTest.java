package com.study.demo.guava.bloom.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.study.demo.classloader.c1.lang.String;
import org.junit.Test;

/**
 * @author 陈添明
 * @date 2018/11/1
 */
public class BloomFilterTest {

    @Test
    public void test1(){
        BloomFilter<String> stringBloomFilter = BloomFilter.create((Funnel<String>) (from, into) -> {

        }, 1024 * 1024 * 32);
    }
}
