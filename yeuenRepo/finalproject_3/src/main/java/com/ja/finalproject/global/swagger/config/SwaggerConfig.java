package com.ja.finalproject.global.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        License license = new License();
        license.setName("testAPI");

        Info info = new Info()
                .title("\"testAPI Document\"")
                .description("testAPI 문서 입니다.")
                .version("v0.0.1")
                .license(license);

        // 두 개의 서버를 Swagger에 추가
        return new OpenAPI()
                .info(info); // 두 개의 서버 추가

    }
}
