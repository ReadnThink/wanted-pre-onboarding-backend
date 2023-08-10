package com.example.wantedboard.service;

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

    @Transactional
    public PostResponse write(PostCreate postCreate, final Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);;
        var post = postRepository.save(postCreate.toEntity());

        post.addUser(user);

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public PostResponse get(final Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        return PostResponse.builder()
                .id(post.getId())
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
    public void edit(Long id, PostEdit postEdit, Long userId) {
        var post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.change(postEdit.getTitle(), postEdit.getContent(), userId);
    }

    public void delete(final Long postId, Long userId) {
        var post = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        validateUserExists(userId);
        post.isSameUser(userId);

        postRepository.delete(post);
    }

    private void validateUserExists(final Long userId) {
        userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
    }
}
