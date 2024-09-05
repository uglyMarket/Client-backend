package com.sparta.uglymarket.user.entity;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.user.dto.JoinRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String password;
    private String phoneNumber; //로그인 ID 대용
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    //g
    @OneToMany(mappedBy = "user")
    private List<Orders> orders;


    public User(JoinRequest request, String encodedPassword, Role role) {
        this.nickname = request.getNickname();
        this.password = encodedPassword;
        this.phoneNumber = request.getPhoneNumber();
        this.profileImageUrl = request.getProfileImageUrl();
        this.role = role;
    }
}
