package com.study.demo.util;


import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

/**
 * Created by chenTianMing on 2018/6/3.
 */
public class EncryptUtils {

    private static final String DEFAULT_AES_KEY_STR = "AxhjMVebmGiprTkbtjteeFhoipDmBhIF";

    private static final AES aes = AES.getInstance(DEFAULT_AES_KEY_STR);

    public static class AES {
        public static final String UTF_8 = "utf-8";
        /**
         * 密钥对象
         */
        private SecretKeySpec secretKeySpec;
        /**
         * 加密的Cipher
         */
        private Cipher encryptCipher;
        /**
         * 解密的Cipher
         */
        private Cipher decryptCipher;

        private AES(String keyStr) {

            try{
                //指定加密算法的名称为AES,
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                //初始化密钥生成器，指定密钥的长度(单位:bit),
                //SecureRandom是生成安全随机数序列
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
                secureRandom.setSeed(keyStr.getBytes());
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
            }catch (Exception e){
                throw new RuntimeException("初始化AES加密器异常", e);
            }

        }

        public static AES getInstance(String keyStr){
            return new AES(keyStr);
        }


        /**
         * 明文加密
         *
         * @param content 加密前的原内容
         * @return
         */
        public String encrypt(String content) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
            //获取加密内容的字节数组
            byte[] contentBytes = content.getBytes("utf-8");
            byte[] aesBytes = encryptCipher.doFinal(contentBytes);
            //为了避免解密时数据丢失，转成16进制
            return HexStringUtils.bytesToHexString(aesBytes);

        }


        /**
         * 密文解密
         *
         * @param content 加密后的内容
         * @return
         */
        public String decrypt(String content) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
            byte[] aesBytes = HexStringUtils.hexStringToByte(content);
            //将密文进行解密
            byte[] contentBytes = decryptCipher.doFinal(aesBytes);
            //将解密后的内容转成字符串并返回
            return new String(contentBytes, UTF_8);
        }
    }


    public static String aesEncrypt(String content) {
        try {
            return aes.encrypt(content);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败", e);
        }
    }

    public static String aesDecrypt(String content) {
        try {
            return aes.decrypt(content);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败", e);
        }
    }

    public static String aesEncrypt(Number id) {
        try {
            return aes.encrypt(String.valueOf(id));
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败", e);
        }
    }

    public static String md5Hex(String content) {
        return DigestUtils.md5Hex(content);
    }


    public static String sha1Hex(String content) {
        return DigestUtils.sha1Hex(content);
    }





    public static void main(String[] args) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {


//        long maxValue = 12342523L;
//
//        for (int i = 0; i < 100; i++) {
//
//            String encrypt = EncryptUtils.aesEncrypt(maxValue);
//            System.out.println(encrypt);
//
//            String decrypt = EncryptUtils.aesDecrypt(encrypt);
//            System.out.println(decrypt);
//        }
        String decrypt = EncryptUtils.aesDecrypt("9796CD95EC499F29284B49CBDCDCABCD");
        System.out.println(decrypt);

    }
}
