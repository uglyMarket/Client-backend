package com.sparta.uglymarket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private Key key; // 비밀 키를 저장할 필드

    @Value("${jwt.secret}") //properties 파일에서 jwt.secret 값 주입
    private String secretKey;

    @Value("${jwt.expiration}") //위와 마찬가지
    private int expiration;

    // 객체 생성 후 키 초기화
    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey); // Base64 디코딩 (시크릿키)
        key = Keys.hmacShaKeyFor(decodedKey); // 디코딩된 키를 사용하여 HMAC-SHA 키 생성
    }

    //phoneNumber를 기반으로 JWT 토큰을 생성
    public String generateToken(String phoneNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber) // 토큰 주제 설정
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정(현재시간)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 키와 알고리즘을 사용하여 서명
                .compact(); // 토큰 생성
    }

    //JWT 토큰에서 phoneNumber를 추출
    public String getPhoneNumberFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 서명 검증을 위한 키를 설정.
                .build()
                .parseClaimsJws(token)  // 토큰을 파싱하여 클레임을 추출
                .getBody(); // 클레임 본문을 반환.
        return claims.getSubject(); // 토큰 주제(phoneNumber) 반환
    }

    //JWT 토큰의 유효성을 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // 토큰을 파싱하고 서명을 검증
            return true; // 토큰이 유효하면 true를 반환
        } catch (Exception e) {
            return false; // 예외가 발생하면 false를 반환하여 토큰이 유효하지 않음을 나타냄
        }
    }

    // JWT 토큰에서 모든 클레임을 추출
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 서명 검증을 위한 키를 설정
                .build()
                .parseClaimsJws(token) // 토큰을 파싱하여 클레임을 추출
                .getBody(); // 클레임 본문을 반환
    }

}
