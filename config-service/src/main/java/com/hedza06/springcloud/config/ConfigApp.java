package com.hedza06.springcloud.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ConfigApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigApp.class).run(args);
    }
}
