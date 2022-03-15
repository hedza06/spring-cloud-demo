package com.hedza06.springcloud.product.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "custom-auth")
public class KeycloakCredentialsConfig {

    private String url;
    private String clientId;
    private String clientUsername;
    private String clientPassword;

}
