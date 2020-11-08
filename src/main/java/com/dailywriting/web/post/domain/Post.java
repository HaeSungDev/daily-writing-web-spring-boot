package com.dailywriting.web.post.domain;

import com.dailywriting.web.global.base.BaseTimeEntity;
import com.dailywriting.web.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article")
public class Post extends BaseTimeEntity {
    @Builder
    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    User user;

    @Column(nullable = false, name = "title")
    String title;

    @Column(nullable = false, name = "content")
    String content;
}
