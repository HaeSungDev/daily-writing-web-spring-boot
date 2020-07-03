package com.dailywriting.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public User login(String username, String password) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("username은 필수 값입니다.");
        }
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("password는 필수 값입니다.");
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new LoginFailException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new LoginFailException();
        }

        return user;
    }

    @Transactional
    public long join(User user) {
        // @TODO validation user
        User encodedUser = User
                .builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
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
