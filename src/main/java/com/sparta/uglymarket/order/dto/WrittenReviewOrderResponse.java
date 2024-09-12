package com.sparta.uglymarket.order.dto;

import lombok.Getter;

@Getter
public class WrittenReviewOrderResponse {
    private Long orderId;
    private Long productId;
    private String orderStatus;

    public WrittenReviewOrderResponse(Long orderId, Long productId, String orderStatus) {
        this.orderId = orderId;
        this.productId = productId;
        this.orderStatus = orderStatus;
    }
}
