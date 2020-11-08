package com.dailywriting.web.user.service;

import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserRepository;
import com.dailywriting.web.user.dto.CreateTokenRequestDto;
import com.dailywriting.web.user.dto.JoinRequestDto;
import com.dailywriting.web.user.exception.LoginFailException;
import com.dailywriting.web.user.exception.UserDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public User login(CreateTokenRequestDto createTokenRequestDto) {
        User user = userRepository.findByUsername(createTokenRequestDto.getUsername());
        if (user == null) {
            throw new LoginFailException();
        }

        if (!passwordEncoder.matches(createTokenRequestDto.getPassword(), user.getPassword())) {
            throw new LoginFailException();
        }

        return user;
    }

    @Transactional
    public long join(JoinRequestDto joinRequestDto) {
        User encodedUser = User
            .builder()
            .username(joinRequestDto.getUsername())
            .password(passwordEncoder.encode(joinRequestDto.getPassword()))
            .build();

        validateDuplicateUser(encodedUser);
        userRepository.save(encodedUser);
        return encodedUser.getId();
    }

    private void validateDuplicateUser(User user) {
        User duplicateUser = userRepository.findByUsername(user.getUsername());
        if (duplicateUser != null) {
            throw new UserDuplicateException();
        }
    }
}
