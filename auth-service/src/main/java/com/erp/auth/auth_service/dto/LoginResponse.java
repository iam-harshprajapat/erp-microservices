package com.erp.auth.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String success;
    private String message;
    private String token;
}
