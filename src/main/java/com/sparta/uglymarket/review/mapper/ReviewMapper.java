package com.sparta.uglymarket.review.mapper;


import com.sparta.uglymarket.review.domain.ReviewDomain;
import com.sparta.uglymarket.review.dto.ReviewCreateRequest;
import com.sparta.uglymarket.review.dto.ReviewCreateResponse;
import com.sparta.uglymarket.review.dto.ReviewGetResponse;
import com.sparta.uglymarket.review.dto.ReviewListByProductResponse;
import com.sparta.uglymarket.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {

    // DTO -> Domain 변환 (리뷰등록)
    public static ReviewDomain toDomain(ReviewCreateRequest request) {
        return new ReviewDomain(
                null,
                request.getContent(),
                request.getReviewImage(),
                request.getOrderId(),
                request.getProductId()
        );
    }

    // Domain -> DTO 변환 (리뷰등록)
    public static ReviewCreateResponse toCreateResponse(ReviewDomain reviewDomain) {
        return new ReviewCreateResponse(
                reviewDomain.getId(),
                reviewDomain.getContent(),
                reviewDomain.getReviewImage(),
                reviewDomain.getOrderId(),
                reviewDomain.getProductId()
        );
    }

    // Domain -> DTO 변환 (특정리뷰 조회)
    public static ReviewGetResponse toGetResponse(ReviewDomain reviewDomain) {
        return new ReviewGetResponse(
                reviewDomain.getId(),
                reviewDomain.getContent(),
                reviewDomain.getReviewImage(),
                reviewDomain.getOrderId(),
                reviewDomain.getProductId()
        );
    }

    //  Domain -> DTO 변환 (특정 상품의 전체 리뷰 목록 조회)
    public static List<ReviewListByProductResponse> toListByProductResponse(List<ReviewDomain> reviewDomains) {

        List<ReviewListByProductResponse> responseList = new ArrayList<>();
        for (ReviewDomain reviewDomain : reviewDomains) {
            responseList.add(new ReviewListByProductResponse(
                    reviewDomain.getId(),
                    reviewDomain.getContent(),
                    reviewDomain.getReviewImage(),
                    reviewDomain.getOrderId(),
                    reviewDomain.getProductId()
            ));
        }
        return responseList;
    }


    // Entity -> Domain 변환
    public static ReviewDomain toDomain(Review review) {
        return new ReviewDomain(
                review.getId(),
                review.getContent(),
                review.getReviewImage(),
                review.getOrders().getId(),
                review.getProduct().getId()
        );
    }

    // 도메인 리스트 -> 엔티티 리스트 변환
    public static List<ReviewDomain> toDomainList(List<Review> reviews) {

        List<ReviewDomain> reviewDomains = new ArrayList<>();
        for (Review review : reviews) {
            reviewDomains.add(toDomain(review));
        }
        return reviewDomains;
    }
}
