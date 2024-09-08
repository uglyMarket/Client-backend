package com.sparta.uglymarket.filter;

import com.sparta.uglymarket.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Claims claims;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("유효한 JWT 토큰으로 필터 통과")
    void doFilterInternal_ValidToken() throws ServletException, IOException {
        // given
        when(request.getRequestURI()).thenReturn("/api/some/secure/endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer validJwtToken");
        when(jwtUtil.validateToken("validJwtToken")).thenReturn(true);
        when(jwtUtil.getAllClaimsFromToken("validJwtToken")).thenReturn(claims);
        when(claims.getSubject()).thenReturn("123456789");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(request, times(1)).setAttribute("phoneNumber", "123456789");
        verify(filterChain, times(1)).doFilter(request, response);
        verify(response, never()).sendError(anyInt(), anyString());
    }


    @Test
    @DisplayName("인증이 필요 없는 경로 필터 통과")
    void doFilterInternal_SkipAuthentication() throws ServletException, IOException {
        // given
        when(request.getRequestURI()).thenReturn("/api/users/join");

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(filterChain, times(1)).doFilter(request, response);
        verify(jwtUtil, never()).validateToken(anyString());
        verify(response, never()).sendError(anyInt(), anyString());
    }

    @Test
    @DisplayName("유효하지 않은 JWT 토큰으로 필터 거부")
    void doFilterInternal_InvalidToken() throws ServletException, IOException {
        // given
        when(request.getRequestURI()).thenReturn("/api/some/secure/endpoint");
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidJwtToken");
        when(jwtUtil.validateToken("invalidJwtToken")).thenReturn(false);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("토큰이 없는 경우 필터 거부")
    void doFilterInternal_NoToken() throws ServletException, IOException {
        // given
        when(request.getRequestURI()).thenReturn("/api/some/secure/endpoint");
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(response, times(1)).sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        verify(filterChain, never()).doFilter(request, response);
    }

}