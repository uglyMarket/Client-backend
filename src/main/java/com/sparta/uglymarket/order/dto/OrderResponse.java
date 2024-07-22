package com.sparta.uglymarket.order.dto;

import com.sparta.uglymarket.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private Long orderId;
    private Long userId;
    private String usernickname;
    private Long productId;
    private String productTitle;
    private String orderStatus;
    private int quantity;
    private LocalDateTime orderDate;

    public OrderResponse(Order savedOrder) {
        this.orderId = savedOrder.getId();
        this.userId = savedOrder.getUser().getId();
        this.usernickname = savedOrder.getUser().getNickname();
        this.productId = savedOrder.getProduct().getId();
        this.productTitle = savedOrder.getProduct().getTitle();
        this.orderStatus = savedOrder.getOrderStatus();
        this.quantity = savedOrder.getQuantity();
        this.orderDate = savedOrder.getOrderDate();
    }
}
