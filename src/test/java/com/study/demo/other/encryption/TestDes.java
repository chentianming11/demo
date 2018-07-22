package com.study.demo.other.encryption;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * DES加密
 * Created by chenTianMing on 2018/6/2.
 */
public class TestDes {

    private static String src = "chentian";

    public static void main(String[] args) throws Exception {
        jdkDES();
    }

    public static void jdkDES() throws Exception {

        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(56);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] secretKeyEncoded = secretKey.getEncoded();

            // key的转换
            DESKeySpec desKeySpec = new DESKeySpec(secretKeyEncoded);
            SecretKeyFactory des = SecretKeyFactory.getInstance("DES");
            Key key = des.generateSecret(desKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(src.getBytes());

            System.out.println(Hex.encodeHexString(bytes));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
