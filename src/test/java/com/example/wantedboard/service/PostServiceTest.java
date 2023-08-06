package com.example.wantedboard.service;

import com.example.wantedboard.domain.Post;
import com.example.wantedboard.postrepository.PostRepository;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.request.PostSearch;
import com.example.wantedboard.response.PostResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Test
    @DisplayName("글 1페이지 조회")
    void test_list() {
        //given
        List<Post> response = IntStream.range(1, 6)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("Title " + i)
                            .content("Content " + i)
                            .build();
                })
                .collect(Collectors.toList());
        // stub
        given(postRepository.getList(any())).willReturn(response);

        //when
        PostSearch postSearch = new PostSearch();
        final List<PostResponse> list = postService.getList(postSearch);

        //then
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("글 수정 성공")
    void test_edit() {
        //given
        final Post post = Post.builder()
                .id(1L)
                .title("Title")
                .content("Content")
                .build();

        //when
        post.change("제목수정","내용수정");

        //then
        assertThat(post.getTitle()).isEqualTo("제목수정");
        assertThat(post.getContent()).isEqualTo("내용수정");
    }
}