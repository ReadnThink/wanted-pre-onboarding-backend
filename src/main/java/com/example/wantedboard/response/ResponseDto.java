package com.example.wantedboard.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private final Integer code; // 1 성공, - 1 실패
    private final String message;
    private final T data;

    @Builder
    public ResponseDto(final Integer code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
