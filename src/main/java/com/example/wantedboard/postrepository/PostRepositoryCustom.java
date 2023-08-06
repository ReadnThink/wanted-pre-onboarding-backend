package com.example.wantedboard.postrepository;


import com.example.wantedboard.domain.Post;
import com.example.wantedboard.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
