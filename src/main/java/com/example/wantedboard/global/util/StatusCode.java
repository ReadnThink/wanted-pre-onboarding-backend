package com.example.wantedboard.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS("200"), BAD_REQUEST("400"), NOT_FOUND("404"), FORBIDDEN("403");

    private final String value;
}
