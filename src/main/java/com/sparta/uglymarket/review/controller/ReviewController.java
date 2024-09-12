package com.sparta.uglymarket.review.controller;

import com.sparta.uglymarket.review.domain.ReviewDomain;
import com.sparta.uglymarket.review.dto.*;
import com.sparta.uglymarket.review.mapper.ReviewMapper;
import com.sparta.uglymarket.review.service.ReviewService;
import com.sparta.uglymarket.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final TokenService tokenService;

    public ReviewController(ReviewService reviewService, TokenService tokenService) {
        this.reviewService = reviewService;
        this.tokenService = tokenService;
    }


    // 후기 등록
    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest request, HttpServletRequest httpRequest) {

        // 헤더에서 폰 번호 추출
        String phoneNumber = tokenService.getPhoneNumberFromRequest(httpRequest);

        // DTO -> Domain 변환 (ReviewMapper 사용)
        ReviewDomain reviewDomain = ReviewMapper.toDomain(request);

        // 서비스에 도메인 객체 전달하여 처리
        ReviewDomain createdReview = reviewService.createReview(reviewDomain, phoneNumber);

        // Domain -> DTO 변환 후 응답 반환
        ReviewCreateResponse response = ReviewMapper.toCreateResponse(createdReview);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //후기 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId, HttpServletRequest httpRequest) {
        String phoneNumber = tokenService.getPhoneNumberFromRequest(httpRequest);

        // 서비스에 도메인 객체 전달하여 처리
        reviewService.deleteReview(reviewId, phoneNumber);

        // 응답 반환
        ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 후기 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewGetResponse> getReviewById(@PathVariable Long reviewId) {
        // 서비스에서 도메인 객체를 가져오기
        ReviewDomain reviewDomain = reviewService.getReviewById(reviewId);

        // Domain -> DTO 변환 후 응답 반환
        ReviewGetResponse response = ReviewMapper.toGetResponse(reviewDomain);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 상품의 전체 후기 목록 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewListByProductResponse>> getAllReviewsByProductId(@PathVariable Long productId) {

        // 서비스에서 도메인 객체 리스트를 가져오기
        List<ReviewDomain> reviewDomains = reviewService.getAllReviewsByProductId(productId);

        // Domain 리스트 -> DTO 리스트 변환 후 응답 반환
        List<ReviewListByProductResponse> responseList = ReviewMapper.toListByProductResponse(reviewDomains);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

}
