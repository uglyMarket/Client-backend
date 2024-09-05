package com.sparta.uglymarket.product.entity;

import com.sparta.uglymarket.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

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

    @Column(nullable = false) // ugly 필드는 nullable = false 추가
    private boolean ugly;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;


}
