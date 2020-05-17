package com.dailywriting.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceUnitTest {
    UserService userService;
    UserRepository userRepositoryMock;
    PasswordEncoder passwordEncoderMock;

    @BeforeEach
    public void init() {
        userRepositoryMock = mock(UserRepository.class);
        passwordEncoderMock = mock(PasswordEncoder.class);
        userService = new UserService(userRepositoryMock, passwordEncoderMock);
    }

    @Test
    public void 회원가입_테스트() {
        // given
        User user = new User("testuser", "testpassword");

        // when
        when(userRepositoryMock.findByUsername("testuser")).thenReturn(null);
        userService.join(user);

        // then
        verify(userRepositoryMock, times(1)).findByUsername(user.getUsername());
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(passwordEncoderMock, times(1)).encode(user.getPassword());
    }

    @Test
    public void 회원가입_중복체크_테스트() {
        // given
        User user = new User("testuser", "testpassword");

        // when
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(new User("testuser", "encodedpassword"));

        // then
        assertThrows(UserDuplicateException.class, () -> {
            userService.join(user);
        });
    }
}
