package com.api.presupuesto.api_presupuesto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Gestión de Presupuestos")
                .version("1.0.0")
                .description("Esta API permite gestionar presupuestos de proyectos. Incluye operaciones CRUD básicas.")
                .contact(new Contact()
                    .name("Yurgen Prado Lopez")
                    .email("yurgen2616@hotmail.com"))
                .license(new License()
                    .name("Licencia MIT")
                    .url("https://opensource.org/licenses/MIT")));
    }
}