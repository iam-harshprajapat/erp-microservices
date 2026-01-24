package com.erp.auth.auth_service.exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String s)
    {
        super(s);
    }
}
