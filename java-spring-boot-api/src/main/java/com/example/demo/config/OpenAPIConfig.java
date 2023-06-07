package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Java SpringBoot RESTful API")
                        .version("v1")
                        .description("Description to be defined")
                        .termsOfService("https://com.example.demo.com/java-api-course")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://com.example.demo.com/java-api-course")));
    }
}
