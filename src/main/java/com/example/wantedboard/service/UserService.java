package com.example.wantedboard.service;

import com.example.wantedboard.domain.User;
import com.example.wantedboard.domain.UserRole;
import com.example.wantedboard.exception.AlreadyExistsEmail;
import com.example.wantedboard.postrepository.UserRepository;
import com.example.wantedboard.request.JoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String join(JoinDto joinDto) {

        userRepository.findByEmail(joinDto.getEmail()).ifPresent(
                user -> {
                    throw new AlreadyExistsEmail();
                });

        var encodedPassword = passwordEncoder.encode(joinDto.getPassword());

        var user = User.builder()
                .email(joinDto.getEmail())
                .password(encodedPassword)
                .userRole(UserRole.USER)
                .build();

        userRepository.save(user);

        return "회원가입을 성공하였습니다.";
    }

}
