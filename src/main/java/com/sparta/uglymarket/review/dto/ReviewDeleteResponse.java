package com.sparta.uglymarket.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDeleteResponse {

    private Long id;
    private String message;


    public ReviewDeleteResponse(Long id) {
        this.id = id;
        this.message = "리뷰가 삭제되었습니다.";
    }
}
