package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.mapper.OrdersMapper;
import com.sparta.uglymarket.order.service.OrderReviewService;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.domain.ReviewDomain;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.factory.ReviewFactory;
import com.sparta.uglymarket.review.mapper.ReviewMapper;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import com.sparta.uglymarket.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final Validator validator;
    private final FinderService finderService;
    private final OrderReviewService orderService;


    public ReviewService(ReviewRepository reviewRepository, Validator validator, FinderService finderService, OrderReviewService orderService) {
        this.reviewRepository = reviewRepository;
        this.validator = validator;
        this.finderService = finderService;
        this.orderService = orderService;
    }


    //후기 등록
    @Transactional
    public ReviewDomain createReview(ReviewDomain reviewDomain, String phoneNumber) {

        // 회원 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 주문을 도메인으로 변환 (주문의 비즈니스 로직을 사용할 것이기 때문에 도메인으로 가져옴)
        OrdersDomain ordersDomain = OrdersMapper.toDomain(finderService.findOrderById(reviewDomain.getOrderId()));

        // 상품 찾기
        Product product = finderService.findProductById(reviewDomain.getProductId());

        //유저 권한 검증 (주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증)
        validator.validate(ordersDomain.getUserId(), user.getId());

        // 리뷰 엔티티 생성 (ReviewFactory에서 처리) (ordersDomain을 사용하기 때문에 생성자로 처리 불가)
        Review reviewEntity = ReviewFactory.createReviewFromDomain(reviewDomain, ordersDomain, product, user);

        // 리뷰 저장
        Review savedReview = reviewRepository.save(reviewEntity);

        // 단일책임 원칙으로 로직 분리 (주문 상태를 리뷰 작성 상태로 변경)
        orderService.updateOrderStatusToReviewed(ordersDomain.getId());

        // DTO로 변환 후 반환
        return ReviewMapper.toDomain(savedReview);
    }

    //리뷰 삭제
    public void deleteReview(Long reviewId, String phoneNumber) {

        // 회원 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 리뷰 찾기
        Review review = finderService.findReviewById(reviewId);

        // 검증 로직 호출 (리뷰의 유저 아이디와, 토큰에서 가져온 유저 아이디가 같은지 검증)
        validator.validateDeleteReview(review.getUser().getId(), user.getId());

        // 주문 상태를 리뷰 작성 취소로 변경
        orderService.updateOrderStatusToUnreviewed(review.getOrders().getId());

        // 리뷰 삭제
        reviewRepository.delete(review);
    }

    // 특정 리뷰 조회
    public ReviewDomain getReviewById(Long reviewId) {

        // 리뷰 찾기
        Review review = finderService.findReviewById(reviewId);

        // 엔티티 -> 도메인 변환 후 반환
        return ReviewMapper.toDomain(review);
    }


    // 특정 상품의 전체 리뷰 목록 조회
    public List<ReviewDomain> getAllReviewsByProductId(Long productId) {
        // 특정 상품의 모든 리뷰 조회
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        // 엔티티 리스트 -> 도메인 리스트 변환 후 반환
        return ReviewMapper.toDomainList(reviews);

    }

}
