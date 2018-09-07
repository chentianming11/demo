package com.study.demo.util;

import com.study.demo.entity.enums.CmsArticleStatusEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 *
 * @author 陈添明
 */
public class EnumUtils {

    /**
     * 获取枚举名称和某个枚举值的映射Map
     * 转化枚举为一个BiMap，key为枚举的name， value为指定属性的值；要求指定属性值必须唯一
     * 例如：未评价(0) 已评价(1) -> {未评价=0, 已评价=1}
     *
     * @param enumClass
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> Map<String, Object> toNameValueMap(Class<E> enumClass, String filedName) {

        Map<String, Object> map = new HashMap<>();
        try {
            Field field = enumClass.getField(filedName);
            // 获取所有枚举常量
            E[] enumConstants = enumClass.getEnumConstants();
            for (E enumConstant : enumConstants) {
                map.putIfAbsent(enumConstant.name(), field.get(enumConstant));
            }
        }catch (NoSuchFieldException e) {
            throw new RuntimeException("枚举中没有" + filedName + "这个字段", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("枚举参数访问异常", e);
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
    public static <E extends Enum<E>> Map<Object, String> toValueNameMap(Class<E> enumClass, String filedName) {

        Map<Object, String> map = new HashMap<>();
        try {
            Field field = enumClass.getField(filedName);
            // 获取所有枚举常量
            E[] enumConstants = enumClass.getEnumConstants();
            for (E enumConstant : enumConstants) {
                map.putIfAbsent(field.get(enumConstant), enumConstant.name());
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("枚举中没有" + filedName + "这个字段", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("枚举参数访问异常", e);
        }
        return map;
    }


    /**
     * 根据指定枚举字段的code值，获取枚举的name
     *
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> String getName(Class<E> enumClass, Integer code, String filedName) {
        String name = null;
        try {
            Field field = enumClass.getField(filedName);
            E[] enumConstants = enumClass.getEnumConstants();
            for (E enumConstant : enumConstants) {
                if (Objects.equals(code, field.get(enumConstant))) {
                    name = enumConstant.name();
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("枚举中没有" + filedName + "这个字段", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("枚举参数访问异常", e);
        }
        return name;
    }

    /**
     * 根据枚举value字段的code值，获取枚举的name
     * @param enumClass
     * @param code
     * @param <E>
     * @return
     */
    public static <E extends Enum<E>> String getName(Class<E> enumClass, Integer code) {
        return EnumUtils.getName(enumClass, code, "value");
    }

    public static void main(String[] args) {
        String name = EnumUtils.getName(CmsArticleStatusEnum.class, 1000900001);
        System.out.println(name);
    }


}
