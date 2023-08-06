package com.example.wantedboard.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final String title;
    private final String content;

    @Builder
    public PostResponse(final String title, final String content) {
        this.title = title;
        this.content = content;
    }
}