package com.example.wantedboard.domain.post.dao;


import com.example.wantedboard.domain.post.entity.Post;
import com.example.wantedboard.domain.post.dto.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
