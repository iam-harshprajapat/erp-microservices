package com.erp.auth.auth_service.dto;

import java.util.List;

public class BulkResponse {
    private String success;
    private int totalRecords;
    private int inserted;
    private int skipped;
    private List<String> errors;

    public BulkResponse(String success, int totalRecords, int inserted, int skipped, List<String> errors) {
        this.success = success;
        this.totalRecords = totalRecords;
        this.inserted = inserted;
        this.skipped = skipped;
        this.errors = errors;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return this.success;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalRecords() {
        return this.totalRecords;
    }

    public void setInserted(int inserted) {
        this.inserted = inserted;
    }

    public int getInserted() {
        return this.inserted;
    }

    public void setSkipped(int skipped)
    {
        this.skipped=skipped;
    }
    public int getSkipped()
    {
        return this.skipped;
    }
    public void setErrors(List<String> errors)
    {
        this.errors=errors;
    }
    public List<String> getErrors()
    {
        return this.errors;
    }
}
