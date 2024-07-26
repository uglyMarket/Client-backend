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
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    //후기 등록
    public ReviewCreateResponse createReview(ReviewCreateRequest request, String phoneNumber) {

        // 회원 찾기
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.USER_NOT_FOUND));

        // 주문 찾기
        Orders orders = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new CustomException(ErrorMsg.ORDER_NOT_FOUND));

        if (!orders.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

        // 상품 찾기
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        // 후기 생성
        Review review = new Review(request, orders, product);
        Review savedReview = reviewRepository.save(review);

        // 반환 객체
        return new ReviewCreateResponse(savedReview);
    }

    //후기 삭제
    public ReviewDeleteResponse deleteReview(Long reviewId, String phoneNumber) {

        // 회원 찾기
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.USER_NOT_FOUND));

        // 후기 찾기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorMsg.REVIEW_NOT_FOUND));

        if (!review.getOrders().getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }

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
