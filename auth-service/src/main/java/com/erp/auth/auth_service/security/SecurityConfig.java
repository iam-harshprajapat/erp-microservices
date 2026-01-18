package com.erp.auth.auth_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 1️⃣ Disable CSRF (not needed for REST APIs)
                .csrf(csrf -> csrf.disable())

                // 2️⃣ Authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/health").permitAll()
                        .requestMatchers("/auth/provision").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 3️⃣ Add JWT filter before Spring's auth filter
                .addFilterBefore(
                        new JwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
