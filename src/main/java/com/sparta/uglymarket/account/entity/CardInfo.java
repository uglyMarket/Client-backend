package com.sparta.uglymarket.account.entity;

import com.sparta.uglymarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CardInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String cardNum;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String cardCompany;
}
