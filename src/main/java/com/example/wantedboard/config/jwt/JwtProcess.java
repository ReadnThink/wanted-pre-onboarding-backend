package com.example.wantedboard.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.wantedboard.config.auth.LoginUser;
import com.example.wantedboard.domain.User;
import com.example.wantedboard.domain.UserRole;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtProcess {

    public static String create(LoginUser loginUser){

        String jwtToken = JWT.create()
                .withSubject("wanted")
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtVO.EXPIRATION_TIM))
                .withClaim("id", loginUser.getUser().getId())
                .withClaim("role", loginUser.getUser().getUserRole().name())
                .sign(Algorithm.HMAC512(JwtVO.SECRET));

        return JwtVO.TOKEN_PREFIX+jwtToken;
    }

    public static LoginUser verify(String token){

        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JwtVO.SECRET)).build().verify(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String role = decodedJWT.getClaim("role").asString();
        User user = User.builder().id(id).userRole(UserRole.valueOf(role)).build();
        LoginUser loginUser = new LoginUser(user);

        return loginUser;
    }
}
