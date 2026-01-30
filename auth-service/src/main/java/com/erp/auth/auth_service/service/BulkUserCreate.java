package com.erp.auth.auth_service.service;

import com.erp.auth.auth_service.dto.BulkResponse;
import org.springframework.web.multipart.MultipartFile;

public interface BulkUserCreate {
    public BulkResponse bulkProvisionUsers(MultipartFile file);
}
