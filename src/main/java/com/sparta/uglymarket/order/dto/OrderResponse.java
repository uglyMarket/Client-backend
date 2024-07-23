package com.sparta.uglymarket.order.dto;

import com.sparta.uglymarket.order.entity.Orders;
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

    public OrderResponse(Orders savedOrders) {
        this.orderId = savedOrders.getId();
        this.userId = savedOrders.getUser().getId();
        this.usernickname = savedOrders.getUser().getNickname();
        this.productId = savedOrders.getProduct().getId();
        this.productTitle = savedOrders.getProduct().getTitle();
        this.orderStatus = savedOrders.getOrderStatus();
        this.quantity = savedOrders.getQuantity();
        this.orderDate = savedOrders.getOrderDate();
    }
}
