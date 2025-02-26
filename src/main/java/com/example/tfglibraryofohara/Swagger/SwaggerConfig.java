package com.example.tfglibraryofohara.Swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // URL SWAGER: http://localhost:9999/doc/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("library of Ohara API")
                        .description("API de gestión de Libros")
                        .contact(new Contact()
                                .name("Jaime González Bravo")
                                .email("jaime.gonbra@educa.jcyl.es")
                                .url("LibraryOfOharaAPI"))
                       /* .version("1.0")).addSecurityItem(new SecurityRequirement().addList("JavaInUseSecurityScheme"))
                .components(new Components().addSecuritySchemes("JavaInUseSecurityScheme", new SecurityScheme().name("JavaInUseSecurityScheme")
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))*/
                );

    }
}
