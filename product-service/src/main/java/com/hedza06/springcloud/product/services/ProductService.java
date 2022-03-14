package com.hedza06.springcloud.product.services;

import com.hedza06.springcloud.product.clients.dto.UserDTO;
import com.hedza06.springcloud.product.clients.feigns.UserClient;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;
import com.hedza06.springcloud.product.mappers.ProductMapper;
import com.hedza06.springcloud.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final UserClient userClient;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;


    public List<UserDTO> findByProductId(String productId) {
        return userClient.findByProductId(productId);
    }

    @Transactional
    public Product create(ProductDTO product)
    {
        Product productToBeStored = productMapper.toProductEntity(product);
        return productRepository.save(productToBeStored);
    }

    public void assignToUser(ProductDTO product, String userEmailAddress) {
        userClient.assignProductToUserWithEmailAddress(product, userEmailAddress);
    }
}
