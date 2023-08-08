package com.example.wantedboard.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserUrl {

    USER_URL("/join");

    private final String value;
}
