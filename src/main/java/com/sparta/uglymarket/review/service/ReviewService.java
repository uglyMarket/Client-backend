package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.dto.*;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import com.sparta.uglymarket.validator.Validator;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final Validator validator;
    private final DtoMapper dtoMapper;
    private final FinderService finderService;


    public ReviewService(ReviewRepository reviewRepository, Validator validator, DtoMapper dtoMapper, FinderService finderService) {
        this.reviewRepository = reviewRepository;
        this.validator = validator;
        this.dtoMapper = dtoMapper;
        this.finderService = finderService;

    }


    //후기 등록
    public ReviewCreateResponse createReview(ReviewCreateRequest request, String phoneNumber) {

        // 회원 찾기
       User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 주문 찾기
        Orders orders = finderService.findOrderById(request.getOrderId());

        // 상품 찾기
        Product product = finderService.findProductById(request.getProductId());

        //검증 로직 사용 (주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증)
        validator.validate(orders, user);

        // 후기 생성
        Review review = new Review(request, orders, product);

        orders.markAsReviewed(); // 주문의 상태를 서비스 계층에서 변경

        Review savedReview = reviewRepository.save(review);

        //dto로 변환 후 반환
        return dtoMapper.toReviewCreateResponse(savedReview);
    }

    //후기 삭제
    public ReviewDeleteResponse deleteReview(Long reviewId, String phoneNumber) {

        // 회원 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 후기 찾기
        Review review = finderService.findReviewById(reviewId);

        //검증 로직 호출 (리뷰의 유저 아이디와, 토큰에서 가져온 유저 아이디가 같은지 검증)
        validator.validateDeleteReview(user, review);

        Orders order = review.getOrders();
        order.unmarkAsReviewed(); // 리뷰 삭제 시 주문의 reviewed 필드를 false로 설정

        //후기 삭제
        reviewRepository.delete(review);

        //dto로 변환 후 반환
        return dtoMapper.toReviewDeleteResponse(review.getId());

    }

    //특정 후기 조회
    public ReviewGetResponse getReviewById(Long reviewId) {
        //후기 찾기
        Review review = finderService.findReviewById(reviewId);

        //dto로 변환 후 반환
        return dtoMapper.toReviewGetResponse(review);
    }


    // 특정 상품의 전체 후기 목록 조회
    public List<ReviewListByProductResponse> getAllReviewsByProductId(Long productId) {
        //특정 상품의 모든 후기 조회
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        //dto로 변환 후 반환
        return dtoMapper.toReviewListByProductResponseList(reviews);

    }

}
