package com.example.wantedboard.service;

import com.example.wantedboard.domain.Post;
import com.example.wantedboard.postrepository.PostRepository;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.response.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    PostRepository postRepository;

    @Test
    @DisplayName("글 작성 성공")
    void test1() {

        final Post post = Post.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .build();
        given(postRepository.save(any())).willReturn(post);

        final PostResponse responseDto = postService.write(PostCreate.builder()
                .title("제목")
                .content("내용")
                .build());

        assertThat(responseDto.getTitle()).isEqualTo("제목");
        assertThat(responseDto.getContent()).isEqualTo("내용");
    }

    @Test
    @DisplayName("글 1개 조회")
    void test() {
        //given
        final Post response = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .build();
        final PostResponse oneResponse = PostResponse.builder()
                .title("Title")
                .content("Content")
                .build();

        // stub
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(response));

        //when
        final PostResponse postOneResponse = postService.get(response.getId());

        //then
        assertThat(postOneResponse).isNotNull();
        assertThat(postOneResponse.getContent()).isEqualTo(oneResponse.getContent());
        assertThat(postOneResponse.getTitle()).isEqualTo(oneResponse.getTitle());
    }
}