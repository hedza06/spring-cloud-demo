package com.hedza06.springcloud.gateway;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApp {

    @Autowired
    RouteDefinitionLocator locator;

    public static void main(String[] args) {
        new SpringApplicationBuilder(GatewayApp.class).run(args);
    }

    @Bean
    public List<GroupedOpenApi> apis()
    {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        assert definitions != null;
        definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
            .forEach(routeDefinition -> {
                String name = routeDefinition.getId().replaceAll("-service", "");
                groups.add(GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build());
            }
        );
        return groups;
    }
}
