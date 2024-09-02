package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.util.PasswordEncoderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserJoinServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    @Mock
    private PhoneNumberValidatorService phoneNumberValidatorService;

    @InjectMocks
    private UserJoinService userJoinService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void joinUser_Success() {
        // given
        String nickname = "testuser";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String phoneNumber = "123456789";
        String profileImageUrl = "imageUrl";
        String role = "USER";

        // Mock으로 JoinRequest 객체 생성
        JoinRequest request = mock(JoinRequest.class);
        given(request.getNickname()).willReturn(nickname);
        given(request.getPassword()).willReturn(password);
        given(request.getPhoneNumber()).willReturn(phoneNumber);
        given(request.getProfileImageUrl()).willReturn(profileImageUrl);
        given(request.getRole()).willReturn(role);

        // 실제 User 객체 생성 (생성자가 있기에 사용)
        User savedUser = new User(request, encodedPassword, Role.USER);

        // Mock 설정
        doNothing().when(phoneNumberValidatorService).validatePhoneNumber(phoneNumber);
        given(passwordEncoderUtil.encodePassword(password)).willReturn(encodedPassword);
        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // when
        JoinResponse response = userJoinService.JoinUser(request);

        // then
        assertNotNull(response);
        assertEquals(nickname, response.getNickname());
        assertEquals(phoneNumber, response.getPhoneNumber());
        assertEquals(profileImageUrl, response.getProfileImageUrl());
        assertEquals(role, response.getRole());

        verify(phoneNumberValidatorService, times(1)).validatePhoneNumber(phoneNumber);// 전화번호 검증 서비스 호출 확인
        verify(passwordEncoderUtil, times(1)).encodePassword(password);// 비밀번호 인코딩 확인
        verify(userRepository, times(1)).save(any(User.class));// 유저 저장 확인

    }

    // 중복 전화번호로 회원가입 시 예외 발생 테스트
    @Test
    void joinUser_DuplicatePhoneNumber() {
        // given
        String phoneNumber = "123456789";
        JoinRequest request = mock(JoinRequest.class);
        given(request.getPhoneNumber()).willReturn(phoneNumber);

        // 전화번호 중복 검사를 실패하도록 설정
        doThrow(new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER)).when(phoneNumberValidatorService).validatePhoneNumber(phoneNumber);

        // when
        assertThrows(CustomException.class, () -> userJoinService.JoinUser(request));

        //then
        verify(phoneNumberValidatorService, times(1)).validatePhoneNumber(phoneNumber);// 전화번호 검증 서비스 호출 확인
        verify(passwordEncoderUtil, times(0)).encodePassword(any());// 비밀번호 인코딩이나 유저 저장은 호출되지 않아야 함
        verify(userRepository, times(0)).save(any(User.class));
    }

}