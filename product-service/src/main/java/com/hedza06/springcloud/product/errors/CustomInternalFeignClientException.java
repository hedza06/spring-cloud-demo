package com.hedza06.springcloud.product.errors;

import lombok.Getter;

@Getter
public class CustomInternalFeignClientException extends RuntimeException {

    private final String message;
    private final String requestUrl;

    public CustomInternalFeignClientException(String message, String requestUrl)
    {
        super(message);
        this.message = message;
        this.requestUrl = requestUrl;
    }
}
