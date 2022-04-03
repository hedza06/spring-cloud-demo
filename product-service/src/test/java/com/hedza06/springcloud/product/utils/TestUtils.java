package com.hedza06.springcloud.product.utils;

import com.hedza06.springcloud.product.clients.dto.UserDTO;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;

import java.util.List;

public final class TestUtils {

    private TestUtils() { }

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
     * Getting product entities
     *
     * @return List of Product Objects
     */
    public static List<Product> getProductEntities()
    {
        Product productOne = new Product(1, "Product one", "D1", "S1");
        Product productTwo = new Product(2, "Product two", "D2", "S2");

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

    public static List<UserDTO> getMockedUsers()
    {
        UserDTO userOne = new UserDTO(1, "Heril Muratovic", "P-001");
        UserDTO userTwo = new UserDTO(2, "John Doe", "P-001");
        UserDTO userThree = new UserDTO(3, "Jake Doe", "P-001");

        return List.of(userOne, userTwo, userThree);
    }
}
