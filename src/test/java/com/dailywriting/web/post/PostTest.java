package com.dailywriting.web.post;

import com.dailywriting.web.Post.domain.Post;
import com.dailywriting.web.Post.domain.PostRepository;
import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class PostTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @Transactional
    public void createPostTest() {
        User user = User.builder()
            .username("test")
            .password("test")
            .build();

        userRepository.save(user);

        Post post = Post.builder()
            .user(user)
            .title("test")
            .content("test")
            .build();

        postRepository.save(post);

        List<Post> posts = postRepository.findAll();
        Assertions.assertEquals(posts.size(), 1);
    }
}
