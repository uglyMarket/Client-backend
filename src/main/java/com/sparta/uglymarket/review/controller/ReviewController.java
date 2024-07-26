package com.sparta.uglymarket.review.controller;

import com.sparta.uglymarket.review.dto.*;
import com.sparta.uglymarket.review.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    // 후기 등록
    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createReview(@RequestBody ReviewCreateRequest request, HttpServletRequest httpRequest) {
        String phoneNumber = (String) httpRequest.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        ReviewCreateResponse response = reviewService.createReview(request, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //후기 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId, HttpServletRequest httpRequest) {
        String phoneNumber = (String) httpRequest.getAttribute("phoneNumber");
        if (phoneNumber == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        reviewService.deleteReview(reviewId, phoneNumber);
        return new ResponseEntity<>(new ReviewDeleteResponse(reviewId), HttpStatus.NO_CONTENT);
    }

    // 특정 후기 조회
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewGetResponse> getReviewById(@PathVariable Long reviewId) {
        ReviewGetResponse response = reviewService.getReviewById(reviewId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 상품의 전체 후기 목록 조회
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewListByProductResponse>> getAllReviewsByProductId(@PathVariable Long productId) {
        List<ReviewListByProductResponse> reviews = reviewService.getAllReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
