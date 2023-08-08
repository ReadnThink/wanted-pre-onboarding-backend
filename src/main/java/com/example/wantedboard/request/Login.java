package com.example.wantedboard.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
public class Login {

    @Email(message = "이메일 조건: @ 포함")
    private final String email;

    @Size(min = 8, message = "비밀번호 조건: 8자 이상")
    private final String password;

}
