package com.example.wantedboard.controller;

import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.response.ResponseDto;
import com.example.wantedboard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        log.info("디버그 : {}", postCreate);
        postService.write(postCreate);
        return new ResponseEntity<>(new ResponseDto<>(1, "글작성에 성공했습니다.", null), HttpStatus.OK);
    }

}
