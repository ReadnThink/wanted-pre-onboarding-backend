package com.example.wantedboard.service;

import com.example.wantedboard.domain.Post;
import com.example.wantedboard.domain.User;
import com.example.wantedboard.exception.PostNotFound;
import com.example.wantedboard.exception.UserNotFound;
import com.example.wantedboard.postrepository.PostRepository;
import com.example.wantedboard.postrepository.UserRepository;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.request.PostEdit;
import com.example.wantedboard.request.PostSearch;
import com.example.wantedboard.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // todo PostId 반환하기
    @Transactional
    public PostResponse write(PostCreate postCreate, final String userEmail) {
        var post = postRepository.save(postCreate.toEntity());

        var user = userRepository.findByEmail(userEmail)
                .orElseThrow(UserNotFound::new);

        post.addUser(user);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public PostResponse get(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        post.change(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(final Long postId) {
        final Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
