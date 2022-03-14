package com.hedza06.springcloud.product.mappers;

import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper
{
    Product toProductEntity(ProductDTO productDTO);
}
