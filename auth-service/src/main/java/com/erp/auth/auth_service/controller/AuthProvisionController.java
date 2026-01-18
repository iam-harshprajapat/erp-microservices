package com.erp.auth.auth_service.controller;

import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.dto.ProvisionUserResponse;
import com.erp.auth.auth_service.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProvisionUserResponse> provisionUser(@RequestBody ProvisionUserRequest request) {
        String result=null;
        try{
             result = authUserService.provisionUser(request);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ProvisionUserResponse("User Already Exist"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ProvisionUserResponse(result));

    }
}
