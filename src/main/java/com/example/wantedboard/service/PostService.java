package com.example.wantedboard.service;

import com.example.wantedboard.exception.CustomApiException;
import com.example.wantedboard.domain.Post;
import com.example.wantedboard.exception.PostNotFound;
import com.example.wantedboard.postrepository.PostRepository;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.request.PostEdit;
import com.example.wantedboard.request.PostSearch;
import com.example.wantedboard.response.PostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponse write(PostCreate postCreate) {
        final Post post = postRepository.save(postCreate.toEntity());
        return new PostResponse(post.getTitle(), post.getContent());
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
