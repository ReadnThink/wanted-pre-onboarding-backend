package com.example.wantedboard.controller;

import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.request.PostSearch;
import com.example.wantedboard.response.PostResponse;
import com.example.wantedboard.response.ResponseDto;
import com.example.wantedboard.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult) {
        postService.write(postCreate);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 작성을 성공했습니다.", null), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable Long postId) {
        final PostResponse postResponse = postService.get(postId);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 조회에 성공했습니다.", postResponse), OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getList(@ModelAttribute PostSearch postSearch) {
        final List<PostResponse> postList = postService.getList(postSearch);
        return new ResponseEntity<>(new ResponseDto<>(1, "글 리스트 조회를 성공했습니다.", postList), HttpStatus.OK);
    }
}