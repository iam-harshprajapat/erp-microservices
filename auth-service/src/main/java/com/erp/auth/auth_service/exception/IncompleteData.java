package com.erp.auth.auth_service.exception;

public class IncompleteData extends RuntimeException{
    public IncompleteData(String msg){
        super(msg);
    }
}
