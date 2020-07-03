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
    public void joinTest() {
        // given
        User user = User.builder()
            .username("testuser")
            .password("testpassword")
            .build();

        // when
        when(userRepositoryMock.findByUsername("testuser")).thenReturn(null);
        userService.join(user);

        // then
        verify(userRepositoryMock, times(1)).findByUsername(user.getUsername());
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(passwordEncoderMock, times(1)).encode(user.getPassword());
    }

    @Test
    public void joinDuplicateCheckTest() {
        // given
        User user = User.builder()
            .username("testuser")
            .password("testpassword")
            .build();

        // when
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(
            User.builder()
                .username("testuser")
                .password("encodedpassword")
                .build()
        );

        // then
        assertThrows(UserDuplicateException.class, () -> {
            userService.join(user);
        });
    }
}
