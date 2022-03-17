package com.hedza06.springcloud.product.application.port.in;

import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserClientIntegrationUseCase
{
    List<UserDTO> findUsersByProductId(@PathVariable(value = "productId") String productSourceIdentifier);
    void assignProductToUserWithEmailAddress(@RequestBody ProductDTO productDTO, @PathVariable String email);
}
