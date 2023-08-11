package com.example.wantedboard.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRespDto {

    private final Long id;
    private final String email;

    @Builder
    public LoginRespDto(final Long id, final String email) {
        this.id = id;
        this.email = email;
    }
}
