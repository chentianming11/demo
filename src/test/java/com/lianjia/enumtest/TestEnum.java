package com.lianjia.enumtest;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.*;
import com.lianjia.entity.User;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;
import sun.plugin.com.AmbientProperty;

import java.lang.reflect.Field;
import java.util.*;


/**
 * Created by chen on 2018/3/5.
 */
public class TestEnum {

    @Test
    public void test1() throws IllegalAccessException, NoSuchFieldException {
        BiMap<String, Object> biMap = getBiMap(User.QuestionEnum.class, "id");
        System.out.println(biMap);


    }

    /**
     * 将枚举转成MultiMap，key为枚举的name，value为枚举各个属性的值
     * 例如: 未评价(0,"aaa") 已评价(1,"bbb")  -> {未评价=[0, "aaa"], 已评价=[1, "bbb"]}
     * @param enumClass
     * @param <E>
     * @return
     * @throws IllegalAccessException
     */
    private static <E extends Enum<E>> Multimap<String, Object> getMultiMap(Class<E> enumClass) throws IllegalAccessException {

        Multimap<String, Object> multimap = ArrayListMultimap.create();
        // 获取枚举的所有字段（枚举常量值+枚举属性）
        Field[] fields = enumClass.getFields();
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            for (int i = enumConstants.length; i < fields.length; i++) {
                multimap.put(enumConstant.name(), fields[i].get(enumConstant));
            }
        }
        return multimap;
    }

    /**
     * 转化枚举为一个BiMap，key为枚举的name， value为指定属性的值；要求指定属性值必须唯一
     * 例如：未评价(0) 已评价(1) -> {未评价=0, 已评价=1}
     * @param enumClass
     * @param <E>
     * @return
     * @throws IllegalAccessException
     */
    private static <E extends Enum<E>> BiMap<String, Object> getBiMap(Class<E> enumClass, String filedName) throws IllegalAccessException, NoSuchFieldException {

        BiMap<String, Object> biMap = HashBiMap.create();
        // 获取枚举的所有字段（枚举常量值+枚举属性）
        Field field = enumClass.getField(filedName);
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
           biMap.putIfAbsent(enumConstant.name(), field.get(enumConstant));
        }
        return biMap;
    }

    /**
     * 转换枚举为List
     * 例如：未评价(0,"aaa") 已评价(1,"bbb")
     * -> [{name=未评价, status=0, extend="aaa"},{name=已评价, status=1, extend="bbb"}]
     * @param enumClass
     * @param <E>
     * @return
     */
    private static <E extends Enum<E>> List<Map<String, Object>> getList(Class<E> enumClass) throws IllegalAccessException {

        List<Map<String, Object>> list = new ArrayList<>();
        // 获取枚举的所有字段（枚举常量值+枚举属性）
        Field[] fields = enumClass.getFields();
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            Map<String, Object> map = new HashMap<>();
            for (int i = enumConstants.length; i < fields.length; i++) {
                map.put("name", enumConstant.name());
                map.put(fields[i].getName(), fields[i].get(enumConstant));
            }
            list.add(map);
        }
        return list;
    }

}
