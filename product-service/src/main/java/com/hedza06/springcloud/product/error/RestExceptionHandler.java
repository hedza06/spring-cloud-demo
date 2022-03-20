package com.hedza06.springcloud.product.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomFeignClientErrorException.class)
    public ResponseEntity<Object> handleFeignClientException(CustomFeignClientErrorException e)
    {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errorMessage", e.getMessage());
        responseBody.put("requestUrl", e.getRequestUrl());
        responseBody.put("errorBody", e.getResponseBody());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomInternalFeignClientException.class)
    public ResponseEntity<Object> handleFeignClientException(CustomInternalFeignClientException e)
    {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("errorMessage", e.getMessage());
        responseBody.put("requestUrl", e.getRequestUrl());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
