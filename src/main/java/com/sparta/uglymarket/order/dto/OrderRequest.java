package com.sparta.uglymarket.order.dto;

import lombok.Getter;

@Getter
public class OrderRequest {
    private Long userId;
    private Long productId;
    private String orderStatus;
    private int quantity;
}
