package com.hedza06.springcloud.user.adapter.web;

import com.hedza06.springcloud.user.application.port.in.UserUseCase;
import com.hedza06.springcloud.user.dto.ProductDTO;
import com.hedza06.springcloud.user.dto.UserDTO;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserUseCase userUseCase;


    @GetMapping(value = "/by-product/{productId}")
    public ResponseEntity<List<UserDTO>> byProductSourceIdentifier(@PathVariable String productId)
    {
        log.info("User Context - Request for getting users by product with id: {}", productId);

        List<UserDTO> users = userUseCase.findUsersByProductSourceIdentifier(productId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping(value = "/{email}/assign-product")
    public ResponseEntity<Void> assignProductToUser(@RequestBody ProductDTO productDTO, @PathVariable String email)
    {
        userUseCase.assignProductToUser(productDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/simulate/internal-server-error")
    public ResponseEntity<Void> simulateInternalServerError()
    {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/simulate/bad-request-error")
    public ResponseEntity<Map<String, Object>> simulateBadRequestError()
    {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("validationMessage", "Some field is required");
        errorResponse.put("field", "Some field name");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
