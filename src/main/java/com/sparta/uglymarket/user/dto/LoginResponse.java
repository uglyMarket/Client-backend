package com.sparta.uglymarket.user.dto;

import lombok.Getter;

@Getter
public class LoginResponse {
    private String token; // JWT 토큰 필드


    public LoginResponse(String token) {
        this.token = token;
    }
}
