package com.hedza06.springcloud.product.controllers;

import com.hedza06.springcloud.product.clients.dto.UserDTO;
import com.hedza06.springcloud.product.dto.ProductDTO;
import com.hedza06.springcloud.product.entities.Product;
import com.hedza06.springcloud.product.services.ProductService;
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
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;


    @GetMapping
    public ResponseEntity<String> test()
    {
        return new ResponseEntity<>("Happy coding from product world!", HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ProductDTO>> all()
    {
        List<ProductDTO> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/{productId}/users")
    public ResponseEntity<List<UserDTO>> byProductSourceIdentifier(@PathVariable String productId)
    {
        log.info("Product Context - Request for getting users by product with id: {}", productId);
        log.info("Sending request using feign client to get users by product identifier...");

        List<UserDTO> users = productService.findUsersByProductId(productId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody ProductDTO product)
    {
        Product storedProduct = productService.create(product);
        return new ResponseEntity<>(storedProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/assign-to-user/{email}")
    public ResponseEntity<Void> assignToUser(@RequestBody ProductDTO product, @PathVariable String email)
    {
        productService.assignToUser(product, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/simulate/internal-server-error")
    public ResponseEntity<Void> simulateInternalServerErrorFromUser()
    {
        productService.simulateInternalServerError();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/simulate/bad-request-error")
    public ResponseEntity<Map<String, Object>> simulateBadRequestErrorFromUser()
    {
        Map<String, Object> errorResponse = productService.simulateBadRequestError();
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
