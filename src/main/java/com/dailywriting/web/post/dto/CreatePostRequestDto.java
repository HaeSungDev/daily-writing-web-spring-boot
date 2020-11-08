package com.dailywriting.web.post.dto;

import com.dailywriting.web.post.domain.Post;
import com.dailywriting.web.user.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePostRequestDto {
    String title;
    String content;

    public Post toEntity(User user) {
        return Post.builder()
            .title(title)
            .content(content)
            .user(user)
            .build();
    }
}
