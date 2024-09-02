package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.review.dto.ReviewCreateResponse;
import com.sparta.uglymarket.review.dto.ReviewDeleteResponse;
import com.sparta.uglymarket.review.dto.ReviewGetResponse;
import com.sparta.uglymarket.review.dto.ReviewListByProductResponse;
import com.sparta.uglymarket.review.entity.Review;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoMapper {

    //리뷰 생성 반환 DTO로 변환
    public ReviewCreateResponse toReviewCreateResponse(Review review) {
        return new ReviewCreateResponse(review);
    }

    //리뷰 삭제 반환 DTO
    public ReviewDeleteResponse toReviewDeleteResponse(Long reviewId) {
        return new ReviewDeleteResponse(reviewId);
    }

    //특정 후기 조회 DTO
    public ReviewGetResponse toReviewGetResponse(Review review) {
        return new ReviewGetResponse(review);
    }

    //상품에 달려있는 리뷰 찾아서 -> DTO 변환
    public ReviewListByProductResponse toReviewListByProductResponse(Review review) {
        return new ReviewListByProductResponse(review);
    }

    //리스트로 상품에 달려있는 리뷰 -> DTO 변환
    public List<ReviewListByProductResponse> toReviewListByProductResponseList(List<Review> reviews) {
        List<ReviewListByProductResponse> responseList = new ArrayList<>();
        for (Review review : reviews) {
            responseList.add(toReviewListByProductResponse(review));
        }
        return responseList;
    }

}
