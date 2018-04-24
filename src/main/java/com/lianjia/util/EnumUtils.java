package com.lianjia.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Multimap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chen on 2018/3/11.
 */
public class EnumUtils {

    /**
     * 例如：未评价(0) 已评价(1) -> {0=已评价, 1=未评价}
     * @param enumClass
     * @param filedName
     * @param <E>
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static <E extends Enum<E>> BiMap<Integer, String> getCodeAndName(Class<E> enumClass, String filedName) throws Exception {

       BiMap<Integer, String> map = HashBiMap.create();
        Field field = enumClass.getField(filedName);
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            map.putIfAbsent((Integer) field.get(enumConstant),enumConstant.name());
        }
        return map;
    }

    /**
     * 转换枚举为List
     * 例如：未评价(0,"aaa") 已评价(1,"bbb")
     * -> [{name=未评价, status=0, extend="aaa"},{name=已评价, status=1, extend="bbb"}]
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> List<Map<String, Object>> getList(Class<E> enumClass) throws IllegalAccessException {

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
