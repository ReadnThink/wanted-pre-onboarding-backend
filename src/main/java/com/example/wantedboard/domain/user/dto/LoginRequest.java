package com.example.wantedboard.domain.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginRequest {

    private String email;
    private String password;

}


