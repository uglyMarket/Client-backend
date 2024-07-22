package com.sparta.uglymarket.order.entity;

import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String orderStatus;

}
