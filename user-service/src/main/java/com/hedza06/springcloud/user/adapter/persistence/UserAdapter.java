package com.hedza06.springcloud.user.adapter.persistence;

import com.hedza06.springcloud.user.application.port.out.UserPort;
import com.hedza06.springcloud.user.domain.User;
import com.hedza06.springcloud.user.domain.UserProduct;
import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAdapter implements UserPort {

    private final UserRepository userRepository;
    private final UserProductRepository userProductRepository;


    @Override
    public List<UserDTO> findUsersByProductSourceIdentifier(String productSourceIdentifier) {
        return userRepository.findByProductSourceIdentifier(productSourceIdentifier);
    }

    @Override
    public void assignProductToUser(ProductDTO productDTO, String userEmailAddress)
    {
        Optional<User> userOptional = userRepository.findByEmail(userEmailAddress);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProductSourceIdentifier(productDTO.getSourceIdentifier());

            userProductRepository.save(userProduct);
        }
    }
}
