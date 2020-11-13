package com.yeepay.g3.sdk.yop.utils;

import com.google.common.base.Charsets;
import com.yeepay.g3.sdk.yop.encrypt.*;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * title: 数字信封 Util<br>
 * description: 描述<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author baitao.ji
 * @version 1.0.0
 * @since 14/12/20 23:10
 */

/**
 * 此工具类在新版sdk中已被弃用
 *
 * @see com.yeepay.yop.sdk.security.DigitalEnvelopeUtils
 */
public final class DigitalEnvelopeUtils {

    public static final String SEPERATOR = "$";

    /**
     * 封装数字信封
     *
     * @param digitalEnvelopeDTO 待加密内容
     * @param privateKey         自己生成的私钥，用于签名
     * @param publicKey          对方给的公钥，用于加密
     * @return DigitalEnvelopeDTO
     */
    public static DigitalEnvelopeDTO encrypt(DigitalEnvelopeDTO digitalEnvelopeDTO, PrivateKey privateKey, PublicKey publicKey) {
        String source = digitalEnvelopeDTO.getPlainText();
        byte[] data = source.getBytes(Charsets.UTF_8);

        SymmetricEncryptAlgEnum symmetricEncryptAlg = digitalEnvelopeDTO.getSymmetricEncryptAlg();
        SymmetricEncryption symmetricEncryption = SymmetricEncryptionFactory.getSymmetricEncryption(symmetricEncryptAlg);
        //生成随机密钥
        byte[] randomKey = symmetricEncryption.generateRandomKey();

        DigestAlgEnum digestAlg = digitalEnvelopeDTO.getDigestAlg();
        //对数据进行签名
        byte[] sign = RSA.sign(data, privateKey, digestAlg);
        String signToBase64 = Encodes.encodeUrlSafeBase64(sign);

        //使用随机密钥对数据和签名进行加密
        data = (source + SEPERATOR + signToBase64).getBytes(Charsets.UTF_8);
        byte[] encryptedData = symmetricEncryption.encrypt(data, randomKey);
        String encryptedDataToBase64 = Encodes.encodeUrlSafeBase64(encryptedData);

        //对密钥加密
        byte[] encryptedRandomKey = RSA.encrypt(randomKey, publicKey);
        String encryptedRandomKeyToBase64 = Encodes.encodeUrlSafeBase64(encryptedRandomKey);

        //把密文和签名进行打包
        String cipherText = encryptedRandomKeyToBase64 +
                SEPERATOR +
                encryptedDataToBase64 +
                SEPERATOR +
                symmetricEncryptAlg.getValue() +
                SEPERATOR +
                digestAlg.getValue();
        digitalEnvelopeDTO.setCipherText(cipherText);
        return digitalEnvelopeDTO;
    }

    /**
     * 拆开数字信封
     *
     * @param digitalEnvelopeDTO 待解密内容
     * @param privateKey         自己生成的私钥，用于解密
     * @param publicKey          对方给的公钥，用于签名
     * @return DigitalEnvelopeDTO
     */
    public static DigitalEnvelopeDTO decrypt(DigitalEnvelopeDTO digitalEnvelopeDTO, PrivateKey privateKey, PublicKey publicKey) {
        String sourceData = com.yeepay.yop.sdk.security.DigitalEnvelopeUtils.decrypt(digitalEnvelopeDTO.getCipherText(), privateKey);
        digitalEnvelopeDTO.setPlainText(sourceData);
        //返回源数据
        return digitalEnvelopeDTO;
    }


    public static DigitalSignatureDTO sign(DigitalSignatureDTO digitalSignatureDTO, PrivateKey privateKey) {
        digitalSignatureDTO.setSignature(sign0(digitalSignatureDTO, privateKey));
        return digitalSignatureDTO;
    }

    public static String sign0(DigitalSignatureDTO digitalSignatureDTO, PrivateKey privateKey) {
        String source = digitalSignatureDTO.getPlainText();
        byte[] data = source.getBytes(Charsets.UTF_8);

        DigestAlgEnum digestAlg = digitalSignatureDTO.getDigestAlg();
        //对数据进行签名
        byte[] sign = RSA.sign(data, privateKey, digestAlg);
        String signToBase64 = Encodes.encodeUrlSafeBase64(sign);

        //把密文和签名进行打包
        return signToBase64 +
                SEPERATOR +
                digestAlg.getValue();
    }

}
