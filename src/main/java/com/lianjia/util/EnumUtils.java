package com.lianjia.util;

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
}