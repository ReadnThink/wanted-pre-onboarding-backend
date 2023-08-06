package com.example.wantedboard.aop;

public class CustomApiException extends RuntimeException{
    public CustomApiException(String message){
        super(message);
    }
}

