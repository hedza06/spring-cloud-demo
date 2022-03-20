package com.hedza06.springcloud.product.adapter.api;

import com.hedza06.springcloud.product.config.CustomFeignErrorDecoder;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "user-service", configuration = CustomFeignErrorDecoder.class)
public interface UserClient
{
    @GetMapping(value = "/user/by-product/{productId}")
    List<UserDTO> findUsersByProductId(@PathVariable(value = "productId") String productSourceIdentifier);

    @PutMapping(value = "/user/{email}/assign-product")
    void assignProductToUserWithEmailAddress(@RequestBody ProductDTO productDTO, @PathVariable String email);

    @GetMapping(value = "/user/simulate/internal-server-error")
    void simulateInternalServerError();

    @GetMapping(value = "/user/simulate/bad-request-error")
    Map<String, Object> simulateBadRequestError();
}
