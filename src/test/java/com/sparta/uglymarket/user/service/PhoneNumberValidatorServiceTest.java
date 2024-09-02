package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PhoneNumberValidatorServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PhoneNumberValidatorService phoneNumberValidatorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //전화번호 중복
    @Test
    void validatePhoneNumber_PhoneNumberExists_ShouldThrowException() {
        // given
        String phoneNumber = "123456789";

        //Mock 설정: 해당 전화번호가 존재한다고 가정
        given(userRepository.existsByPhoneNumber(phoneNumber)).willReturn(true);

        // when & then
        assertThrows(CustomException.class, () -> phoneNumberValidatorService.validatePhoneNumber(phoneNumber));

        // 예외가 발생해야 하며, UserRepository의 existsByPhoneNumber 메서드가 1회 호출되었음을 검증
        verify(userRepository, times(1)).existsByPhoneNumber(phoneNumber);
    }

    //전화번호 중복이 아닐때
    @Test
    void validatePhoneNumber_PhoneNumberDoesNotExist_ShouldNotThrowException() {
        // given
        String phoneNumber = "123456789";

        //Mock 설정: 해당 전화번호가 존재하지 않는다고 가정
        given(userRepository.existsByPhoneNumber(phoneNumber)).willReturn(false);

        // when
        phoneNumberValidatorService.validatePhoneNumber(phoneNumber);

        // then
        // 예외가 발생하지 않아야 하며, UserRepository의 existsByPhoneNumber 메서드가 1회 호출되었음을 검증
        verify(userRepository, times(1)).existsByPhoneNumber(phoneNumber);
    }
}