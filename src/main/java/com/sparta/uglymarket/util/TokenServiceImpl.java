package com.sparta.uglymarket.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService{

    private final JwtUtil jwtUtil;

    public TokenServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String getPhoneNumberFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return null;  // 헤더가 없거나 Bearer 토큰이 아닐 경우 null 반환
        }

        String token = header.substring(7);
        return jwtUtil.getPhoneNumberFromToken(token);
    }
}
