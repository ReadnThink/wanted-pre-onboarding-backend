package com.example.wantedboard.domain.user.entity;

import javax.persistence.*;

import com.example.wantedboard.domain.post.entity.Post;
import com.example.wantedboard.domain.user.UserRole;
import com.example.wantedboard.domain.user.exception.InvalidEmail;
import com.example.wantedboard.domain.user.exception.InvalidPassword;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private UserRole userRole;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    @Builder
    public User(final Long id, final String email, final String password, final UserRole userRole) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public void validateJoinCreate(){
        validateEmail();
        validatePassword();
    }

    private void validateEmail() {
        if (!this.email.contains("@")) {
            throw new InvalidEmail();
        }
    }

    private void validatePassword() {
        if (this.password.length() < 8) {
            throw new InvalidPassword();
        }
    }

    public void encodePassword(final PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
}

