package com.hedza06.springcloud.user.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hedza06.springcloud.user.controllers.UserController;
import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.services.UserService;
import com.hedza06.springcloud.user.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackageClasses = { KeycloakSecurityComponents.class, KeycloakSpringBootConfigResolver.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    private static final String IS_ALIVE_API = "/user/is-alive";
    private static final String GET_USERS_BY_PRODUCT_API = "/user/by-product/";
    private static final String SIMULATE_INTERNAL_SERVER_ERROR_API = "/user/simulate/internal-server-error";
    private static final String SIMULATE_BAD_REQUEST_ERROR_API = "/user/simulate/bad-request-error";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void shouldReturnUsersByProductIdentifier() throws Exception
    {
        List<UserDTO> mockedUsers = TestUtils.getMockedUsers();
        when(userService.findByProductSource(anyString())).thenReturn(mockedUsers);

        mockMvc
            .perform(MockMvcRequestBuilders.get(GET_USERS_BY_PRODUCT_API.concat("P-001")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].id").value(mockedUsers.get(0).getId()))
            .andExpect(jsonPath("[0].fullName").value(mockedUsers.get(0).getFullName()))
            .andExpect(jsonPath("[0].productSourceIdentifier").value(mockedUsers.get(0).getProductSourceIdentifier()))
            .andExpect(jsonPath("[1].id").value(mockedUsers.get(1).getId()))
            .andExpect(jsonPath("[1].fullName").value(mockedUsers.get(1).getFullName()))
            .andExpect(jsonPath("[1].productSourceIdentifier").value(mockedUsers.get(1).getProductSourceIdentifier()))
            .andExpect(jsonPath("[2].id").value(mockedUsers.get(2).getId()))
            .andExpect(jsonPath("[2].fullName").value(mockedUsers.get(2).getFullName()))
            .andExpect(jsonPath("[2].productSourceIdentifier").value(mockedUsers.get(2).getProductSourceIdentifier()));
    }

    @Test
    public void shouldAssignGivenProductToGivenUserAndReturnOnlyStatus() throws Exception
    {
        ProductDTO productDTO = TestUtils.getRequestProduct();
        productDTO.setId(1);

        doNothing().when(userService).assignProductToUser(anyString(), anyString());

        mockMvc
            .perform(MockMvcRequestBuilders.put("/user/test@email.com/assign-product")
                .content(objectMapper.writeValueAsBytes(productDTO))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnInternalServerErrorAfterSimulationCall() throws Exception
    {
        mockMvc
            .perform(MockMvcRequestBuilders.get(SIMULATE_INTERNAL_SERVER_ERROR_API))
            .andExpect(status().isInternalServerError());
    }

    @Test
    public void shouldReturnBadRequestAfterSimulationCall() throws Exception
    {
        mockMvc
            .perform(MockMvcRequestBuilders.get(SIMULATE_BAD_REQUEST_ERROR_API))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("validationMessage").value("Some field is required"))
            .andExpect(jsonPath("field").value("Some field name"));
    }

    @Test
    public void shouldBeAlive() throws Exception
    {
        mockMvc
            .perform(MockMvcRequestBuilders.get(IS_ALIVE_API))
            .andExpect(status().isOk());
    }
}
