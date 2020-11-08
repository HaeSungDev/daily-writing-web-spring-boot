package com.dailywriting.web.Post.domain;

import com.dailywriting.web.global.base.BaseTimeEntity;
import com.dailywriting.web.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "article")
public class Post extends BaseTimeEntity {
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
