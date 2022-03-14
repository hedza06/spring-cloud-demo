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
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final UserClient userClient;
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;


    /**
     * Getting all available products
     *
     * @return List of ProductDTO Objects
     */
    public List<ProductDTO> findAll()
    {
        return productRepository.findAll()
            .stream()
            .map(productMapper::toProductDTO)
            .collect(Collectors.toList());
    }

    /**
     * Getting users by product identifier
     *
     * @param productId product source identifier
     * @return List of UserDTO Objects
     */
    public List<UserDTO> findUsersByProductId(String productId) {
        return userClient.findUsersByProductId(productId);
    }

    /**
     * Store new product
     *
     * @param product payload
     * @return persisted Product Object
     */
    @Transactional
    public Product create(ProductDTO product)
    {
        Product productToBeStored = productMapper.toProductEntity(product);
        return productRepository.save(productToBeStored);
    }

    /**
     * Assign given product to user
     *
     * @param product payload
     * @param userEmailAddress user email address
     */
    @Transactional
    public void assignToUser(ProductDTO product, String userEmailAddress) {
        userClient.assignProductToUserWithEmailAddress(product, userEmailAddress);
    }
}
