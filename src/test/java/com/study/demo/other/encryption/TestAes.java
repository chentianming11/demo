package com.study.demo.other.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by chenTianMing on 2018/6/2.
 */
public class TestAes {

    public static final String src = "123";

    public static void main(String[] args) {
        jdkAES();
    }

    public static void jdkAES() {
        try {
            // 生成key
            KeyGenerator aes = KeyGenerator.getInstance("AES");
            aes.init(128);
            SecretKey secretKey = aes.generateKey();

            byte[] encodedKey = secretKey.getEncoded();

            // key的转换
            Key key = new SecretKeySpec(encodedKey, "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(src.getBytes());
            System.out.println(Base64.encodeBase64String(bytes));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes1 = cipher.doFinal(bytes);
            System.out.println(new String(bytes1));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
