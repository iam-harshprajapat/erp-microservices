package com.erp.auth.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleInvalidCredentials(InvalidCredentialsException ex) {
        Map<String,String> response=new LinkedHashMap<>();
        response.put("success","false");
        response.put("message",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(UserNotActiveException.class)
    public ResponseEntity<Map<String,String>>handleUserNotActive(UserNotActiveException ex) {
        Map <String,String>response=new LinkedHashMap<>();
        response.put("success","false");
        response.put("message",ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @ExceptionHandler(IncompleteData.class)
    public ResponseEntity<Map<String,String>>handleIncompleteData(IncompleteData e)
    {
        Map<String,String> response=new LinkedHashMap<>();
        response.put("success","false");
        response.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>>handleGeneric(Exception ex) {
        Map<String,String> response=new LinkedHashMap<>();
        response.put("success","false");
        response.put("message","Internal Server Error");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
