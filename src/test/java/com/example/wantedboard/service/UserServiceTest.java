package com.example.wantedboard.service;

import com.example.wantedboard.domain.User;
import com.example.wantedboard.domain.UserRole;
import com.example.wantedboard.exception.AlreadyExistsEmail;
import com.example.wantedboard.exception.InvalidEmail;
import com.example.wantedboard.exception.InvalidPassword;
import com.example.wantedboard.postrepository.UserRepository;
import com.example.wantedboard.request.JoinCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공")
    void 회원가입() {
        //given
        String password = "12341234";
        final String encodedPassword = passwordEncoder.encode(password);

        var joinDto = JoinCreate.builder()
                .email("wanted@wanted.com")
                .password(password)
                .build();

        var user = User.builder()
                .email("wanted@wanted.com")
                .password(encodedPassword)
                .userRole(UserRole.USER)
                .build();

        given(userRepository.findByEmail(any())).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(user);

        //when
        var message = userService.join(joinDto);

        //then
        assertThat(message).isEqualTo("회원가입을 성공하였습니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 존재하는 이메일")
    void 회원가입1() {
        //given
        String password = "1234";

        var joinDto = JoinCreate.builder()
                .email("wanted@wanted.com")
                .password(password)
                .build();

        given(userRepository.findByEmail(any())).willThrow(new AlreadyExistsEmail());

        //when
        AlreadyExistsEmail exception = assertThrows(AlreadyExistsEmail.class, () -> userService.join(joinDto));

        //then
        assertEquals(exception.HttpStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(exception.getMessage(), "이미 가입된 이메일입니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 '@' 미포함")
    void 회원가입2() {
        //given
        String password = "12341234";

        var joinDto = JoinCreate.builder()
                .email("wantedwanted.com")
                .password(password)
                .build();

        //when
        final InvalidEmail invalidEmail = assertThrows(InvalidEmail.class, () -> userService.join(joinDto));

        //then
        assertThat(invalidEmail.HttpStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(invalidEmail.getMessage()).isEqualTo("이메일 조건: '@'가 포함되어야 합니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 8자 이하")
    void 회원가입3() {
        //given
        String password = "1234";

        var joinDto = JoinCreate.builder()
                .email("wanted@wanted.com")
                .password(password)
                .build();

        //when
        final InvalidPassword invalidPassword = assertThrows(InvalidPassword.class, () -> userService.join(joinDto));

        //then
        assertThat(invalidPassword.HttpStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(invalidPassword.getMessage()).isEqualTo("비밀번호 조건: 8자 이상이어야 합니다.");
    }
}