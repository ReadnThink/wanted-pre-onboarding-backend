package com.example.wantedboard.controller;

import com.example.wantedboard.request.JoinDto;
import com.example.wantedboard.response.ResponseDto;
import com.example.wantedboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.wantedboard.util.StatusCode.SUCCESS;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<ResponseDto<Object>> join(@RequestBody @Valid JoinDto joinDto, BindingResult bindingResult) {
        var message = userService.join(joinDto);

        return new ResponseEntity<>( ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message(message)
                .build(),
                OK
        );
    }
}
