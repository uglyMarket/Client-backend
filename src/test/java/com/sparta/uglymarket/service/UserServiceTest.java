package com.sparta.uglymarket.service;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.user.service.UserService;
import com.sparta.uglymarket.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserService(userRepository, jwtUtil);
    }

    // 회원가입 테스트
    @Test
    void joinUser() {
        // given
        String nickname = "testuser";
        String password = "password";
        String phoneNumber = "123456789";
        String profileImageUrl = "imageUrl";
        String role = "USER";

        // JoinRequest 객체 생성 및 설정 (목 객체 사용)
        JoinRequest request = mock(JoinRequest.class);
        given(request.getNickname()).willReturn(nickname);
        given(request.getPassword()).willReturn(password);
        given(request.getPhoneNumber()).willReturn(phoneNumber);
        given(request.getProfileImageUrl()).willReturn(profileImageUrl);
        given(request.getRole()).willReturn(role);

        // User 객체 생성 및 설정 (이미 생성자가 있기에 그대로 사용)
        User user = new User(request, passwordEncoder.encode(password), Role.USER);

        // 목 객체 설정
        given(userRepository.existsByPhoneNumber(phoneNumber)).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);

        // when (검증하고 싶은 서비스 실행)
        JoinResponse response = userService.joinUser(request);

        // then (response검증)
        assertNotNull(response);
        assertEquals(nickname, response.getNickname());
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(profileImageUrl, response.getProfileImageUrl());
        assertEquals(role, response.getRole());
        verify(userRepository, times(1)).existsByPhoneNumber(phoneNumber);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // 중복 전화번호로 회원가입 시 예외 발생 테스트
    @Test
    void joinUser_DuplicatePhoneNumber() {
        // given
        String phoneNumber = "123456789";

        // JoinRequest 객체 생성 및 설정 (목 객체 사용)
        JoinRequest request = mock(JoinRequest.class);
        given(request.getPhoneNumber()).willReturn(phoneNumber);

        given(userRepository.existsByPhoneNumber(phoneNumber)).willReturn(true);

        // when & then
        assertThrows(CustomException.class, () -> userService.joinUser(request));
    }

    // 로그인 테스트
    @Test
    void loginUser() {
        // given
        String phoneNumber = "123456789";
        String password = "password";
        String encodedPassword = passwordEncoder.encode(password);
        String token = "mockedJwtToken";

        // LoginRequest 객체 생성 및 설정 (목 객체 사용)
        LoginRequest request = mock(LoginRequest.class);
        given(request.getPhoneNumber()).willReturn(phoneNumber);
        given(request.getPassword()).willReturn(password);

        // User 객체 생성 및 설정 (목 객체 사용)
        User user = mock(User.class);
        given(user.getPhoneNumber()).willReturn(phoneNumber);
        given(user.getPassword()).willReturn(encodedPassword);

        // 목 객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(jwtUtil.generateToken(phoneNumber)).willReturn(token);

        // when
        LoginResponse response = userService.loginUser(request);

        // then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        verify(userRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(jwtUtil, times(1)).generateToken(phoneNumber);
    }

    // 잘못된 비밀번호로 로그인 시 예외 발생 테스트
    @Test
    void loginUser_InvalidPassword() {
        // given
        String phoneNumber = "123456789";
        String password = "password";
        String wrongPassword = "wrongPassword";

        // LoginRequest 객체 생성 및 설정 (목 객체 사용)
        LoginRequest request = mock(LoginRequest.class);
        given(request.getPhoneNumber()).willReturn(phoneNumber);
        given(request.getPassword()).willReturn(wrongPassword);

        // User 객체 생성 및 설정 (목 객체 사용)
        User user = mock(User.class);
        given(user.getPhoneNumber()).willReturn(phoneNumber);
        given(user.getPassword()).willReturn(passwordEncoder.encode(password));

        // 목 객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));

        // when & then
        assertThrows(CustomException.class, () -> userService.loginUser(request));
    }

}
