package com.example.wantedboard.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@Builder
public class JoinCreate {

    private final String email;

    private final String password;

}
