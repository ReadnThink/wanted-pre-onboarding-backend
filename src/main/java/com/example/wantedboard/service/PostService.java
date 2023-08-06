package com.example.wantedboard.service;

import com.example.wantedboard.domain.Post;
import com.example.wantedboard.postrepository.PostRepository;
import com.example.wantedboard.request.PostCreate;
import com.example.wantedboard.response.PostResponseDto;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(final PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto write(PostCreate postCreate) {
        final Post post = postRepository.save(postCreate.toEntity());
        return new PostResponseDto(post.getTitle(), post.getContent());
    }
}
