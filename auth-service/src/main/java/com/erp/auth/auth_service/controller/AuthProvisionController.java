package com.erp.auth.auth_service.controller;

import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.dto.ProvisionUserResponse;
import com.erp.auth.auth_service.service.AuthUserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
public class AuthProvisionController {

    private final AuthUserService authUserService;

    public AuthProvisionController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    /**
     * ADMIN-ONLY (later secured)
     * Provisions credentials for a user (student/faculty/admin)
     */
    @PostMapping("/provision")
    public ProvisionUserResponse provisionUser(@RequestBody ProvisionUserRequest request) {
        String result = authUserService.provisionUser(request);
        return new ProvisionUserResponse(result);
    }
}
