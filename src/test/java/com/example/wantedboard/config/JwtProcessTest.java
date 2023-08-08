package com.example.wantedboard.config;

import com.example.wantedboard.config.auth.LoginUser;
import com.example.wantedboard.config.jwt.JwtProcess;
import com.example.wantedboard.config.jwt.JwtVO;
import com.example.wantedboard.domain.User;
import com.example.wantedboard.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtProcessTest {

    private String createToken(){
        //given
        User user = User.builder().id(1L).userRole(UserRole.USER).build();
        LoginUser loginUser = new LoginUser(user);

        //when
        String jwtToken = JwtProcess.create(loginUser);
        return jwtToken;
    }

    @Test
    @DisplayName("jwt Create 테스트")
    void test() {

        //when
        final String jwt = createToken();

        //then
        assertTrue(jwt.startsWith(JwtVO.TOKEN_PREFIX));
    }

    @Test
    @DisplayName("jwt Verify 테스트")
    void test1() {
        //given
        final String bearerKwt = createToken();
        final String jwt = bearerKwt.replace(JwtVO.TOKEN_PREFIX, "");

        //when
        final LoginUser loginUser = JwtProcess.verify(jwt);

        //then
        assertThat(loginUser.getUser().getId()).isEqualTo(1L);
        assertThat(loginUser.getUser().getUserRole()).isEqualTo(UserRole.USER);
    }

}