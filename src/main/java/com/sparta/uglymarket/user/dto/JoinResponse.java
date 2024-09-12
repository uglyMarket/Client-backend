package com.sparta.uglymarket.user.dto;

import lombok.Getter;

@Getter
public class JoinResponse {
    private Long id;
    private String nickname;
    private String phoneNumber;
    private String profileImageUrl;
    private String role;

    public JoinResponse(Long id, String nickname, String phoneNumber, String profileImageUrl, String role) {
        this.id = id;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
    }
}
