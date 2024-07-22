package com.sparta.uglymarket.order.entity;

import com.sparta.uglymarket.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class CardProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int count;
}
