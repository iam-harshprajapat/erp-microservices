package com.erp.auth.auth_service.dto;


public class ErrorResponse {
    private boolean success;
    private String message;

    public ErrorResponse(boolean success, String message)
    {
        this.success=success;
        this.message=message;
    }
    public void setSuccess(boolean success)
    {
        this.success=success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }

}
