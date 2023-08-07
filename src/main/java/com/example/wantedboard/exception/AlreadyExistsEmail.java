package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.BAD_REQUEST;
import static com.example.wantedboard.util.StatusCode.NOT_FOUND;

public class AlreadyExistsEmail extends CustomApiException{

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmail() {
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
