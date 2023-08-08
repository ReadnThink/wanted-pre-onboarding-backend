package com.example.wantedboard.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import static com.example.wantedboard.util.StatusCode.FORBIDDEN;

public class JwtException extends CustomApiException{


    public JwtException(String message) {
        super(message);
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
