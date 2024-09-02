package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import com.sparta.uglymarket.util.JwtUtil;
import com.sparta.uglymarket.util.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserLoginServiceTest {

    @Mock
    private FinderService finderService;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserLoginService userLoginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userLoginService = new UserLoginService(finderService, passwordEncoderUtil, jwtUtil);

    }


    // 로그인 테스트
    @Test
    void loginUser() {
        // given
        String phoneNumber = "123456789";
        String password = "password";
        String encodedPassword = "encodedPassword"; // Mock에서 사용되는 인코딩된 비밀번호
        String token = "mockedJwtToken";

        // LoginRequest 객체 생성 및 설정 (목 객체 사용)
        LoginRequest request = mock(LoginRequest.class);
        given(request.getPhoneNumber()).willReturn(phoneNumber);
        given(request.getPassword()).willReturn(password);

        // User 객체 생성 및 설정 (목 객체 사용)
        User user = mock(User.class);
        given(user.getPhoneNumber()).willReturn(phoneNumber);
        given(user.getPassword()).willReturn(encodedPassword);

        // Mock 설정
        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
        doNothing().when(passwordEncoderUtil).validatePassword(password, encodedPassword); // doNothing()을 사용하여 예외가 발생하지 않도록 설정
        given(jwtUtil.generateToken(phoneNumber)).willReturn(token);

        // when
        LoginResponse response = userLoginService.loginUser(request);

        // then
        assertNotNull(response);
        assertEquals(token, response.getToken());
        verify(finderService, times(1)).findUserByPhoneNumber(phoneNumber);
        verify(passwordEncoderUtil, times(1)).validatePassword(password, encodedPassword); // 이 호출이 예외 없이 진행되었음을 확인
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
        given(user.getPassword()).willReturn("encodedPassword");

        // 목 객체 설정
        // Mock 설정
        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user); // UserRepository 대신 FinderService 사용
        doThrow(new CustomException(ErrorMsg.PHONE_NUMBER_INVALID)).when(passwordEncoderUtil).validatePassword(wrongPassword, "encodedPassword");

        // when & then
        assertThrows(CustomException.class, () -> userLoginService.loginUser(request));
    }

}
