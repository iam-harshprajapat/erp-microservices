package com.erp.auth.auth_service.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String s)
    {
        super(s);
    }
}
