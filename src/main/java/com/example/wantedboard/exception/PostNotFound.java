package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

public class PostNotFound extends CustomApiException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public String statusCode() {
        return "404";
    }

    @Override
    public HttpStatus HttpStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
