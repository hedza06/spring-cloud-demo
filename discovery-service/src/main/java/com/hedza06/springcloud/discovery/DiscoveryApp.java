package com.hedza06.springcloud.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class DiscoveryApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryApp.class).run(args);
    }

}
