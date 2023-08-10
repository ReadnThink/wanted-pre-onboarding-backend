package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.BAD_REQUEST;

public class InvalidEmail extends CustomApiException{

    private static final String MESSAGE = "이메일 조건: '@'가 포함되어야 합니다.";

    public InvalidEmail() {
        super(MESSAGE);
    }

    @Override
    public String statusCode() {
        return BAD_REQUEST.getValue();
    }

    @Override
    public HttpStatus HttpStatusCode() {
        return HttpStatus.BAD_REQUEST;
    }
}
