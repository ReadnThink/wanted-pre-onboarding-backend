package com.example.wantedboard.controller;

import com.example.wantedboard.domain.user.entity.User;
import com.example.wantedboard.domain.user.UserRole;
import com.example.wantedboard.global.exception.CustomApiException;
import com.example.wantedboard.domain.post.exception.PostNotFound;
import com.example.wantedboard.domain.user.dao.UserRepository;
import com.example.wantedboard.domain.post.dto.PostCreate;
import com.example.wantedboard.domain.post.dto.PostEdit;
import com.example.wantedboard.domain.post.dto.PostResponse;
import com.example.wantedboard.domain.post.application.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.wantedboard.config.PostUrl.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper om;

    @MockBean
    PostService postService;

    @Autowired
    UserRepository userRepository;


    @BeforeEach
    public void setUp(){
        saveMockUser();
    }

    @Test
    @DisplayName("글 작성 성공")
    @WithUserDetails(value = "wanted@wanted.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성성공1() throws Exception {
        // when
        var postCreate = PostCreate.builder()
                .title("제목")
                .content("내용")
                .build();

        var postResponse = PostResponse.builder()
                .title("제목")
                .content("내용")
                .build();

        given(postService.write(any(),any())).willReturn(postResponse);

        mockMvc.perform(post(POST_CREATE.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(postCreate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 작성을 성공했습니다."))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 작성 title 빈 문자열 가능")
    @WithUserDetails(value = "wanted@wanted.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성성공2() throws Exception {
        var postCreate = PostCreate.builder()
                .title("")
                .content("내용")
                .build();
        mockMvc.perform(post(POST_CREATE.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 작성을 성공했습니다."))
                .andDo(print())
        ;
    }
    @Test
    @DisplayName("글 작성 글의 제목은 제한 없음")
    @WithUserDetails(value = "wanted@wanted.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 작성성공3() throws Exception {
        var postCreate = PostCreate.builder()
                .title("제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.제목 글자수 제한이 없습니다.")
                .content("내용")
                .build();

        mockMvc.perform(post(POST_CREATE.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsBytes(postCreate))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 작성을 성공했습니다."))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("글 1개 조회 성공")
    void 조회성공1() throws Exception {
        Long postId = 1L;
        //when
        mockMvc.perform(get(POST_GET.getValue(), postId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 조회에 성공했습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).get(1L);
    }

    @Test
    @DisplayName("글 1개 조회 실패")
    void 조회실패() throws Exception {
        Long postId = 1L;

        //given
        final CustomApiException customApiException = new PostNotFound();
        given(postService.get(postId)).willThrow(customApiException);

        //when
        mockMvc.perform(get(POST_GET.getValue(), postId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("존재하지 않는 글입니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).get(1L);
    }

    @Test
    @DisplayName("글 여러개 조회 성공")
    void 글_여러개조회성공() throws Exception {
        //given
        List<PostResponse> requestPosts = IntStream.range(1, 11)
                .mapToObj(i -> {
                    return PostResponse.builder()
                            .title("Title " + i)
                            .content("Content " + i)
                            .build();
                })
                .collect(Collectors.toList());
        //stub
        given(postService.getList(any())).willReturn(requestPosts);

        //when
        mockMvc.perform(get(POST_LIST.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 리스트 조회를 성공했습니다."))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.size()").value(10))
                .andDo(print())
        ;
        verify(postService).getList(any());
    }

    @Test
    @DisplayName("글 수정 성공")
    @WithUserDetails(value = "wanted@wanted.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 글_수정() throws Exception {
        //given
        var request = PostEdit.builder()
                .title("수정")
                .content("수정")
                .build();
        //when
        mockMvc.perform(patch(POST_EDIT.getValue(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 수정을 성공했습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).edit(any(), any(), any());
    }

    @Test
    @DisplayName("글 삭제 성공")
    @WithUserDetails(value = "wanted@wanted.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void 글_삭제() throws Exception {
        //when
        mockMvc.perform(delete(POST_DELETE.getValue(), 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("글 삭제를 성공했습니다."))
                .andExpect(jsonPath("$.data").isEmpty())
                .andDo(print())
        ;
        verify(postService).delete(any(), any());
    }

    private void saveMockUser() {
        User user = User.builder()
                .email("wanted@wanted.com")
                .userRole(UserRole.USER)
                .password("12341234")
                .build();
        userRepository.save(user);
    }
}