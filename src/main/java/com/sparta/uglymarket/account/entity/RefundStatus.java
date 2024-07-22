package com.sparta.uglymarket.account.entity;

import com.sparta.uglymarket.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefundStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String refundStatus;

    @Column(nullable = false)
    private String accountNum;
}
