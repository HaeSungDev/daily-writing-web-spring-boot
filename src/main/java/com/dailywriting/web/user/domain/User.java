package com.dailywriting.web.user.domain;

import com.dailywriting.web.post.domain.Post;
import com.dailywriting.web.global.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "user",
    uniqueConstraints = @UniqueConstraint(name = "uk_user_username", columnNames = {
            "username"
    })
)
public class User extends BaseTimeEntity {
    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    public void changePassword(String password) {
        this.password = password;
    }
}
