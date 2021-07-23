package com.yeepay.g3.sdk.yop.utils;

import com.yeepay.g3.sdk.yop.encrypt.CertTypeEnum;
import com.yeepay.yop.sdk.config.YopSdkConfig;
import com.yeepay.yop.sdk.config.provider.YopSdkConfigProviderRegistry;
import com.yeepay.yop.sdk.config.provider.file.YopHttpClientConfig;
import com.yeepay.yop.sdk.config.provider.file.YopProxyConfig;

import java.security.PublicKey;

/**
 * title: <br>
 * description:描述<br>
 * Copyright: Copyright (c)2011<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author dreambt
 * @version 1.0.0
 * @since 2016/12/26 下午3:48
 */
@Deprecated
public final class InternalConfig {

    public static int CONNECT_TIMEOUT = 30000;
    public static int READ_TIMEOUT = 60000;

    public static int MAX_CONN_TOTAL = 200;
    public static int MAX_CONN_PER_ROUTE = 100;

    public static boolean TRUST_ALL_CERTS = false;

    public static YopProxyConfig proxy;

    static {
        init();
    }

    private static void init() {
        YopSdkConfig config = YopSdkConfigProviderRegistry.getProvider().getConfig();
        if (config != null && config.getYopHttpClientConfig() != null) {
            YopHttpClientConfig clientConfig = config.getYopHttpClientConfig();
            CONNECT_TIMEOUT = clientConfig.getConnectTimeout();
            READ_TIMEOUT = clientConfig.getReadTimeout();
            MAX_CONN_TOTAL = clientConfig.getMaxConnTotal();
            MAX_CONN_PER_ROUTE = clientConfig.getMaxConnPerRoute();
            proxy = config.getProxy();
            TRUST_ALL_CERTS = config.getTrustAllCerts();
        }
    }

    public static PublicKey getYopPublicKey(CertTypeEnum certType) {
        YopSdkConfig defaultYopSdkConfig = YopSdkConfigProviderRegistry.getProvider().getConfig();
        return defaultYopSdkConfig.loadYopPublicKey(parseCertTypeEnum(certType));
    }

    private static com.yeepay.yop.sdk.security.CertTypeEnum parseCertTypeEnum(CertTypeEnum oldCertTypeEnum) {
        if (oldCertTypeEnum == null) {
            return null;
        }
        switch (oldCertTypeEnum) {
            case AES128: {
                return com.yeepay.yop.sdk.security.CertTypeEnum.AES128;
            }
            case AES256: {
                return com.yeepay.yop.sdk.security.CertTypeEnum.AES256;
            }
            case RSA2048: {
                return com.yeepay.yop.sdk.security.CertTypeEnum.RSA2048;
            }
        }
        return null;
    }

}
