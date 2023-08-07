package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomApiException extends RuntimeException{
    public CustomApiException(String message){
        super(message);
    }

    public abstract String statusCode();
    public abstract HttpStatus HttpStatusCode();
}


