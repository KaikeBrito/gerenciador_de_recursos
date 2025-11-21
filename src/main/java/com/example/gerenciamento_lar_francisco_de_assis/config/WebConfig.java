package com.example.gerenciamento_lar_francisco_de_assis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a todas as rotas
                    .allowedOrigins(
                        "http://localhost:3000",  // Para testes locais
                        "http://localhost:5173",  // Para testes locais (Vite)
                        "https://lar-stock-manager-production.up.railway.app" // <--- ADICIONE ESTA LINHA (Seu Frontend de Prod)
                    ) 
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "TRACE", "CONNECT") 
                    .allowedHeaders("*") 
                    .allowCredentials(true);
            }
        };
    }
}