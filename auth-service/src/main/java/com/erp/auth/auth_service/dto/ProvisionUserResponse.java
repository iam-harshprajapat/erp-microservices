package com.erp.auth.auth_service.dto;

public class ProvisionUserResponse {

    private String message;

    public ProvisionUserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
