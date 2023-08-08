package com.example.wantedboard.controller;

import com.example.wantedboard.request.JoinCreate;
import com.example.wantedboard.request.Login;
import com.example.wantedboard.response.ResponseDto;
import com.example.wantedboard.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.wantedboard.util.StatusCode.SUCCESS;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "USER API")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @ApiOperation(
            value = "회원가입",
            notes = "이메일, 비밀번호를 통해 회원가입을 하는 API"
    )
    @PostMapping("/join")
    public ResponseEntity<ResponseDto<Object>> join(@RequestBody @Valid JoinCreate joinCreate, BindingResult bindingResult) {
        var message = userService.join(joinCreate);

        return new ResponseEntity<>( ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message(message)
                .build(),
                OK
        );
    }

    @ApiOperation(
            value = "로그인",
            notes = "회원가입된 이메일, 비밀번호를 통해 로그인을 하는 API"
    )
    @PostMapping("/login")
    public void login(@RequestBody @Valid Login login, BindingResult bindingResult) {
    }
}
