package com.hedza06.springcloud.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProductApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductApp.class, args);
    }

}
