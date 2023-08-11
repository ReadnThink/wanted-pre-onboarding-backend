package com.example.wantedboard.domain.post.dao;


import com.example.wantedboard.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}

