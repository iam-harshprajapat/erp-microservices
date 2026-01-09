package com.erp.auth.auth_service.dto;

public class ProvisionUserRequest {

    private String username;   // enrollment / facultyId/adminId
    private String role;       // STUDENT / FACULTY / ADMIN
    private String tempPassword; // system-generated or admin-set

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
}
