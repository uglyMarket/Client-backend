package com.sparta.uglymarket.util;

import jakarta.servlet.http.HttpServletRequest;

public class TokenServiceImpl implements TokenService{

    private final JwtUtil jwtUtil;

    public TokenServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String getPhoneNumberFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return jwtUtil.getPhoneNumberFromToken(token);
    }
}
