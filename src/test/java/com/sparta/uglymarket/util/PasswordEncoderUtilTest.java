package com.sparta.uglymarket.util;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderUtilTest {

    @InjectMocks
    private PasswordEncoderUtil passwordEncoderUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("비밀번호 인코딩 성공 테스트")
    void encodePassword_Success() {
        // given
        String rawPassword = "password123";

        // when
        String encodedPassword = passwordEncoderUtil.encodePassword(rawPassword);

        // then
        assertThat(encodedPassword).isNotNull();
        assertThat(encodedPassword).startsWith("$2a$"); // BCrypt로 인코딩된 비밀번호는 이 prefix로 시작함
    }

    @Test
    @DisplayName("비밀번호 검증 성공 테스트")
    void validatePassword_Success() {
        // given
        String rawPassword = "password123";
        String encodedPassword = passwordEncoderUtil.encodePassword(rawPassword);

        // when & then
        passwordEncoderUtil.validatePassword(rawPassword, encodedPassword);
    }

    @Test
    @DisplayName("비밀번호 검증 실패 시 예외 발생 테스트")
    void validatePassword_Fail() {
        // given
        String rawPassword = "password123";
        String wrongPassword = "wrongPassword123";
        String encodedPassword = passwordEncoderUtil.encodePassword(rawPassword);

        // when & then
        assertThatThrownBy(() -> passwordEncoderUtil.validatePassword(wrongPassword, encodedPassword))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMsg.PASSWORD_INCORRECT.getDetails());
    }

}