package com.dailywriting.web.user;

import com.dailywriting.web.user.domain.User;
import com.dailywriting.web.user.domain.UserRepository;
import com.dailywriting.web.user.domain.UserService;
import com.dailywriting.web.user.dto.CreateTokenRequestDto;
import com.dailywriting.web.user.dto.JoinRequestDto;
import com.dailywriting.web.user.exception.UserDuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void loginTest() {
        // given
        JoinRequestDto joinRequestDto = JoinRequestDto
            .builder()
            .username("testuser")
            .password("testpassword")
            .build();

        when(userRepositoryMock.findByUsername("testuser")).thenReturn(null);
        userService.join(joinRequestDto);

        User joinUser = User.builder()
                .username("testuser")
                .password("encodedpassword")
                .build();

        // when
        when(passwordEncoderMock.encode("testpassword")).thenReturn("encodedpassword");
        when(passwordEncoderMock.matches("testpassword", "encodedpassword")).thenReturn(true);
        when(userRepositoryMock.findByUsername("testuser")).thenReturn(joinUser);
        User loginUser = userService.login(
            CreateTokenRequestDto
                .builder()
                .username("testuser")
                .password("testpassword")
                .build()
        );

        // then
        assertEquals(joinRequestDto.getUsername(), loginUser.getUsername());
    }

    @Test
    public void joinTest() {
        // given
        JoinRequestDto joinDto = JoinRequestDto
            .builder()
            .username("testuser")
            .password("testpassword")
            .build();

        // when
        when(userRepositoryMock.findByUsername("testuser")).thenReturn(null);
        userService.join(joinDto);

        // then
        verify(userRepositoryMock, times(1)).findByUsername(joinDto.getUsername());
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(passwordEncoderMock, times(1)).encode(joinDto.getPassword());
    }

    @Test
    public void joinDuplicateCheckTest() {
        // given
        JoinRequestDto joinDto = JoinRequestDto
            .builder()
            .username("testuser")
            .password("testpassword")
            .build();

        // when
        when(userRepositoryMock.findByUsername(joinDto.getUsername())).thenReturn(
            User.builder()
                .username("testuser")
                .password("encodedpassword")
                .build()
        );

        // then
        assertThrows(UserDuplicateException.class, () -> userService.join(joinDto));
    }
}
