package com.lianjia.strman;


import org.junit.Test;
import strman.Strman;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Strman测试类
 * Created by chen on 2018/3/4.
 */
public class TestStrman {

    @Test
    public void test1(){
        // 字符串追加  -- 可变参数字符串
        System.out.println(Strman.append("abc", "chen", "tianming"));
        // 字符串追加 -- 字符串数组
        String[] strings = {"chen","tianming"};
        System.out.println(Strman.appendArray("abc", strings));
        // 取字符串中某个索引处的值
        Optional<String> stringOptional = Strman.at("chentianming", 10);
        stringOptional.ifPresent((s -> System.out.println(s)));
        // 字符串base64编码
        String chentianming = Strman.base64Encode("chentianming");
        System.out.println(chentianming);
        // 字符串base64解码
        System.out.println("base64解码：" + Strman.base64Decode(chentianming));
        // 取出某两个字符包含的内容
        String[] between = Strman.between("[chen]tian[ming]", "[", "]");
        for (String s : between) {
            System.out.println(s);
        }
        // 二进制编码
        String luoxiaoyu = Strman.binEncode("luoxiaoyu");
        System.out.println(luoxiaoyu);
        // 字符串首字母大写，其他全部小写
        System.out.println(Strman.capitalize("chenTianMing"));
        // 字符串转为String数组
        String[] chars = Strman.chars("chentianming");
        for (String aChar : chars) {
            System.out.println(aChar);
        }
        // 统计字符串中每个字符的数量
        System.out.println(Strman.charsCount("chentianmingluoxiaoyuchenyuxialiunanrong"));
        // 按指定数量将字符串拆分成字符串数组
        String[] chop = Strman.chop("chentianming", 2);
        for (String s : chop) {
            System.out.println(s);
        }
        // 用一个空格替换多个连续的空格字符
        System.out.println(Strman.collapseWhitespace("  chen   tian  ming "));
        // 判断是否包含 默认忽略大小写，也可以配置为区分大小写
        System.out.println(Strman.contains("chentiaming", "TIA", true));
        // 判断是否包含所有
        // 判断是否包含任意一个
        System.out.println(Strman.containsAll("chentianming", new String[]{"che", "TI"}));
        // 统计子串数量
        System.out.println(Strman.countSubstr("chentianmingchentianmingchenyuxiachenxuyaw", "chen"));
        // 转换为中划线字符串
        System.out.println(Strman.dasherize("chenTianMing"));
        // 判断是否以某个字符串结尾
        System.out.println(Strman.endsWith("chentianming", "ing"));
        // 保证字符串有指定前缀
        System.out.println(Strman.ensureLeft("chentianming", "liu"));
        // 获取字符串的前n个字符部分
        System.out.println(Strman.first("chentianming", 2));
        // String的format 支持任意类型
        System.out.println(String.format("woshi%s,nianling%d, shengri%s", "chen", 25, LocalDate.now()));
        // Strman的format执行字符串
        System.out.println(Strman.format("woshi{0},nianling{1},shengri{2}", "chentianming","25","09-09"));
        // 返回第一个字符
        Strman.head("ddd");
        // 字符串人名化
        System.out.println(Strman.humanize("chen_tian_ming"));

        
    }
}
