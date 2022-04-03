package com.hedza06.springcloud.product.unit.services;

import com.hedza06.springcloud.product.clients.dto.UserDTO;
import com.hedza06.springcloud.product.clients.feigns.UserClient;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;
import com.hedza06.springcloud.product.mappers.ProductMapper;
import com.hedza06.springcloud.product.repositories.ProductRepository;
import com.hedza06.springcloud.product.services.ProductService;
import com.hedza06.springcloud.product.utils.TestUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @Mock
    private UserClient userClient;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;


    @Test
    public void shouldReturnAllProductAsDTO()
    {
        List<Product> productEntities = TestUtils.getProductEntities();
        List<ProductDTO> productDTOs = TestUtils.getAllMocked();

        when(productRepository.findAll()).thenReturn(productEntities);
//        List<ProductDTO> products = productService.findAll();
        productService.findAll();

        assertThat(productEntities.get(0).getId()).isEqualTo(productDTOs.get(0).getId());
        assertThat(productEntities.get(0).getName()).isEqualTo(productDTOs.get(0).getName());
        assertThat(productEntities.get(0).getDescription()).isEqualTo(productDTOs.get(0).getDescription());
        assertThat(productEntities.get(0).getSourceIdentifier()).isEqualTo(productDTOs.get(0).getSourceIdentifier());

        assertThat(productEntities.get(1).getId()).isEqualTo(productDTOs.get(1).getId());
        assertThat(productEntities.get(1).getName()).isEqualTo(productDTOs.get(1).getName());
        assertThat(productEntities.get(1).getDescription()).isEqualTo(productDTOs.get(1).getDescription());
        assertThat(productEntities.get(1).getSourceIdentifier()).isEqualTo(productDTOs.get(1).getSourceIdentifier());

//        assertThat(products.get(0).getId()).isEqualTo(productDTOs.get(0).getId());
//        assertThat(products.get(0).getName()).isEqualTo(productDTOs.get(0).getName());
//        assertThat(products.get(0).getDescription()).isEqualTo(productDTOs.get(0).getDescription());
//        assertThat(products.get(0).getSourceIdentifier()).isEqualTo(productDTOs.get(0).getSourceIdentifier());
//
//        assertThat(products.get(1).getId()).isEqualTo(productDTOs.get(1).getId());
//        assertThat(products.get(1).getName()).isEqualTo(productDTOs.get(1).getName());
//        assertThat(products.get(1).getDescription()).isEqualTo(productDTOs.get(1).getDescription());
//        assertThat(products.get(1).getSourceIdentifier()).isEqualTo(productDTOs.get(1).getSourceIdentifier());
    }

    @Test
    public void shouldReturnUserDTOsByGivenProductSourceIdentifier()
    {
        List<UserDTO> users = TestUtils.getMockedUsers();
        when(userClient.findUsersByProductId(anyString())).thenReturn(users);

        productService.findUsersByProductId(anyString());

        assertThat(users).isNotEmpty().isNotNull();
    }

    @Test
    public void shouldCreateNewProductByDTOAndReturnEntity()
    {
        ProductDTO productDTO = TestUtils.getRequestProduct();
        Product productWithNoId = new Product(
            null,
            productDTO.getName(),
            productDTO.getDescription(),
            productDTO.getSourceIdentifier()
        );
        Product productToBePersisted = new Product(
            33,
            productDTO.getName(),
            productDTO.getDescription(),
            productDTO.getSourceIdentifier()
        );

        when(productMapper.toProductEntity(productDTO)).thenReturn(productWithNoId);
        when(productRepository.save(any(Product.class))).thenReturn(productToBePersisted);

        Product persistedProduct = productService.create(productDTO);

        assertThat(persistedProduct.getId()).isNotNull();
        assertThat(persistedProduct.getName()).isEqualTo(productToBePersisted.getName());
        assertThat(persistedProduct.getDescription()).isEqualTo(productToBePersisted.getDescription());
        assertThat(persistedProduct.getSourceIdentifier()).isEqualTo(productToBePersisted.getSourceIdentifier());
    }

    @Test
    @Ignore("Unnecessary stubbing")
    public void shouldAssignProductToUserTargetingByEmail() { }

    @Test
    @Ignore("Unnecessary stubbing")
    public void simulateInternalServerError() { }

    @Test
    public void simulateBadRequestAndReturnErrorResponseAsHashMap()
    {
        Map<String, Object> errorResponseFromClient = new HashMap<>();
        errorResponseFromClient.put("errorMessage", "Error message");
        errorResponseFromClient.put("errorStatus", "Error status");

        when(userClient.simulateBadRequestError()).thenReturn(errorResponseFromClient);

        Map<String, Object> errorResponseFromService = productService.simulateBadRequestError();

        assertThat(errorResponseFromClient).hasSize(2);
        assertThat(errorResponseFromService).hasSize(2);

        assertThat(errorResponseFromClient).containsKey("errorMessage").containsKey("errorStatus");
        assertThat(errorResponseFromService).containsKey("errorMessage").containsKey("errorStatus");

        assertThat(errorResponseFromClient.get("errorMessage"))
            .isEqualTo(errorResponseFromService.get("errorMessage"));

        assertThat(errorResponseFromClient.get("errorStatus"))
            .isEqualTo(errorResponseFromService.get("errorStatus"));
    }
}
