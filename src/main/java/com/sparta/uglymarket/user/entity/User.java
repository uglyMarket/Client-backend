package com.sparta.uglymarket.user.entity;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.order.entity.Orders;
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

    @OneToMany(mappedBy = "user")
    private List<Orders> orders;


    public User(Long id, String nickname, String password, String phoneNumber, String profileImageUrl, Role role, List<Orders> orders) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.profileImageUrl = profileImageUrl;
        this.role = role;
        this.orders = orders;
    }
}
