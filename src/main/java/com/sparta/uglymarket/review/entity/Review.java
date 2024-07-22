package com.sparta.uglymarket.review.entity;

import com.sparta.uglymarket.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String review;
    private String reviewImage;

}
