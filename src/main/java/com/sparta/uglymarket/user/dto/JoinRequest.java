package com.sparta.uglymarket.user.dto;

import lombok.Getter;

@Getter
public class JoinRequest {
    private String nickname;
    private String password;
    private String phoneNumber;
    private String profileImageUrl;
    private String role;
}
