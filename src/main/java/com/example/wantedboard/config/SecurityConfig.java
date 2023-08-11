package com.example.wantedboard.config;

import com.example.wantedboard.config.jwt.JwtAuthenticationFilter;
import com.example.wantedboard.config.jwt.JwtAuthorizationFilter;
import com.example.wantedboard.domain.UserRole;
import com.example.wantedboard.util.CustomResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            builder.addFilter(new JwtAuthenticationFilter(authenticationManager, secretKey));
            builder.addFilter(new JwtAuthorizationFilter(authenticationManager, secretKey));
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.csrf().disable();
        http.cors().configurationSource(configurationSource());
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable();
        http.apply(new CustomSecurityFilterManager());

        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.fail(response, "로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
        });

        http.exceptionHandling().accessDeniedHandler((request, response, e) ->
                CustomResponseUtil.fail(response, "권한이 없습니다.", HttpStatus.FORBIDDEN)
        );

        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").hasRole("" + UserRole.ADMIN)
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그 : configurationSource cors 설정이 SecurityFilterChain에 등록됨");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
