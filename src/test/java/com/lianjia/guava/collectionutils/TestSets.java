package com.lianjia.guava.collectionutils;


import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chen on 2018/2/25.
 */
public class TestSets {

    @Test
    public void test(){
        HashSet<String> set = Sets.newHashSet("a", "b", "c","d","e");
        // 从5个元素中  取4个做随机组合
        Set<Set<String>> combinations = Sets.combinations(set, 4);
        combinations.forEach(e -> System.out.println(e));
        HashSet<String> set2 = Sets.newHashSet("a", "b", "f","g","h");
        // 取2个set集合的交集
        System.out.println("交集" + Sets.intersection(set, set2));
        // 取并集
        System.out.println("并集" + Sets.union(set, set2));
        // set1中去除交集的部分
        System.out.println("set1中去除交集的部分" + Sets.difference(set, set2));
        System.out.println("并集中去除交集的部分" + Sets.symmetricDifference(set, set2));

        System.out.println("------powerSet-------");
        // set集合中的所有子集
        Set<Set<String>> powerSet = Sets.powerSet(set);



    }
}
