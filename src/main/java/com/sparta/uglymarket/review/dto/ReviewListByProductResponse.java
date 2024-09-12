package com.sparta.uglymarket.review.dto;

import lombok.Getter;

@Getter
public class ReviewListByProductResponse {

    private Long id;
    private String content;
    private String reviewImage;
    private Long orderId;
    private Long productId;


    public ReviewListByProductResponse(Long id, String content, String reviewImage, Long orderId, Long productId) {
        this.id = id;
        this.content = content;
        this.reviewImage = reviewImage;
        this.orderId = orderId;
        this.productId = productId;
    }
}
