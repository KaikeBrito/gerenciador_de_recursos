package com.example.gerenciamento_lar_francisco_de_assis.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP) // Usamos HTTP
                        .scheme("bearer")               // O esquema é 'bearer'
                        .bearerFormat("JWT")            // O formato é 'JWT'
                )
            )
            // 2. Adiciona o "cadeado" de segurança global a todos os endpoints
            .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
            
            // 3. Define as informações gerais da sua API
            .info(new Info()
                .title("API de Gerenciamento - Lar Francisco de Assis")
                .version("v1.0.0")
                .description("API REST para o Sistema de Gerenciamento de Recursos (SGR) " +
                             "da ONG Lar Francisco de Assis.")
            );
    }
}