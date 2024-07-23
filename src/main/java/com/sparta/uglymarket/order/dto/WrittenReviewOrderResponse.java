package com.sparta.uglymarket.order.dto;

import com.sparta.uglymarket.order.entity.Orders;
import lombok.Getter;

@Getter
public class WrittenReviewOrderResponse {
    private Long orderId;
    private Long productId;
    private String productName;
    private String orderStatus;

    public WrittenReviewOrderResponse(Orders order) {
        this.orderId = order.getId();
        this.productId = order.getProduct().getId();
        this.productName = order.getProduct().getTitle();
        this.orderStatus = order.getOrderStatus();
    }
}
