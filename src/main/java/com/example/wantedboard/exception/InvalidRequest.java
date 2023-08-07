package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

public class InvalidRequest extends CustomApiException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public String statusCode() {
        return "400";
    }

    @Override
    public HttpStatus HttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}