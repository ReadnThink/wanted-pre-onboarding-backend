package com.example.wantedboard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    SUCCESS("200"), BAD_REQUEST("400"), NOT_FOUND("404");

    private String value;
}
