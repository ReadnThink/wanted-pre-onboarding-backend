package com.example.wantedboard.util;

import com.example.wantedboard.response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;

import static com.example.wantedboard.util.StatusCode.BAD_REQUEST;
import static com.example.wantedboard.util.StatusCode.SUCCESS;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomResponseUtil {

    public static void success(HttpServletResponse response, Object dto) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(SUCCESS.getValue(), "로그인 성공", dto);
            String responseBody = om.writeValueAsString(responseDto);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }
    public static void fail(HttpServletResponse response, String msg, HttpStatus httpStatus) {
        try {
            ObjectMapper om = new ObjectMapper();
            ResponseDto<?> responseDto = new ResponseDto<>(BAD_REQUEST.getValue(), msg, null);
            String responseBody = om.writeValueAsString(responseDto);
            response.setCharacterEncoding(UTF_8.name());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(httpStatus.value());
            response.getWriter().println(responseBody);
        } catch (Exception e) {
            log.error("서버 파싱 에러");
        }
    }

}
