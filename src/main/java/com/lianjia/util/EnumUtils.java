package com.lianjia.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumUtils {
    public EnumUtils() {
    }

    public static String name(Class clazz, Object key) {
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var2 = clazz.getEnumConstants();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object t = var2[var4];

                try {
                    Field[] var6 = t.getClass().getFields();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field = var6[var8];
                        if((ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) && Is.equals(field.getType().cast(field.get(t)), key)) {
                            return t.toString();
                        }
                    }
                } catch (Exception var10) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return null;
        }
    }

    public static Object value(Class clazz, Object key) {
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var2 = clazz.getEnumConstants();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object t = var2[var4];

                try {
                    Field[] var6 = t.getClass().getFields();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field = var6[var8];
                        if((ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) && Is.equals(field.getType().cast(field.get(t)), key)) {
                            return t;
                        }
                    }
                } catch (Exception var10) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return null;
        }
    }

    public static Map<Object, Object> toMap(Class clazz) {
        Map<Object, Object> result = new HashMap();
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var2 = clazz.getEnumConstants();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object t = var2[var4];

                try {
                    Field[] var6 = t.getClass().getFields();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field = var6[var8];
                        if(ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) {
                            String value = t.toString();
                            if(value.contains("_")) {
                                value = value.replace('_', '(') + ')';
                            }

                            result.put(field.get(t), value);
                        }
                    }
                } catch (Exception var11) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return result;
        }
    }

    public static Map<Object, Object> toMap(Class clazz, Object excludeEnum) {
        Map<Object, Object> result = new HashMap();
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var3 = clazz.getEnumConstants();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object t = var3[var5];

                try {
                    Field[] var7 = t.getClass().getFields();
                    int var8 = var7.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        Field field = var7[var9];
                        if((ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) && !Is.equals(t, excludeEnum)) {
                            String value = t.toString();
                            if(value.contains("_")) {
                                value = value.replace('_', '(') + ')';
                            }

                            result.put(field.get(t), value);
                        }
                    }
                } catch (Exception var12) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return result;
        }
    }

    public static List<Map<String, Object>> toList(Class clazz) {
        List<Map<String, Object>> result = new ArrayList();
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var2 = clazz.getEnumConstants();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object t = var2[var4];

                try {
                    Field[] var6 = t.getClass().getFields();
                    int var7 = var6.length;

                    for(int var8 = 0; var8 < var7; ++var8) {
                        Field field = var6[var8];
                        if(ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) {
                            Map<String, Object> item = new HashMap();
                            String value = t.toString();
                            if(value.contains("_")) {
                                value = value.replace('_', '(') + ')';
                            }

                            item.put("key", field.get(t).toString());
                            item.put("value", value);
                            result.add(item);
                        }
                    }
                } catch (Exception var12) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return result;
        }
    }

    public static List<Map<String, Object>> toList(Class clazz, Object excludeEnum) {
        List<Map<String, Object>> result = new ArrayList();
        if(!clazz.isEnum()) {
            throw new IllegalArgumentException("Class[" + clazz + "]不是枚举类型");
        } else {
            Object[] var3 = clazz.getEnumConstants();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object t = var3[var5];

                try {
                    Field[] var7 = t.getClass().getFields();
                    int var8 = var7.length;

                    for(int var9 = 0; var9 < var8; ++var9) {
                        Field field = var7[var9];
                        if(ClassUtils.isPrimitiveOrWrapper(field.getType()) || Is.equals(field.getType().getTypeName(), "java.lang.String") || Is.equals(field.getType().getTypeName(), "java.util.Date")) {
                            Map<String, Object> item = new HashMap();
                            if(!Is.equals(t, excludeEnum)) {
                                String value = t.toString();
                                if(value.contains("_")) {
                                    value = value.replace('_', '(') + ')';
                                }

                                item.put("key", field.get(t).toString());
                                item.put("value", value);
                                result.add(item);
                            }
                        }
                    }
                } catch (Exception var13) {
                    throw new IllegalArgumentException("枚举必须要有值!");
                }
            }

            return result;
        }


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