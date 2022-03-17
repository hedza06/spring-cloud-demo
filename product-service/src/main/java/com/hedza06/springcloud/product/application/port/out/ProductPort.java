package com.hedza06.springcloud.product.application.port.out;

import com.hedza06.springcloud.product.domain.Product;
import com.hedza06.springcloud.product.dto.ProductDTO;

import java.util.List;

public interface ProductPort
{
    List<ProductDTO> findAll();
    Product create(ProductDTO productDTO);
}
