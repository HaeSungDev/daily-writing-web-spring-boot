package com.dailywriting.web.post.service;

import com.dailywriting.web.post.domain.Post;
import com.dailywriting.web.post.domain.PostRepository;
import com.dailywriting.web.post.dto.CreatePostRequestDto;
import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserRepository;
import com.dailywriting.web.user.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {
    final PostRepository postRepository;
    final UserRepository userRepository;

    @Transactional
    public Long createPost(Long userId, CreatePostRequestDto createPostRequestDto) {
        Optional<User> user = userRepository.findById(userId);
        Post post = createPostRequestDto.toEntity(user.orElseThrow(InvalidUserException::new));
        postRepository.save(post);
        return post.getId();
    }
}
