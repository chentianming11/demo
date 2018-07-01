package com.lianjia.util;

import com.google.common.primitives.Chars;
import strman.Strman;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author chenTianMing
 * @date 2018/3/17
 */
public class ShortUrlUtils {

    // 32位
    public static final List<Character> characters = Chars.asList(
            'D', 'L', '2', 'Q', '3', 'U', 'a', 'p', '5', 'z', 's', 'I', 'V', 'v', '1', 't', '8', 'W', 'Z', 'A', 'K', 'i', '6', 'l', 'q', 'x', 'B', 'E', 'J', 'h', 'X', 'u');

    public static final int eo = 11293573;

    /**
     * int最大为2的24次方-1
     * @param num
     * @return
     */
    public static String encode(int num) {
        // 异或
        String toBinaryString = Integer.toBinaryString(num ^ eo);
        System.out.println(num ^ eo);
        // 前置补0 凑足24位
        String binaryString = Strman.leftPad(toBinaryString, "0", 24);
        System.out.println(binaryString);
        // 3位一组， 分成8段
        StringBuilder stringBuilder = new StringBuilder();
        String[] chop = Strman.chop(binaryString, 3);
        for (int i = 0; i < chop.length; i++) {
            // 前置补2位随机01
            stringBuilder.append(characters.get(Integer.valueOf((getRandom() + chop[i]), 2)));
        }
        return stringBuilder.toString();
    }

    public static int decode(String str) {
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for (char aChar : chars) {
            int i = characters.indexOf(aChar);
            String s = Strman.leftPad(Integer.toBinaryString(i), "0", 5);
            stringBuilder.append(s.substring(2));
        }
        Integer integer = Integer.valueOf(stringBuilder.toString(), 2);
        return integer ^ eo;
    }


    private static String getRandom() {
        List<String> strings = Arrays.asList("00", "10", "01", "11");
        Double v = Math.random() * 4;
        int i = v.intValue();
        return strings.get(i);
    }

    private static String getOneRandom() {
        List<String> strings = Arrays.asList("0", "1");
        Double v = Math.random() * 2;
        int i = v.intValue();
        return strings.get(i);
    }



    public static void main(String[] args) {
        String encode = encode(1);
        System.out.println(encode);

    }
}
