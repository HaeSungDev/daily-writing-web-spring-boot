package com.dailywriting.web.post;

import com.dailywriting.web.post.domain.Post;
import com.dailywriting.web.post.domain.PostRepository;
import com.dailywriting.web.post.dto.CreatePostRequestDto;
import com.dailywriting.web.post.service.PostService;
import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class PostServiceTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    @Transactional
    public void createPostTest() {
        User user = User.builder()
            .username("test")
            .password("test")
            .build();

        userRepository.save(user);

        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
            .title("test title")
            .content("test content")
            .build();

        long id = postService.createPost(user.getId(), createPostRequestDto);

        Post findPost = postRepository.findById(id).get();
        Assertions.assertEquals(findPost.getTitle(), "test title");
        Assertions.assertEquals(findPost.getContent(), "test content");
    }
}
