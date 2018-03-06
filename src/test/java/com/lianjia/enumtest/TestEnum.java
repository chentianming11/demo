package com.lianjia.enumtest;

import com.lianjia.entity.User;
import com.lianjia.util.EnumUtils;
import org.junit.Test;

/**
 * Created by chen on 2018/3/5.
 */
public class TestEnum {

    @Test
    public void test1(){
        String 交易员 = EnumUtils.name(User.QuestionEnum.class, 2);
        System.out.println(交易员);

        Object o = EnumUtils.value(User.QuestionEnum.class, 2);
        System.out.println(o.getClass());
        User.QuestionEnum questionEnum = (User.QuestionEnum) o;
        System.out.println(questionEnum.question);
    }
}
