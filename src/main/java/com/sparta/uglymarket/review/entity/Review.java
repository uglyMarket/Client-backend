package com.sparta.uglymarket.review.entity;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String reviewImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Review(String content, String reviewImage, Orders orders, Product product, User user) {
        this.content = content;
        this.reviewImage = reviewImage;
        this.orders = orders;
        this.product = product;
        this.user = user;
    }
}
