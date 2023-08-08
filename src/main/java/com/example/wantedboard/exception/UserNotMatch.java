package com.example.wantedboard.exception;

import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.FORBIDDEN;
import static com.example.wantedboard.util.StatusCode.NOT_FOUND;

public class UserNotMatch extends CustomApiException{

    private static final String MESSAGE = "게시글을 수정할 수 있는 사용자는 게시글 작성자만이어야 합니다.";

    public UserNotMatch() {
        super(MESSAGE);
    }

    @Override
    public String statusCode() {
        return FORBIDDEN.getValue();
    }

    @Override
    public HttpStatus HttpStatusCode() {
        return HttpStatus.FORBIDDEN;
    }

}
