package com.sparta.uglymarket.review.dto;

import com.sparta.uglymarket.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewListByProductResponse {

    private Long id;
    private String content;
    private String reviewImage;
    private Long orderId;
    private Long productId;


    public ReviewListByProductResponse(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.reviewImage = review.getReviewImage();
        this.orderId = review.getOrders().getId();
        this.productId = review.getProduct().getId();
    }
}
