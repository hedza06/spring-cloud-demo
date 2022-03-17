package com.hedza06.springcloud.user.application.service;

import com.hedza06.springcloud.user.application.port.in.UserUseCase;
import com.hedza06.springcloud.user.application.port.out.UserPort;
import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserUseCase {

    private final UserPort userPort;


    @Override
    public List<UserDTO> findUsersByProductSourceIdentifier(String productSourceIdentifier) {
        return userPort.findUsersByProductSourceIdentifier(productSourceIdentifier);
    }

    @Override
    @Transactional
    public void assignProductToUser(ProductDTO productDTO, String userEmailAddress) {
        userPort.assignProductToUser(productDTO, userEmailAddress);
    }
}
