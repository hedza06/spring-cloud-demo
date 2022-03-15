package com.hedza06.springcloud.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry
            .addMapping("/product/**")
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins("*")
            .exposedHeaders("cache-control,content-length,content-type,expires,pragma");
    }
}
