package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.review.dto.*;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }


    //후기 등록
    public ReviewCreateResponse createReview(ReviewCreateRequest request) {

        //주문 찾기
        Orders orders = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorMsg.ORDER_NOT_FOUND));
        //상품 찾기
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        //후기 생성
        Review review = new Review(request, orders, product);
        Review savedReview = reviewRepository.save(review);

        //반환 객체
        ReviewCreateResponse response = new ReviewCreateResponse(savedReview);

        return response;
    }

    //후기 삭제
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        //후기 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorMsg.REVIEW_NOT_FOUND));

        Orders order = review.getOrders();
        order.unmarkAsReviewed(); // 리뷰 삭제 시 주문의 reviewed 필드를 false로 설정

        //후기 삭제
        reviewRepository.delete(review);

        //반환 객체
        ReviewDeleteResponse response = new ReviewDeleteResponse(review.getId());

        return response;

    }

    //특정 후기 조회
    public ReviewGetResponse getReviewById(Long reviewId) {
        //후기 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorMsg.REVIEW_NOT_FOUND));

        //DTO에 담아주기
        ReviewGetResponse response = new ReviewGetResponse(review);

        return response;
    }

    // 특정 상품의 전체 후기 목록 조회
    public List<ReviewListByProductResponse> getAllReviewsByProductId(Long productId) {
        //특정 상품의 모든 후기 조회
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        //DTO로 담을 리스트
        List<ReviewListByProductResponse> responseList = new ArrayList<>();
        for (Review review : reviews) {
            responseList.add(new ReviewListByProductResponse(review));
        }
        return responseList;
    }

}
