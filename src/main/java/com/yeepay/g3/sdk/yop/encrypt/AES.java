package com.yeepay.g3.sdk.yop.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

/**
 * title: <br>
 * description: 描述<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wenkang.zhang
 * @version 1.0.0
 * @since 16/11/24 下午2:35
 */
public class AES implements SymmetricEncryption {

    private static final String NAME = "AES";

    @Override
    public byte[] generateRandomKey() {
        //实例化
        KeyGenerator generator;
        try {
            generator = KeyGenerator.getInstance(NAME);
        } catch (NoSuchAlgorithmException e) {
            //should never be here
            return null;
        }
        //设置密钥长度
        generator.init(128);
        //生成密钥
        return generator.generateKey().getEncoded();
    }

    @Override
    public byte[] encrypt(byte[] plainText, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, NAME);
            byte[] enCodeFormat = secretKey.getEncoded();
            Cipher cipher = getCipher();// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(enCodeFormat, NAME));// 初始化
            return cipher.doFinal(plainText);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] cipherText, byte[] key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, NAME);
            byte[] enCodeFormat = secretKey.getEncoded();
            Cipher cipher = getCipher();// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(enCodeFormat, NAME));// 初始化
            return cipher.doFinal(cipherText);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(NAME);
    }
}
