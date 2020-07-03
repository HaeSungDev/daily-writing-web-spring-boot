package com.dailywriting.web.user;

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

    @Transactional
    public long join(User user) {
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
