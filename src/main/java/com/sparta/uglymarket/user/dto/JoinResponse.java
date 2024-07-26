package com.sparta.uglymarket.user.dto;

import com.sparta.uglymarket.user.entity.User;
import lombok.Getter;

@Getter
public class JoinResponse {
    private Long id;
    private String nickname;
    private String phoneNumber;
    private String profileImageUrl;
    private String role;

    public JoinResponse(User savedUser) {
        this.id = savedUser.getId();
        this.nickname = savedUser.getNickname();
        this.phoneNumber = savedUser.getPhoneNumber();
        this.profileImageUrl = savedUser.getProfileImageUrl();
        this.role = savedUser.getRole().name();
    }
}
