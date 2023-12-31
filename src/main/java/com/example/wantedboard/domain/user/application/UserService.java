package com.example.wantedboard.domain.user.application;

import com.example.wantedboard.domain.user.entity.User;
import com.example.wantedboard.domain.user.UserRole;
import com.example.wantedboard.domain.user.exception.AlreadyExistsEmail;
import com.example.wantedboard.domain.user.dao.UserRepository;
import com.example.wantedboard.domain.user.dto.JoinCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String join(JoinCreate joinCreate) {
        validateEmailExist(joinCreate);
        var user = User.builder()
                .email(joinCreate.getEmail())
                .password(joinCreate.getPassword())
                .userRole(UserRole.USER)
                .build();

        user.validateJoinCreate();
        user.encodePassword(passwordEncoder);

        userRepository.save(user);

        return "회원가입을 성공하였습니다.";
    }

    private void validateEmailExist(final JoinCreate joinCreate) {
        userRepository.findByEmail(joinCreate.getEmail()).ifPresent(user -> {
                    throw new AlreadyExistsEmail();
                });
    }

}
