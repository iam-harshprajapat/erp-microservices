package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.entity.AuthUser;
import com.erp.auth.auth_service.repository.AuthUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Override
    public String provisionUser(ProvisionUserRequest request) {
        return "";
    }

//    @Override
//    public Optional<AuthUser> findByUsername(String username) {
//        return authUserRepository.findByUsername(username);
//    }
//
//    @Override
//    public AuthUser save(AuthUser authUser) {
//        return authUserRepository.save(authUser);
//    }
}
