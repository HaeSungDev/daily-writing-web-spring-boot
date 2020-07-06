package com.dailywriting.web.user.domain;

import com.dailywriting.web.user.dto.JoinDto;
import com.dailywriting.web.user.dto.LoginDto;
import com.dailywriting.web.user.exception.LoginFailException;
import com.dailywriting.web.user.exception.UserDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public User login(@Valid LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (user == null) {
            throw new LoginFailException();
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new LoginFailException();
        }

        return user;
    }

    @Transactional
    public long join(@Valid JoinDto joinDto) {
        User encodedUser = User
            .builder()
            .username(joinDto.getUsername())
            .password(passwordEncoder.encode(joinDto.getPassword()))
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
