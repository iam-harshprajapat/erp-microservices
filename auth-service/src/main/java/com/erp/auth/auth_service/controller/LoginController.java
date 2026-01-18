package com.erp.auth.auth_service.controller;

import com.erp.auth.auth_service.dto.LoginRequest;
import com.erp.auth.auth_service.dto.LoginResponse;
import com.erp.auth.auth_service.service.AuthUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    public AuthUserService authUserService;

    public LoginController(AuthUserService authUserService){
        this.authUserService= authUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginController(@RequestBody LoginRequest request){
         String token = authUserService.login(request);
         return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse("true","Login Successfully",token));
    }
}
