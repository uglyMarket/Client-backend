package com.sparta.uglymarket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private Key key;

    private final int expiration = 3600; // 1시간 만료
    private final String phoneNumber = "123456789";


    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // 키를 Keys.secretKeyFor로 생성 (512비트 이상 키 보장)
        key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

        // secretKey와 expiration을 강제로 주입
        ReflectionTestUtils.setField(jwtUtil, "secretKey", Base64.getEncoder().encodeToString(key.getEncoded()));
        ReflectionTestUtils.setField(jwtUtil, "expiration", expiration);
        jwtUtil.init(); // 비밀키 초기화
    }

    @Test
    @DisplayName("JWT 토큰 생성 테스트")
    void generateToken() {
        // when
        String token = jwtUtil.generateToken(phoneNumber);

        // then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("JWT 토큰에서 phoneNumber 추출 테스트")
    void getPhoneNumberFromToken() {
        // given 서명된 JWT 토큰을 생성 (단위테스트를 위해)
        String token = Jwts.builder()
                .setSubject(phoneNumber)  // 클레임 설정
                .setIssuedAt(new Date())  // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘을 사용하여 서명
                .compact();  // 토큰 생성

        // when
        String extractedPhoneNumber = jwtUtil.getPhoneNumberFromToken(token);

        // then
        assertThat(extractedPhoneNumber).isEqualTo(phoneNumber);
    }

    @Test
    @DisplayName("JWT 토큰 유효성 검증 테스트: 유효한 토큰")
    void validateToken_ValidToken() {
        // given 서명된 JWT 토큰을 생성 (단위테스트를 위해)
        String token = Jwts.builder()
                .setSubject(phoneNumber)  // 클레임 설정
                .setIssuedAt(new Date())  // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘을 사용하여 서명
                .compact();  // 토큰 생성

        // when
        boolean isValid = jwtUtil.validateToken(token);

        // then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("JWT 토큰 유효성 검증 테스트: 유효하지 않은 토큰")
    void validateToken_InvalidToken() {
        // given
        String invalidToken = "invalidToken";

        // when
        boolean isValid = jwtUtil.validateToken(invalidToken);

        // then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("JWT 만료된 토큰 검증 테스트")
    void validateToken_ExpiredToken() {
        // given
        // 만료된 토큰 생성 (1시간 전 만료)
        String expiredToken = Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1시간 전 발행
                .setExpiration(new Date(System.currentTimeMillis() - 1000)) // 이미 만료된 토큰
                .signWith(key, SignatureAlgorithm.HS512) // 서명 키를 직접 사용
                .compact();

        // when
        boolean isValid = jwtUtil.validateToken(expiredToken);

        // then
        assertFalse(isValid); // 만료된 토큰은 유효하지 않음
    }

    @Test
    @DisplayName("JWT 토큰에서 모든 클레임 추출 테스트")
    void getAllClaimsFromToken() {
        // given 서명된 JWT 토큰을 생성 (단위테스트를 위해)
        String token = Jwts.builder()
                .setSubject(phoneNumber)  // 클레임 설정
                .setIssuedAt(new Date())  // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘을 사용하여 서명
                .compact();  // 토큰 생성

        // when: 서명 검증 후 클레임 추출
        Claims claims = jwtUtil.getAllClaimsFromToken(token);

        // then: 클레임이 정상적으로 추출되는지 확인
        assertThat(claims).isNotNull();
        assertThat(claims.getSubject()).isEqualTo(phoneNumber);
    }
}