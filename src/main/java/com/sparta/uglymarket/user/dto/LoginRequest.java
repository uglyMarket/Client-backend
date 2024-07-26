package com.sparta.uglymarket.user.dto;


import lombok.Getter;

@Getter
public class LoginRequest {
    private String phoneNumber;
    private String password;
}
