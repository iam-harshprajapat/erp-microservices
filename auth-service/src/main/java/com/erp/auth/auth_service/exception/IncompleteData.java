package com.erp.auth.auth_service.exception;

public class IncompleteData extends RuntimeException{
    IncompleteData(String msg){
        super(msg);
    }
}
