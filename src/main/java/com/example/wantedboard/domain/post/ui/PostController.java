package com.example.wantedboard.domain.post.ui;

import com.example.wantedboard.global.config.auth.LoginUser;
import com.example.wantedboard.domain.post.dto.PostCreate;
import com.example.wantedboard.domain.post.dto.PostEdit;
import com.example.wantedboard.domain.post.dto.PostSearch;
import com.example.wantedboard.domain.post.dto.PostResponse;
import com.example.wantedboard.global.util.ResponseDto;
import com.example.wantedboard.domain.post.application.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

import static com.example.wantedboard.global.util.StatusCode.SUCCESS;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Api(tags = "POST API")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ApiOperation(
            value = "글 작성",
            notes = "글의 제목, 내용을 작성하는 API - 유효한 JWT 필요"
    )
    @PostMapping("/user/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate postCreate, BindingResult bindingResult, @AuthenticationPrincipal @ApiIgnore LoginUser loginUser) {
        postService.write(postCreate, loginUser.getUser().getId());

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 작성을 성공했습니다.")
                .build(),
                OK
        );
    }

    @ApiOperation(
            value = "글 한건 조회",
            notes = "게시글의 ID를 통해 게시글의 정보를 조회"
    )
    @ApiImplicitParam(
            name = "postId",
            value = "글 인덱스 번호",
            required = true,
            dataType = "Long"
    )
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

    @ApiOperation(
            value = "글 리스트 조회",
            notes = "글을 리스트 형식으로 조회하는 API 디폴트 설정 : page = 1, size = 10"
    )
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

    @ApiOperation(
            value = "글 수정",
            notes = "글의 제목, 내용을 수정하는 API - 글 Id, 유효한 JWT 필요"
    )
    @ApiImplicitParam(
            name = "postId",
            value = "글 인덱스 번호",
            required = true,
            dataType = "Long"
    )
    @PatchMapping("/user/posts/{postId}") // todo patch 바꾸기
    public ResponseEntity<?> edit(@PathVariable Long postId, @RequestBody PostEdit postEdit, @AuthenticationPrincipal @ApiIgnore LoginUser loginUser) {
        postService.edit(postId, postEdit, loginUser.getUser().getId());

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 수정을 성공했습니다.")
                .data(null)
                .build(),
                OK
        );
    }

    @ApiOperation(
            value = "글 삭제",
            notes = "글의 제목, 내용을 삭제하는 API - 글 Id, 유효한 JWT 필요"
    )
    @ApiImplicitParam(
            name = "postId",
            value = "글 인덱스 번호",
            required = true,
            dataType = "Long" //
    )
    @DeleteMapping("/user/posts/{postId}")
    public ResponseEntity<?> delete(@PathVariable Long postId, @AuthenticationPrincipal @ApiIgnore LoginUser loginUser) {
        postService.delete(postId, loginUser.getUser().getId());

        return new ResponseEntity<>(ResponseDto.builder()
                .code(SUCCESS.getValue())
                .message("글 삭제를 성공했습니다.")
                .data(null)
                .build(),
                OK
        );
    }
}
