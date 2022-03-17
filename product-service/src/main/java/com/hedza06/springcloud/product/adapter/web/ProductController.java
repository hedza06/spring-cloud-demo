package com.hedza06.springcloud.product.adapter.web;

import com.hedza06.springcloud.product.application.port.in.ProductUseCase;
import com.hedza06.springcloud.product.application.port.in.UserClientIntegrationUseCase;
import com.hedza06.springcloud.product.domain.Product;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductUseCase productUseCase;
    private final UserClientIntegrationUseCase userClientIntegrationUseCase;


    @GetMapping(value = "/all")
    public ResponseEntity<List<ProductDTO>> all()
    {
        List<ProductDTO> products = productUseCase.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/{productId}/users")
    public ResponseEntity<List<UserDTO>> byProductSourceIdentifier(@PathVariable String productId)
    {
        log.info("Product Context - Request for getting users by product with id: {}", productId);
        log.info("Sending request using feign client to get users by product identifier...");

        List<UserDTO> users = userClientIntegrationUseCase.findUsersByProductId(productId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO product)
    {
        Product storedProduct = productUseCase.create(product);
        return new ResponseEntity<>(storedProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/assign-to-user/{email}")
    public ResponseEntity<Void> assignToUser(@RequestBody ProductDTO product, @PathVariable String email)
    {
        userClientIntegrationUseCase.assignProductToUserWithEmailAddress(product, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
