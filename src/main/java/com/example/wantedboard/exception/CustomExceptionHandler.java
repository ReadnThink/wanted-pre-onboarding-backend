package com.example.wantedboard.exception;

import com.example.wantedboard.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(e.statusCode(),e.getMessage(),null), e.HttpStatusCode());
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.info("디버그 : {}",e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>("400", e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
