package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.ProvisionUserRequest;

public interface AuthUserService {

    String provisionUser(ProvisionUserRequest request);

//    String login(com.erp.auth.auth_service.dto.LoginRequest request);
}
