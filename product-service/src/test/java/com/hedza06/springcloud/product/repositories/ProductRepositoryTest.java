package com.hedza06.springcloud.product.repositories;

import com.hedza06.springcloud.product.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;


    @Test
    @Transactional
    public void shouldReturnCreated()
    {
        Product product = Product.builder()
            .description("Dummy description")
            .name("Dummy-name")
            .sourceIdentifier("dummy-source-identifier")
            .build();

        Product persistedProduct = productRepository.save(product);
        assertThat(persistedProduct).isNotNull();
        assertThat(persistedProduct.getId()).isNotNull();

        Optional<Product> targetProductOptional = productRepository.findById(persistedProduct.getId());
        assertThat(targetProductOptional).isPresent();

        Product targetProduct = targetProductOptional.get();
        assertThat(targetProduct.getName()).isEqualTo(persistedProduct.getName());
        assertThat(targetProduct.getDescription()).isEqualTo(persistedProduct.getDescription());
        assertThat(targetProduct.getSourceIdentifier()).isEqualTo(persistedProduct.getSourceIdentifier());
    }
}
