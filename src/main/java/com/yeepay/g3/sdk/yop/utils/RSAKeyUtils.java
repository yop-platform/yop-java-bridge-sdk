package com.yeepay.g3.sdk.yop.utils;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * title: <br>
 * description: 描述<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wenkang.zhang
 * @version 1.0.0
 * @since 16/11/24 下午2:11
 */

/**
 * 此工具类在新版sdk中已被弃用
 *
 * @see com.yeepay.yop.sdk.security.rsa.RSAKeyUtils
 */
public class RSAKeyUtils {

    private static final String RSA = "RSA";

    /**
     * string 转 java.security.PublicKey
     *
     * @param pubKey pubKey
     * @return PublicKey
     */
    public static PublicKey string2PublicKey(String pubKey) {
        return com.yeepay.yop.sdk.security.rsa.RSAKeyUtils.string2PublicKey(pubKey);
    }

    public static PrivateKey string2PrivateKey(String priKey) {
        return com.yeepay.yop.sdk.security.rsa.RSAKeyUtils.string2PrivateKey(priKey);
    }

}
