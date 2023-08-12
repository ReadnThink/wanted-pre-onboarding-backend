package com.example.wantedboard.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostUrl {

    POST_CREATE_URL("/user/posts"),
    POST_GET_URL("/posts/{postId}"),
    POST_LIST_URL("/posts"),
    POST_EDIT_URL("/user/posts/{postId}"),
    POST_DELETE_URL("/user/posts/{postId}");

    private final String value;
}
