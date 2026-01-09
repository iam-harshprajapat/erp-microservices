package com.erp.auth.auth_service.repository;

import com.erp.auth.auth_service.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
    Optional<AuthUser> findByUsername(String username);
}
