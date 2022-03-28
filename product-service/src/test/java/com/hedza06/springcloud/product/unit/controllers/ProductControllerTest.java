package com.hedza06.springcloud.product.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedza06.springcloud.product.controllers.ProductController;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;
import com.hedza06.springcloud.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.hedza06.springcloud.product.utils.TestUtils.getAllMocked;
import static com.hedza06.springcloud.product.utils.TestUtils.getPersisted;
import static com.hedza06.springcloud.product.utils.TestUtils.getRequestProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    private static final String GET_ALL_API = "/product/all";
    private static final String CREATE_API  = "/product";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;


    @Test
    public void shouldReturnAll() throws Exception
    {
        List<ProductDTO> mockedProducts = getAllMocked();
        given(productService.findAll()).willReturn(mockedProducts);

        mockMvc
            .perform(MockMvcRequestBuilders.get(GET_ALL_API))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("[0].id").value(mockedProducts.get(0).getId()))
            .andExpect(jsonPath("[0].name").value(mockedProducts.get(0).getName()))
            .andExpect(jsonPath("[0].description").value(mockedProducts.get(0).getDescription()))
            .andExpect(jsonPath("[0].sourceIdentifier").value(mockedProducts.get(0).getSourceIdentifier()))
            .andExpect(jsonPath("[1].id").value(mockedProducts.get(1).getId()))
            .andExpect(jsonPath("[1].name").value(mockedProducts.get(1).getName()))
            .andExpect(jsonPath("[1].description").value(mockedProducts.get(1).getDescription()))
            .andExpect(jsonPath("[1].sourceIdentifier").value(mockedProducts.get(1).getSourceIdentifier()));
    }

    @Test
    public void shouldCreateNew() throws Exception
    {
        Product product = getPersisted();
        ProductDTO productDTO = getRequestProduct();

        given(productService.create(any(ProductDTO.class))).willReturn(product);

        mockMvc
            .perform(MockMvcRequestBuilders.post(CREATE_API)
                .content(new ObjectMapper().writeValueAsBytes(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("id").value(product.getId()))
            .andExpect(jsonPath("name").value(product.getName()))
            .andExpect(jsonPath("description").value(product.getDescription()))
            .andExpect(jsonPath("sourceIdentifier").value(product.getSourceIdentifier()));

        assertThat(product.getName()).isEqualTo(productDTO.getName());
        assertThat(product.getDescription()).isEqualTo(productDTO.getDescription());
        assertThat(product.getSourceIdentifier()).isEqualTo(productDTO.getSourceIdentifier());
    }

    @Test
    public void shouldReturnBadRequest() throws Exception
    {
        Product product = getPersisted();
        ProductDTO productDTO = getRequestProduct();
        productDTO.setId(1);

        given(productService.create(any(ProductDTO.class))).willReturn(product);

        mockMvc
            .perform(MockMvcRequestBuilders.post(CREATE_API)
                .content(new ObjectMapper().writeValueAsBytes(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }
}
