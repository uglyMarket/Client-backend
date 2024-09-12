package com.sparta.uglymarket.user.domain;

import com.sparta.uglymarket.enums.Role;

import java.util.List;

public class UserDomain {

    private Long id;
    private String nickname;
    private String password;
    private String phoneNumber;
    private String profileImageUrl;
    private Role role;
    private List<Long> ordersId;
    private String token;


    public UserDomain(Long id, String nickname, String password, String phoneNumber, String profileImageUrl, Role role, List<Long> ordersId, String token) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.ordersId = ordersId;
        this.token = token;

    }

    // 비즈니스 로직 (판매자 여부 확인)
    public boolean isSeller() {
        return this.role == Role.SELLER;
    }

    // 비즈니스 로직 (권한 변경)
    public void changeRole(Role newRole) {
        this.role = newRole;
    }

    // 비즈니스 로직 (비밀번호 저장)
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    // 비즈니스 로직 (비밀번호 확인)
    public boolean checkPassword(String rawPassword) {
        return this.password.equals(rawPassword);  // 인코딩된 비밀번호와 비교하는 로직만 유지
    }

    // 비즈니스 로직 (토큰값 담기)
    public void token(String token) {
        this.token = token;
    }


    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Role getRole() {
        return role;
    }

    public List<Long> getOrdersId() {
        return ordersId;
    }

    public String getToken() {
        return token;
    }
}
