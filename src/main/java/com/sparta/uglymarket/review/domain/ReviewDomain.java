package com.sparta.uglymarket.review.domain;


import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;

public class ReviewDomain {

    private Long id;
    private String content;
    private String reviewImage;
    private Long orderId;
    private Long productId;


    // 비즈니스 로직
    public void validateContent() {
        if (this.content == null || this.content.isEmpty()) {
            throw new CustomException(ErrorMsg.REVIEW_NOT_FOUND);
        }

    }

    public ReviewDomain(Long id, String content, String reviewImage, Long orderId, Long productId) {
        this.id = id;
        this.content = content;
        this.reviewImage = reviewImage;
        this.orderId = orderId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }
}
