package com.hedza06.springcloud.product.adapter.api;

import com.hedza06.springcloud.product.application.port.in.UserClientIntegrationUseCase;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserClientIntegrationAdapter implements UserClientIntegrationUseCase {

    private final UserClient userClient;


    @Override
    public List<UserDTO> findUsersByProductId(String productSourceIdentifier) {
        return userClient.findUsersByProductId(productSourceIdentifier);
    }

    @Override
    public void assignProductToUserWithEmailAddress(ProductDTO productDTO, String email) {
        userClient.assignProductToUserWithEmailAddress(productDTO, email);
    }

    @Override
    public Map<String, Object> simulateBadRequestError() {
        return userClient.simulateBadRequestError();
    }

    @Override
    public void simulateInternalServerError() {
        userClient.simulateInternalServerError();
    }
}
