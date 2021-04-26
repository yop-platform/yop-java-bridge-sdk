package com.yeepay.g3.sdk.yop.client;

import com.google.common.collect.Multimap;
import com.yeepay.g3.sdk.yop.client.router.YopConstants;
import com.yeepay.g3.sdk.yop.encrypt.Assert;
import com.yeepay.g3.sdk.yop.encrypt.RSA;
import com.yeepay.g3.sdk.yop.exception.YopClientException;
import com.yeepay.g3.sdk.yop.http.Headers;
import com.yeepay.yop.sdk.auth.credentials.PKICredentialsItem;
import com.yeepay.yop.sdk.auth.credentials.YopPKICredentials;
import com.yeepay.yop.sdk.security.CertTypeEnum;
import com.yeepay.yop.sdk.security.rsa.RSAKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 每个请求对应一个ClientRequest对象
 * </pre>
 * 此类已被弃用，请及时升级
 *
 * @author wang.bao
 * @version 1.0
 * @see com.yeepay.yop.sdk.service.common.request.YopRequest
 */
public class YopRequest {

    private static final Logger LOGGER = LoggerFactory.getLogger(YopRequest.class);

    private String signAlg = YopConstants.ALG_SHA256;

    private com.yeepay.yop.sdk.service.common.request.YopRequest yopRequest = new com.yeepay.yop.sdk.service.common.request.YopRequest("", "");

    {
        yopRequest.getRequestConfig().setCustomRequestHeaders(new HashMap<String, String>());
    }

    public YopRequest() {
        yopRequest.getRequestConfig();
    }

    public YopRequest(String appKey) {
        Validate.notBlank(appKey, "AppKey is blank.");
        yopRequest.getRequestConfig().setAppKey(appKey);
    }

    /**
     * 同一个工程内部可支持多个开放应用发起调用
     */
    public YopRequest(String appKey, String secretKey) {
        Validate.notBlank(appKey, "AppKey is blank.");
        Validate.notBlank(secretKey, "SecretKey is blank.");
        PKICredentialsItem pkiCredentialsItem = new PKICredentialsItem(RSAKeyUtils.string2PrivateKey(secretKey), null, CertTypeEnum.RSA2048);
        YopPKICredentials yopPKICredentials = new YopPKICredentials(appKey, null, pkiCredentialsItem);
        yopRequest.getRequestConfig().setCredentials(yopPKICredentials);
    }

    public YopRequest setSubCustomerId(String subCustomerId) {
        yopRequest.getRequestConfig().getCustomRequestHeaders().put(Headers.YOP_SUB_CUSTOMER_ID, subCustomerId);
        return this;
    }

    public YopRequest setParam(String paramName, Object paramValue) {
        removeParam(paramName);
        addParam(paramName, paramValue, false);
        return this;
    }

    public YopRequest addParam(String paramName, Object paramValue) {
        addParam(paramName, paramValue, false);
        return this;
    }

    public YopRequest addNullParam(String paramName) {
        Assert.hasText(paramName, "参数名不能为空");
        yopRequest.getParameters().put(paramName, "");
        return this;
    }

    /**
     * 添加参数
     *
     * @param paramName  参数名
     * @param paramValue 参数值：如果为集合或数组类型，则自动拆解，最终想作为数组提交到服务端
     * @param ignoreSign 是否忽略签名
     * @return
     */
    public YopRequest addParam(String paramName, Object paramValue, boolean ignoreSign) {
        Assert.hasText(paramName, "参数名不能为空");
        if (paramValue == null || ((paramValue instanceof String) && StringUtils.isBlank((String) paramValue))
                || ((paramValue instanceof Collection<?>) && ((Collection<?>) paramValue).isEmpty())) {
            LOGGER.warn("param " + paramName + "is null or empty，ignore it");
            return this;
        }

        // file
        if (StringUtils.equals("_file", paramName)) {
            this.addFile(paramValue);
            return this;
        }

        if (YopConstants.isProtectedKey(paramName)) {
            yopRequest.getParameters().put(paramName, paramValue.toString());
            return this;
        }
        if (paramValue instanceof Collection<?>) {
            // 集合类
            for (Object o : (Collection<?>) paramValue) {
                if (o != null) {
                    yopRequest.getParameters().put(paramName, o.toString());
                }
            }
        } else if (paramValue.getClass().isArray()) {
            // 数组
            int len = Array.getLength(paramValue);
            for (int i = 0; i < len; i++) {
                Object o = Array.get(paramValue, i);
                if (o != null) {
                    yopRequest.getParameters().put(paramName, o.toString());
                }
            }
        } else {
            yopRequest.getParameters().put(paramName, paramValue.toString());
        }
        return this;
    }

