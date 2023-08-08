package com.example.wantedboard.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostUrl {

    POST_CREATE("/user/posts"),
    POST_GET("/posts/{postId}"),
    POST_LIST("/posts"),
    POST_EDIT("/user/posts/{postId}"),
    POST_DELETE("/user/posts/{postId}");

    private final String value;
}
