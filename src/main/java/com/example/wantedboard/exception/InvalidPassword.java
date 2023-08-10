package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.BAD_REQUEST;

public class InvalidPassword extends CustomApiException{

    private static final String MESSAGE = "비밀번호 조건: 8자 이상이어야 합니다.";

    public InvalidPassword() {
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
