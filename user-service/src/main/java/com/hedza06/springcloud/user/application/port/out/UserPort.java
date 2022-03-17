package com.hedza06.springcloud.user.application.port.out;

import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;

import java.util.List;

public interface UserPort
{
    List<UserDTO> findUsersByProductSourceIdentifier(String productSourceIdentifier);
    void assignProductToUser(ProductDTO productDTO, String userEmailAddress);
}
