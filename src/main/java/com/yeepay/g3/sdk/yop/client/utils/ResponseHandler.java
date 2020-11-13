package com.yeepay.g3.sdk.yop.client.utils;

import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.sdk.yop.client.error.YopError;
import com.yeepay.yop.sdk.exception.YopServiceException;
import com.yeepay.yop.sdk.http.Headers;
import com.yeepay.yop.sdk.service.common.response.YosUploadResponse;

import java.util.HashMap;

public class ResponseHandler {

    public static YopResponse parseResponse
            (final com.yeepay.yop.sdk.service.common.response.YopResponse response) {
        YopResponse result = new YopResponse();
        result.setHeaders(new HashMap<String, String>() {
            {
                put(Headers.YOP_SIGN, response.getMetadata().getYopSign());
                put(Headers.YOP_REQUEST_ID, response.getMetadata().getYopRequestId());
            }
        });
        result.setSign(response.getMetadata().getYopSign());
        result.setRequestId(response.getMetadata().getYopRequestId());
        result.setState("SUCCESS");
        if (response.getMetadata().getDate() != null) {
            result.setTs(response.getMetadata().getDate().getTime());
        }
        result.setResult(response.getResult());
        result.setStringResult(response.getStringResult());
        return result;
    }

    public static YopResponse parseExceptionResponse(YopServiceException e) {
        YopResponse result = new YopResponse();
        YopError error = new YopError();
        error.setCode(e.getErrorCode());
        error.setMessage(e.getErrorMessage());
        error.setDocUrl(e.getDocUrl());
        error.setSubCode(e.getSubErrorCode());
        error.setSubMessage(e.getSubMessage());
        result.setError(error);
        result.setState("FAILURE");
        return result;
    }

    public static YopResponse parseResponse
            (final YosUploadResponse response) {
        YopResponse result = new YopResponse();
        result.setState("SUCCESS");
        result.setResult(response.getResult());
        result.setStringResult(response.getStringResult());
        result.setSign(response.getMetadata().getYopSign());
        result.setRequestId(response.getMetadata().getYopRequestId());
        result.setHeaders(new HashMap<String, String>() {
            {
                put(Headers.YOP_REQUEST_ID, response.getMetadata().getYopRequestId());
                put(Headers.YOP_SIGN, response.getMetadata().getYopSign());
                put(Headers.YOP_HASH_CRC64ECMA, response.getMetadata().getCrc64ECMA());
                put(Headers.CONTENT_DISPOSITION, response.getMetadata().getContentDisposition());
            }
        });
        return result;
    }

}
