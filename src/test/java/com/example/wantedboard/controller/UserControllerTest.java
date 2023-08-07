package com.example.wantedboard.controller;

import com.example.wantedboard.request.JoinCreate;
import com.example.wantedboard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @MockBean
    UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입() throws Exception {
        //given
        String password = "12345678";

        var joinDto = JoinCreate.builder()
                .email("wanted@wanted.com")
                .password(password)
                .build();

        given(userService.join(any())).willReturn("회원가입을 성공했습니다.");

        //when
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(joinDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("회원가입을 성공했습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 8글자 이하")
    void 회원가입1() throws Exception {
        //given
        String password = "12345";

        var joinDto = JoinCreate.builder()
                .email("wanted@wanted.com")
                .password(password)
                .build();

        given(userService.join(any())).willReturn("회원가입을 성공했습니다.");

        //when
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(joinDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("유효성검사 실패"))
                .andExpect(jsonPath("$.data.password").value("비밀번호 조건: 8자 이상"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 형식이 아님 (@ 미포함)")
    void 회원가입2() throws Exception {
        //given
        String password = "12345678";

        var joinDto = JoinCreate.builder()
                .email("wantedwanted.com")
                .password(password)
                .build();

        given(userService.join(any())).willReturn("회원가입을 성공했습니다.");

        //when
        mockMvc.perform(post("/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(joinDto))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("유효성검사 실패"))
                .andExpect(jsonPath("$.data.email").value("이메일 조건: @ 포함"))
                .andDo(print());
    }

}