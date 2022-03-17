package com.hedza06.springcloud.product.adapter.persistence;

import com.hedza06.springcloud.product.application.port.out.ProductPort;
import com.hedza06.springcloud.product.domain.Product;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductPort {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;


    @Override
    public List<ProductDTO> findAll()
    {
        return productRepository
            .findAll()
            .stream()
            .map(productMapper::toProductDTO)
            .collect(Collectors.toList());
    }

    @Override
    public Product create(ProductDTO productDTO)
    {
        Product product = productMapper.toProductEntity(productDTO);
        return productRepository.save(product);
    }
}
