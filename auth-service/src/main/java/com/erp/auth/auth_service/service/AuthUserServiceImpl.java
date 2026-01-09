package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.config.SecurityConfig;
import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.entity.AuthUser;
import com.erp.auth.auth_service.repository.AuthUserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.erp.auth.auth_service.entity.Role;
import com.erp.auth.auth_service.entity.Status;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;
    private final  BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authUserRepository = authUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String provisionUser(ProvisionUserRequest request) {

        if (request.getUsername() == null ||
                request.getRole() == null ||
                request.getTempPassword() == null) {
            throw new RuntimeException("Username, role and temporary password are required");
        }

        if (authUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already provisioned");
        }

        try{
            String hashedPassword = bCryptPasswordEncoder.encode(request.getTempPassword());

            AuthUser user = new AuthUser();
            user.setUsername(request.getUsername());
            user.setPasswordHash(hashedPassword);
            user.setRole(Role.valueOf(request.getRole()));
            user.setStatus(Status.ACTIVE);
            user.setCreatedAt(Instant.now());

            authUserRepository.save(user);
        } catch (Exception e) {
            log.error("INTERNAL SERVER ERROR:",e);
            throw new RuntimeException(e);
        }
        return "User provisioned successfully";
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
