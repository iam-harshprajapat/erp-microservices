package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.BulkResponse;
import com.erp.auth.auth_service.dto.LoginRequest;
import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.entity.AuthUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AuthUserService {

    String provisionUser(ProvisionUserRequest request);
    Optional<AuthUser> findByUsername(String username);
    String login(LoginRequest request);

}
