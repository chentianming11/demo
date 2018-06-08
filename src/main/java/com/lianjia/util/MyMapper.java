package com.lianjia.util;

import com.google.common.collect.Lists;
import org.apache.ibatis.annotations.InsertProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by chen on 2018/1/29.
 */
public interface MyMapper<T> extends Mapper<T> {


    /**
     *  忽略id列批量插入
     * @param list
     */
    default void insertBatchSkipId(List<T> list){
        Lists.partition(list, 50).forEach(partitionList -> insertAllSkipId(partitionList));
    }

    /**
     *  不忽略id列批量插入
     * @param list
     */
    default void insertBatchNotSkipId(List<T> list){
        Lists.partition(list, 50).forEach(partitionList -> insertAllNotSkipId(partitionList));
    }

    /**
     * 忽略id列全量插入
     * @param list
     */
    @InsertProvider(type = MySelectProvider.class, method = "dynamicSQL")
     void insertAllSkipId(List<T> list);

    /**
     * 不忽略id列全量插入
     * @param list
     */
    @InsertProvider(type = MySelectProvider.class, method = "dynamicSQL")
    void insertAllNotSkipId(List<T> list);

}
