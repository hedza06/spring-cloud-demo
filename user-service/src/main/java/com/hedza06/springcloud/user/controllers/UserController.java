package com.hedza06.springcloud.user.controllers;

import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
import com.hedza06.springcloud.user.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<String> test()
    {
        return new ResponseEntity<>("Happy coding from user world!", HttpStatus.OK);
    }

    @GetMapping(value = "/by-product/{productId}")
    public ResponseEntity<List<UserDTO>> byProductSourceIdentifier(@PathVariable String productId)
    {
        log.info("User Context - Request for getting users by product with id: {}", productId);

        List<UserDTO> users = userService.findByProductSource(productId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/user/{email}/assign-product")
    public ResponseEntity<Void> assignProductToUser(@RequestBody ProductDTO productDTO, @PathVariable String email)
    {
        userService.assignProductToUser(productDTO.getSourceIdentifier(), email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
