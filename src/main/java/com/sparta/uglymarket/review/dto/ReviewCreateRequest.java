package com.sparta.uglymarket.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateRequest {

    private String content;
    private String reviewImage;
    private Long orderId;
    private Long productId;
}
