package com.example.wantedboard.domain.post.dto;

import com.example.wantedboard.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private final String title;
    private final String content;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
    @Builder
    public PostResponse(final Long id, final String title, final String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}