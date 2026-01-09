package com.erp.auth.auth_service.config;

import com.erp.auth.auth_service.repository.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConnectionTestRunner {

    @Bean
    CommandLineRunner testDatabaseConnection(AuthUserRepository repository) {
        return args -> {
            long count = repository.count();
            System.out.println("✅ Database connection successful. AuthUser count = " + count);
        };
    }
}
