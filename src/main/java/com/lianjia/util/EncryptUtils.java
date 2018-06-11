package com.lianjia.util;

import lombok.SneakyThrows;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by chenTianMing on 2018/6/3.
 */
public class EncryptUtils {


    public static AES getAES(String keyStr){
        return new AES(keyStr);
    }

    public static class AES {
        private SecretKeySpec secretKeySpec; // 密钥对象
        private Cipher encryptCipher; // 加密的Cipher
        private Cipher decryptCipher; // 解密的Cipher
        @SneakyThrows
        private AES(String keyStr) {
            //指定加密算法的名称为AES,
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            //初始化密钥生成器，指定密钥的长度(单位:bit),
            //SecureRandom是生成安全随机数序列
            SecureRandom secureRandom = new SecureRandom(keyStr.getBytes());
            keyGenerator.init(128, secureRandom);
            //生成原始对称密钥
            SecretKey secretKey = keyGenerator.generateKey();
            //返回编码格式的密钥
            byte[] enCodeFormat = secretKey.getEncoded();
            //根据字节数组生成AES专用密钥
            secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            //根据指定算法生成密码器
            encryptCipher = Cipher.getInstance("AES");
            decryptCipher = Cipher.getInstance("AES");
            //初始化密码器，
            // 第一个参数为密码的操作模式：加密(ENCRYPT_MODE),解密(DECRYPT_MODE)
            //第二个参数为AES密钥
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }

        /**
         * 明文加密
         * @param content 加密前的原内容
         * @return
         */
        public String encrypt(String content) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
            //获取加密内容的字节数组
            byte[] contentBytes = content.getBytes("utf-8");
            byte[] aesBytes = encryptCipher.doFinal(contentBytes);
            //为了避免解密时数据丢失，将加密后的内容进行Base64编码后再返回
            String aesContent = Base64.getEncoder().encodeToString(aesBytes);
            //将加密后的密文转为字符串返回
            return aesContent;
        }

        /**
         * 密文解密
         * @param content 加密后的内容
         * @return
         */
        public String decrypt(String content) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
            //先将密文进行Base64解码
            byte[] aesBytes = Base64.getDecoder().decode(content);
            //将密文进行解密
            byte[] contentBytes = decryptCipher.doFinal(aesBytes);
            //将解密后的内容转成字符串并返回
            return new String(contentBytes, "utf-8");
        }
    }
}
