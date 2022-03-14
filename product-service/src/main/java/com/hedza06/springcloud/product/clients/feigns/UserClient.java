package com.hedza06.springcloud.product.clients.feigns;

import com.hedza06.springcloud.product.clients.dto.UserDTO;
import com.hedza06.springcloud.product.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient
{
    @GetMapping(value = "/user/by-product/{productId}")
    List<UserDTO> findUsersByProductId(@PathVariable(value = "productId") String productSourceIdentifier);

    @PutMapping(value = "/user/{email}/assign-product")
    void assignProductToUserWithEmailAddress(@RequestBody ProductDTO productDTO, @PathVariable String email);
}
