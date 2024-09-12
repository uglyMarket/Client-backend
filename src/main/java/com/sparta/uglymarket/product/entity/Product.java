package com.sparta.uglymarket.product.entity;

import com.sparta.uglymarket.review.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    public Product(Long id, String title, String content, String image, String deliveryType, String price, boolean ugly, List<Review> reviews) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.deliveryType = deliveryType;
        this.price = price;
        this.ugly = ugly;
        this.reviews = reviews;
    }
}
