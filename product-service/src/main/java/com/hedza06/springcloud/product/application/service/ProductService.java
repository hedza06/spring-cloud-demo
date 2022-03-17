package com.hedza06.springcloud.product.application.service;

import com.hedza06.springcloud.product.application.port.in.ProductUseCase;
import com.hedza06.springcloud.product.application.port.out.ProductPort;
import com.hedza06.springcloud.product.domain.Product;
import com.hedza06.springcloud.product.dto.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService implements ProductUseCase {

    private final ProductPort productPort;


    @Override
    public List<ProductDTO> findAll() {
        return productPort.findAll();
    }

    @Override
    public Product create(ProductDTO productDTO) {
        return productPort.create(productDTO);
    }
}
