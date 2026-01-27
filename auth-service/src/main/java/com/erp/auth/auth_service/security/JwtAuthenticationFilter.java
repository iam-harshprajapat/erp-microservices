package com.erp.auth.auth_service.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    public JwtAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.info("JWT FILTER HIT = {}", request.getRequestURI());
        String authHeader = request.getHeader("Authorization");

        // 1️⃣ Check Authorization header
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                // 2️⃣ Extract data from token
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                // 3️⃣ Create authentication object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                // 4️⃣ Set authentication in context
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                // Invalid token → clear context
                SecurityContextHolder.clearContext();
            }
        }

        // 5️⃣ Continue request
        filterChain.doFilter(request, response);
    }
}
