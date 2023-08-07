package com.example.wantedboard.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto<T> {
    private final String code; // 1 성공, - 1 실패
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
