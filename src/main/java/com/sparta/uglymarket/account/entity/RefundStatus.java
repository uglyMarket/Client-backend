package com.sparta.uglymarket.account.entity;

import com.sparta.uglymarket.order.entity.OrderHistory;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class RefundStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_history_id")
    private OrderHistory orderHistory;

    @Column(nullable = false)
    private String refundStatus;

    @Column(nullable = false)
    private String accountNum;
}
