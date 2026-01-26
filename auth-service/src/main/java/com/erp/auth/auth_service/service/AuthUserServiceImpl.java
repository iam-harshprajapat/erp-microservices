package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.BulkResponse;
import com.erp.auth.auth_service.dto.LoginRequest;
import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.entity.AuthUser;
import com.erp.auth.auth_service.exception.IncompleteData;
import com.erp.auth.auth_service.exception.InvalidCredentialsException;
import com.erp.auth.auth_service.exception.UserAlreadyExist;
import com.erp.auth.auth_service.exception.UserNotActiveException;
import com.erp.auth.auth_service.repository.AuthUserRepository;
import com.erp.auth.auth_service.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.erp.auth.auth_service.entity.Role;
import com.erp.auth.auth_service.entity.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthUserServiceImpl implements AuthUserService {

    private final  JwtUtil jwtUtil;
    private final AuthUserRepository authUserRepository;
    private final  BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthUserServiceImpl(AuthUserRepository authUserRepository,
                               BCryptPasswordEncoder bCryptPasswordEncoder,JwtUtil jwtUtil) {
        this.authUserRepository = authUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public String provisionUser(ProvisionUserRequest request) {

        if (request.getUsername() == null ||
                request.getRole() == null ||
                request.getTempPassword() == null) {
            throw new IncompleteData("Username, role and temporary password are required");
        }

        if (authUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExist("User already provisioned");
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


    @Override
    public Optional<AuthUser> findByUsername(String username) {
        return authUserRepository.findByUsername(username);
    }

    @Override
    public String login(LoginRequest request) {


        AuthUser user = authUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (user.getStatus() != Status.ACTIVE) {
            throw new UserNotActiveException("User is not active");
        }

        return jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name()
        );
    }

}

