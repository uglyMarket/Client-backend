package com.sparta.uglymarket.review.dto;

import com.sparta.uglymarket.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {

    private Long id;
    private String content;
    private String reviewImage;


    public ReviewResponse(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.reviewImage = review.getReviewImage();
    }
}
