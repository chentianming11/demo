package com.study.demo.util;

import com.google.common.collect.Lists;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by chen on 2018/1/29.
 */
public interface MyMapper<T> extends Mapper<T> {


    /**
     * 批量插入
     *
     * @param list
     */
    default void insertBatch(List<T> list) {
        Lists.partition(list, 1000).forEach(partitionList -> insertAll(partitionList));
    }


    /**
     * 全量插入
     *
     * @param list
     */
    @InsertProvider(type = MySelectProvider.class, method = "dynamicSQL")
    void insertAll(List<T> list);

}
