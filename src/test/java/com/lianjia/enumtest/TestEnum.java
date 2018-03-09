package com.lianjia.enumtest;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimaps;
import com.lianjia.entity.User;
import org.apache.commons.lang3.EnumUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;


/**
 * Created by chen on 2018/3/5.
 */
public class TestEnum {

    @Test
    public void test1(){

    }

    @Test
    public void test2() {

        List<User.QuestionEnum> enumList = EnumUtils.getEnumList(User.QuestionEnum.class);

        ArrayListMultimap<Object, Object> multimap = ArrayListMultimap.create();

        enumList.forEach(questionEnum -> {
            try {
                Object id = questionEnum.getClass().getField("id").get(questionEnum);
                System.out.println(id);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        System.out.println(JSON.toJSONString(multimap.asMap()));
    }

    /**
     * 将Enum转换成MultiMap形式
     * Enum的name作为key，Enum的自定义属性作为值
     *
     * @throws NoSuchFieldException
     */
    @Test
    public void test3() throws NoSuchFieldException {
        User.QuestionEnum[] enums = User.QuestionEnum.values();
        Field[] fields = User.QuestionEnum.class.getFields();
        List<Map<String, Object>> list = new ArrayList<>();
        for (User.QuestionEnum anEnum : enums) {
            Map<String, Object> map = new HashMap<>();
            for (int i = enums.length; i < fields.length; i++) {
                try {
                    map.put("name", anEnum.name());
                    map.put(fields[i].getName(), fields[i].get(anEnum));

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            list.add(map);
        }

        System.out.println(list);

    }
}
