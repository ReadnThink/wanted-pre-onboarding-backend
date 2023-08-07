package com.example.wantedboard.config.auth;

import com.example.wantedboard.domain.User;
import com.example.wantedboard.postrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userPs = userRepository.findByEmail(username).orElseThrow(
                () -> new InternalAuthenticationServiceException("인증 실패")
        );
        return new LoginUser(userPs);
    }
}
