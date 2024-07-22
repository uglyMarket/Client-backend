package com.sparta.uglymarket.product.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String image;
    private String deliveryType;
    private String price;

    @Column(nullable = false) // isUgly 필드는 nullable = false 추가
    private boolean isUgly;

}
