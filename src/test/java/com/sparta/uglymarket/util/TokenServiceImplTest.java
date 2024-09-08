package com.sparta.uglymarket.util;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceImplTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private final String token = "Bearer validJwtToken";
    private final String phoneNumber = "123456789";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("HTTP 요청에서 전화번호 추출 성공 테스트")
    void getPhoneNumberFromRequest_Success() {
        // given
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.getPhoneNumberFromToken("validJwtToken")).thenReturn(phoneNumber);

        // when
        String extractedPhoneNumber = tokenService.getPhoneNumberFromRequest(request);

        // then
        assertThat(extractedPhoneNumber).isEqualTo(phoneNumber);
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtUtil, times(1)).getPhoneNumberFromToken("validJwtToken");
    }

    @Test
    @DisplayName("HTTP 요청에서 토큰이 없는 경우 처리")
    void getPhoneNumberFromRequest_NoToken() {
        // given
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        String extractedPhoneNumber = tokenService.getPhoneNumberFromRequest(request);

        // then
        assertThat(extractedPhoneNumber).isNull();
        verify(request, times(1)).getHeader("Authorization");
        verify(jwtUtil, never()).getPhoneNumberFromToken(anyString());
    }

    @Test
    @DisplayName("JWT 토큰이 잘못된 경우 예외 발생 처리")
    void getPhoneNumberFromRequest_InvalidToken() {
        // given
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.getPhoneNumberFromToken("validJwtToken")).thenThrow(new RuntimeException("Invalid Token"));

        // when & then
        assertThatThrownBy(() -> tokenService.getPhoneNumberFromRequest(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid Token");

        verify(request, times(1)).getHeader("Authorization");
        verify(jwtUtil, times(1)).getPhoneNumberFromToken("validJwtToken");
    }
}