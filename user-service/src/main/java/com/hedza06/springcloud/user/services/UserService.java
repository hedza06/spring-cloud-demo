package com.hedza06.springcloud.user.services;

import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.entities.User;
import com.hedza06.springcloud.user.entities.UserProduct;
import com.hedza06.springcloud.user.repositories.UserProductRepository;
import com.hedza06.springcloud.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserProductRepository userProductRepository;


    public List<UserDTO> findByProductSource(String productId) {
        return userRepository.findByProductSourceIdentifier(productId);
    }

    @Transactional
    public void assignProductToUser(String sourceIdentifier, String email)
    {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent())
        {
            User user = userOptional.get();
            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProductSourceIdentifier(sourceIdentifier);

            userProductRepository.save(userProduct);
        }
    }
}
