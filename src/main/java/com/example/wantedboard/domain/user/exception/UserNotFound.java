package com.example.wantedboard.domain.user.exception;

import com.example.wantedboard.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

import static com.example.wantedboard.global.util.StatusCode.NOT_FOUND;

public class UserNotFound extends CustomApiException {

    private static final String MESSAGE = "존재하지 않는 이메일 입니다.";

    public UserNotFound() {
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
