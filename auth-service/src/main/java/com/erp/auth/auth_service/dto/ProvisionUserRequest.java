package com.erp.auth.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvisionUserRequest {


    private String username;   // enrollment / facultyId/adminId
    private String role;       // STUDENT / FACULTY / ADMIN
    private String tempPassword; // system-generated or admin-set


}
