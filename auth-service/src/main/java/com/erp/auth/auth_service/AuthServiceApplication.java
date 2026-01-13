package com.erp.auth.auth_service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}
    @PostConstruct
    public void debugDb() {
        System.out.println("DB_URL=" + System.getenv("DB_URL"));
        System.out.println("DB_USER=" + System.getenv("DB_USERNAME"));
        System.out.println("DB_PASSWORD=" + (System.getenv("DB_PASSWORD") != null));
    }
}