    public List<String> getParam(String key) {
        return (List<String>) yopRequest.getParameters().get(key);
    }

    public String getParamValue(String key) {
        return StringUtils.join(yopRequest.getParameters().get(key), ",");
    }

    public String removeParam(String key) {
        return StringUtils.join(yopRequest.getParameters().removeAll(key), ",");
    }

    public Multimap<String, String> getParams() {
        return yopRequest.getParameters();
    }

    public YopRequest addFile(Object file) {
        addFile("_file", file);
        return this;
    }

    public YopRequest addFile(String paramName, Object file) {
        if (file instanceof String || file instanceof File || file instanceof InputStream) {
            if (file instanceof String) {
                yopRequest.getMultipartFiles().put(paramName, new File((String) file));
            } else {
                yopRequest.getMultipartFiles().put(paramName, file);
            }
        } else {
            throw new YopClientException("Unsupported file object.");
        }
        return this;
    }

    public Map<String, Object> getMultipartFiles() {
        return (Map<String, Object>) yopRequest.getMultipartFiles();
    }

    public boolean hasFiles() {
        return null != getMultipartFiles() && getMultipartFiles().size() > 0;
    }

    public Map<String, String> getHeaders() {
        return yopRequest.getRequestConfig().getCustomRequestHeaders();
    }

    /**
     * customize header,but none system reserved headers
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        yopRequest.getRequestConfig().getCustomRequestHeaders().put(name, value);
    }

    public String getRequestId() {
        return yopRequest.getRequestConfig().getCustomRequestHeaders().get(Headers.YOP_REQUEST_ID);
    }

    public void setRequestId(String requestId) {
        yopRequest.getRequestConfig().getCustomRequestHeaders().put(Headers.YOP_REQUEST_ID, requestId);
    }

    public void setRequestSource(String source) {
        yopRequest.getRequestConfig().getCustomRequestHeaders().put(Headers.YOP_REQUEST_SOURCE, source);
    }

    @Deprecated
    public String getSignAlg() {
        return signAlg;
    }

    @Deprecated
    public void setSignAlg(String signAlg) {
        this.signAlg = signAlg;
    }

    @Deprecated
    public String getSecretKey() {
        YopPKICredentials yopPKICredentials  = (YopPKICredentials) yopRequest.getRequestConfig().getCredentials();
        return RSAKeyUtils.key2String(yopPKICredentials.getCredential().getPrivateKey());
    }

    public Boolean isNeedEncrypt() {
        return yopRequest.getRequestConfig().getNeedEncrypt();
    }

    public void setNeedEncrypt(Boolean needEncrypt) {
        this.yopRequest.getRequestConfig().setNeedEncrypt(needEncrypt);
    }

    public String getEncryptKey() {
        if (yopRequest.getRequestConfig().getCredentials() instanceof YopPKICredentials) {
            YopPKICredentials yopRSACredentials = (YopPKICredentials) yopRequest.getRequestConfig().getCredentials();
            return yopRSACredentials.getEncryptKey();
        }
        return null;
    }

    public void setEncryptKey(String encryptKey) {
        PKICredentialsItem pkiCredentialsItem = new PKICredentialsItem(RSAKeyUtils.string2PrivateKey(encryptKey), null, CertTypeEnum.RSA2048);
        YopPKICredentials yopPKICredentials = new YopPKICredentials(null, null, pkiCredentialsItem);
        yopRequest.getRequestConfig().setCredentials(yopPKICredentials);
    }

    public String getAppKey() {
        return yopRequest.getRequestConfig().getAppKey();
    }

    public com.yeepay.yop.sdk.service.common.request.YopRequest getYopRequest() {
        return this.yopRequest;
    }


    /**
     * 将参数转换成k=v拼接的形式
     *
     * @return
     */
    public String toQueryString() {
        StringBuilder builder = new StringBuilder();
        for (String key : this.yopRequest.getParameters().keySet()) {
            Collection<String> values = this.yopRequest.getParameters().get(key);
            for (String value : values) {
                builder.append(builder.length() == 0 ? "" : "&");
                builder.append(key);
                builder.append("=");
                builder.append(value);
            }
        }
        return builder.toString();
    }
}
