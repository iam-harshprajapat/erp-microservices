package com.erp.auth.auth_service.controller;

import com.erp.auth.auth_service.service.AuthUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthHealthController {

    private final AuthUserService authUserService;

    public AuthHealthController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/auth/health")
    public String health() {
        long count = authUserService.findByUsername("dummy").isPresent() ? 1 : 0;
        return "Auth Service is UP";
    }
}
