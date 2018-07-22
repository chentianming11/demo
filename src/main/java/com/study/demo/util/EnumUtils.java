package com.study.demo.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 枚举工具类
 *
 * @author chenTianMing
 */
public class EnumUtils {

    /**
     * 获取枚举名称和某个枚举值的映射BiMap
     * 转化枚举为一个BiMap，key为枚举的name， value为指定属性的值；要求指定属性值必须唯一
     * 例如：未评价(0) 已评价(1) -> {未评价=0, 已评价=1}
     *
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> Map<String, Object> getNameToValueMapping(Class<E> enumClass, String filedName) throws NoSuchFieldException, IllegalAccessException {

        Map<String, Object> map = new HashMap<>();
        Field field = enumClass.getField(filedName);
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            map.putIfAbsent(enumConstant.name(), field.get(enumConstant));
        }
        return map;
    }

    /**
     * 获取某个枚举值和枚举名称映射Map
     * 例如：未评价(0) 已评价(1) -> {0=已评价, 1=未评价}
     *
     * @param enumClass
     * @param filedName
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> Map<Integer, String> getValueToNameMapping(Class<E> enumClass, String filedName) throws NoSuchFieldException, IllegalAccessException {

        Map<Integer, String> map = new HashMap<>();
        Field field = enumClass.getField(filedName);
        // 获取所有枚举常量
        E[] enumConstants = enumClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            map.putIfAbsent((Integer) field.get(enumConstant), enumConstant.name());
        }
        return map;
    }

}
