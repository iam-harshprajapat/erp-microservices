package com.erp.auth.auth_service.controller;

import com.erp.auth.auth_service.dto.BulkResponse;
import com.erp.auth.auth_service.dto.ProvisionUserRequest;
import com.erp.auth.auth_service.dto.ProvisionUserResponse;
import com.erp.auth.auth_service.service.AuthUserService;
import com.erp.auth.auth_service.service.BulkUserCreate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
public class AuthProvisionController {

    private final AuthUserService authUserService;
    private final BulkUserCreate bulkUserCreate;

    public AuthProvisionController(AuthUserService authUserService,BulkUserCreate bulkUserCreate) {
        this.authUserService = authUserService;
        this.bulkUserCreate = bulkUserCreate;
    }

    /**
     * ADMIN-ONLY (later secured)
     * Provisions credentials for a user (student/faculty/admin)
     */
    @PostMapping("/provision")
    public ResponseEntity<ProvisionUserResponse> provisionUser(@RequestBody ProvisionUserRequest request) {
         String result=null;
         result = authUserService.provisionUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ProvisionUserResponse(true,result));

    }
    @PostMapping("/provision/bulk")
    public ResponseEntity<BulkResponse> bulkProvision(@RequestParam("file") MultipartFile file)
    {
        BulkResponse response= bulkUserCreate.bulkProvisionUsers(file);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
