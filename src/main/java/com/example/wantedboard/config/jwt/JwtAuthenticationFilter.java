package com.example.wantedboard.config.jwt;

import com.example.wantedboard.config.auth.LoginUser;
import com.example.wantedboard.domain.User;
import com.example.wantedboard.request.LoginRequest;
import com.example.wantedboard.response.LoginRespDto;
import com.example.wantedboard.util.CustomResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            ObjectMapper om = new ObjectMapper();
            LoginRequest loginRequest = om.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        }catch (Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        CustomResponseUtil.fail(response,"로그인실패", HttpStatus.UNAUTHORIZED);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authResult.getPrincipal();
        User user = loginUser.getUser();
        String jwtToken = JwtProcess.create(loginUser);
        response.addHeader(JwtVO.HEADER, jwtToken);

        LoginRespDto loginRespDto = LoginRespDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        CustomResponseUtil.success(response,loginRespDto);
    }

}
