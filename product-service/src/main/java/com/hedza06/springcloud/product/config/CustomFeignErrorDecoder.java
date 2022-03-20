package com.hedza06.springcloud.product.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedza06.springcloud.product.errors.CustomFeignClientErrorException;
import com.hedza06.springcloud.product.errors.CustomInternalFeignClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response)
    {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.is4xxClientError())
        {
            Map<String, Object> convertedResponse = convertResponseToMap(responseBody);
            return new CustomFeignClientErrorException(
                "Feign error exception",
                requestUrl,
                convertedResponse
            );
        }
        else if (responseStatus.is5xxServerError())
        {
            return new CustomInternalFeignClientException(
                "Internal server error exception", requestUrl
            );
        }
        else {
            return new Exception("Generic error!");
        }
    }

    /**
     * Converting response body to map
     *
     * @param responseBody feign response body
     * @return Map of string and object data
     */
    private Map<String, Object> convertResponseToMap(Response.Body responseBody)
    {
        try
        {
            return new ObjectMapper()
                .readValue(responseBody.asInputStream().readAllBytes(), Map.class);
        }
        catch (IOException e) {
            return Collections.emptyMap();
        }
    }
}
