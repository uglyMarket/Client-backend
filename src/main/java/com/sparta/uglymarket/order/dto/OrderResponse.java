package com.sparta.uglymarket.order.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private Long productId;
    private String orderStatus;
    private int quantity;
    private LocalDateTime orderDate;

    public OrderResponse(Long orderId, Long userId, Long productId, String orderStatus, int quantity, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.userId = userId;
        this.productId = productId;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
}
