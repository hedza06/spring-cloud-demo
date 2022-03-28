package com.hedza06.springcloud.product.utils;

import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;

import java.util.List;

public class TestUtils {

    /**
     * Getting products for response entity
     *
     * @return List of ProductDTO Objects
     */
    public static List<ProductDTO> getAllMocked()
    {
        ProductDTO productOne = new ProductDTO(1, "Product one", "D1", "S1");
        ProductDTO productTwo = new ProductDTO(2, "Product two", "D2", "S2");

        return List.of(productOne, productTwo);
    }

    /**
     * Getting persisted product with identifier
     *
     * @return Product Object
     */
    public static Product getPersisted()
    {
        Product product = new Product();
        product.setId(100);
        product.setName("P-NEW");
        product.setDescription("D-NEW");
        product.setSourceIdentifier("S-NEW");

        return product;
    }

    /**
     * Getting product for creating new request
     *
     * @return ProductDTO object
     */
    public static ProductDTO getRequestProduct()
    {
        ProductDTO product = new ProductDTO();
        product.setName("P-NEW");
        product.setDescription("D-NEW");
        product.setSourceIdentifier("S-NEW");

        return product;
    }
}
