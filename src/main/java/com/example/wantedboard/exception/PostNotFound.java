package com.example.wantedboard.exception;

import com.example.wantedboard.util.StatusCode;
import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.*;

public class PostNotFound extends CustomApiException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public PostNotFound() {
        super(MESSAGE);
    }

    @Override
    public String statusCode() {
        return NOT_FOUND.getValue();
    }

    @Override
    public HttpStatus HttpStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
