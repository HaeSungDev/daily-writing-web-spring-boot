package com.dailywriting.web.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public long join(String username, String password) {
        User user = User.join(
                username,
                bCryptPasswordEncoder.encode(password)
        );
        userRepository.save(user);
        return user.getId();
    }
}
