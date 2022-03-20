package com.hedza06.springcloud.product.error;

import lombok.Getter;

import java.util.Map;

@Getter
public class CustomFeignClientErrorException extends RuntimeException {

    private final String message;
    private final String requestUrl;
    private final Map<String, Object> responseBody;

    public CustomFeignClientErrorException(String message, String requestUrl, Map<String, Object> responseBody)
    {
        super(message);
        this.message = message;
        this.requestUrl = requestUrl;
        this.responseBody = responseBody;
    }

}
