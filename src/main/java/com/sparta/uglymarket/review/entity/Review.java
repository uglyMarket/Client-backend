package com.sparta.uglymarket.review.entity;

import com.sparta.uglymarket.order.entity.OrderHistory;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

    private String review;
    private String reviewImage;

}
