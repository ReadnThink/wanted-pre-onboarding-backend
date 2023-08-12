package com.example.wantedboard.domain.post.entity;

import javax.persistence.*;

import com.example.wantedboard.domain.user.entity.User;
import com.example.wantedboard.domain.user.exception.UserNotMatch;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String title;

    @ManyToOne
    @JoinColumn
    private User user;

    @Builder
    public Post(final Long id, final String content, final String title, User user) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.user = user;
    }

    public void change(String title, String content) {
        this.title = title != null ? title : this.title;
        this.content = content != null ? content : this.content;
    }

    public void addUser(final User user) {
        this.user = user;
    }

    public void isSameUser(final Long userId) {
        if (this.user == null || this.user.getId() != userId ) {
            throw new UserNotMatch();
        }
    }
}