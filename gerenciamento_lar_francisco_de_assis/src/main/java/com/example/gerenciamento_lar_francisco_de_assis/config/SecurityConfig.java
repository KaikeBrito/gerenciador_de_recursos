package com.example.gerenciamento_lar_francisco_de_assis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    // Injeta nosso CustomUserDetailsService
    private final UserDetailsService userDetailsService; 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Este é o "Provedor de Autenticação" que diz ao Spring Security:
        // 1. Como encontrar o usuário (usando userDetailsService)
        // 2. Como verificar a senha (usando passwordEncoder)
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Expõe o AuthenticationManager como um Bean para usarmos no AuthController
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desabilita CSRF (padrão para APIs stateless)
            .csrf(csrf -> csrf.disable())
            
            // 2. Define as regras de autorização
            .authorizeHttpRequests(authorize -> authorize
                // Permite acesso público aos endpoints de login E REGISTRO
                .requestMatchers("/api/v1/auth/login").permitAll()
                .requestMatchers("/api/v1/auth/register").permitAll() // <-- ESTA É A LINHA QUE FALTAVA
                .requestMatchers("/h2-console/**").permitAll()
                
                // Exige autenticação para todos os outros endpoints
                .anyRequest().authenticated()
            )
            
            // 3. Define a política de sessão como STATELESS (sem estado)
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // 4. Diz ao Spring para usar nosso provedor de autenticação
            .authenticationProvider(authenticationProvider())

            // 5. Adiciona nosso filtro JWT *antes* do filtro padrão de login
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            
            // 6. (Necessário para o H2 Console funcionar em navegadores)
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}