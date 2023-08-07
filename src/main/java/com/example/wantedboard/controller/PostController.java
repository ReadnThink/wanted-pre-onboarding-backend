package com.example.wantedboard.controller;

import com.example.wantedboard.config.auth.LoginUser;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.request.PostEdit;
import com.example.wantedboard.request.PostSearch;
import com.example.wantedboard.response.PostResponse;
import com.example.wantedboard.response.ResponseDto;
import com.example.wantedboard.service.PostService;
import com.example.wantedboard.util.StatusCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.example.wantedboard.util.StatusCode.*;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/user/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult, @AuthenticationPrincipal LoginUser loginUser) {
        postService.write(postCreate, loginUser.getUser().getEmail());

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 작성을 성공했습니다.")
                .build(),
                OK
        );
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> get(@PathVariable Long postId) {
        final PostResponse postResponse = postService.get(postId);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 조회에 성공했습니다.")
                .data(postResponse)
                .build(),
                OK
        );
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getList(@ModelAttribute PostSearch postSearch) {
        final List<PostResponse> postList = postService.getList(postSearch);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 리스트 조회를 성공했습니다.")
                .data(postList)
                .build(),
                OK
        );
    }

    @PostMapping("/user/posts/{postId}")
    public ResponseEntity<?> edit(@PathVariable Long postId, @RequestBody PostEdit postEdit) {
        postService.edit(postId, postEdit);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 수정을 성공했습니다.")
                .data(null)
                .build(),
                OK
        );
    }

    @DeleteMapping("/user/posts/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId) {
        postService.delete(postId);

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 삭제를 성공했습니다.")
                .data(null)
                .build(),
                OK
        );
    }
}
