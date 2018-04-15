package com.lianjia.apache;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * StringUtils测试类
 * Created by chen on 2018/3/12.
 */
public class TestStringUtils {
    @Test
    public void test1(){
        // 空格
        String space = StringUtils.SPACE;
        // 判断字符串是否为空白
        System.out.println(StringUtils.isBlank("    "));
        // 判断字符串是否全部为小写
        System.out.println(StringUtils.isAllLowerCase("abc"));
        // 检查CharSequence是否只包含Unicode字母
        System.out.println(StringUtils.isAlpha("iii"));
        // 检查CharSequence是否只包含Unicode字母或数字
        System.out.println(StringUtils.isAlphanumeric("ddd"));

    }
}
