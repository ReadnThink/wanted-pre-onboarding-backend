package com.example.wantedboard.domain.user.exception;

import com.example.wantedboard.global.exception.CustomApiException;
import org.springframework.http.HttpStatus;

import static com.example.wantedboard.global.util.StatusCode.FORBIDDEN;

public class UserNotMatch extends CustomApiException {

    private static final String MESSAGE = "게시글을 수정/삭제는 게시글 작성자만 가능합니다.";

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
