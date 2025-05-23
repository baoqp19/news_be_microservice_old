package com.example.notification_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("NOTIFICATION-SERVICE API")
                        .description("API Documentation's NOTIFICATION-SERVICE")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("phamquocbao2k5")
                                .url("https://phamquocbao2k5.github.io/")
                                .email("phamquocbao@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}