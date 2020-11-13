package com.yeepay.g3.sdk.yop.client;

import com.yeepay.g3.sdk.yop.client.utils.ResponseHandler;
import com.yeepay.yop.sdk.exception.YopServiceException;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.response.YosUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * <pre>
 * 非对称 Client，简化用户发起请求及解析结果的处理
 * </pre>
 *
 * @author baitao.ji
 * @version 3.0
 */

/**
 * 此类已被弃用，请及时升级
 *
 * @see com.yeepay.yop.sdk.service.common.YopClient
 */
public class YopRsaClient {

    protected static final Logger LOGGER = LoggerFactory.getLogger(YopRsaClient.class);

    private static YopClient client = new YopClientBuilder().build();

    public static YopResponse get(String apiUri, YopRequest request) throws IOException {
        com.yeepay.yop.sdk.service.common.request.YopRequest newRequest =
                request.getYopRequest().withApiUri(apiUri).withHttpMethod("GET");
        try {
            com.yeepay.yop.sdk.service.common.response.YopResponse response = client.request(newRequest);
            return ResponseHandler.parseResponse(response);
        } catch (YopServiceException e) {
            return ResponseHandler.parseExceptionResponse(e);
        }
    }

    /**
     * 发起post请求，以YopResponse对象返回
     *
     * @param apiUri  目标地址或命名模式的method
     * @param request 客户端请求对象
     * @return 响应对象
     */
    public static YopResponse post(String apiUri, YopRequest request) throws IOException {
        com.yeepay.yop.sdk.service.common.request.YopRequest newRequest =
                request.getYopRequest().withApiUri(apiUri).withHttpMethod("POST");
        try {
            com.yeepay.yop.sdk.service.common.response.YopResponse response = client.request(newRequest);
            return ResponseHandler.parseResponse(response);
        } catch (YopServiceException e) {
            return ResponseHandler.parseExceptionResponse(e);
        }
    }

    /**
     * 上传文件
     *
     * @param apiUri  目标地址或命名模式的method
     * @param request 客户端请求对象
     * @return 响应对象
     */
    public static YopResponse upload(String apiUri, YopRequest request) throws IOException {
        com.yeepay.yop.sdk.service.common.request.YopRequest newRequest =
                request.getYopRequest().withApiUri(apiUri).withHttpMethod("POST");
        ;
        try {
            YosUploadResponse response = client.upload(newRequest);
            return ResponseHandler.parseResponse(response);
        } catch (YopServiceException e) {
            return ResponseHandler.parseExceptionResponse(e);
        }
    }
}
