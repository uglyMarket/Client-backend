package com.sparta.uglymarket.service;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.review.dto.ReviewCreateRequest;
import com.sparta.uglymarket.review.dto.ReviewCreateResponse;
import com.sparta.uglymarket.review.dto.ReviewGetResponse;
import com.sparta.uglymarket.review.dto.ReviewListByProductResponse;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import com.sparta.uglymarket.review.service.ReviewService;
import com.sparta.uglymarket.review.service.validator.ReviewValidator;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewValidator reviewValidator;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, orderRepository, productRepository, userRepository, reviewValidator);
    }


    // 후기 등록 테스트
    @Test
    void createReview() {
        // given
        String phoneNumber = "123456789";

        // ReviewCreateRequest 객체 생성 및 설정 (mock 사용)
        ReviewCreateRequest request = mock(ReviewCreateRequest.class);
        given(request.getContent()).willReturn("상품 좋아요!");
        given(request.getReviewImage()).willReturn("image.jpg");
        given(request.getOrderId()).willReturn(1L);
        given(request.getProductId()).willReturn(1L);

        // 목 객체 생성
        User user = mock(User.class);
        Orders order = mock(Orders.class);
        Product product = mock(Product.class);
        Review review = new Review(request, order, product);

        // 목 객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(orderRepository.findById(request.getOrderId())).willReturn(Optional.of(order));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(reviewRepository.save(any(Review.class))).willReturn(review);


        // when (검증하고 싶은 서비스 실행)
        ReviewCreateResponse response = reviewService.createReview(request, phoneNumber);

        // then (response 검증)
        assertNotNull(response);
        assertEquals(request.getContent(), response.getContent());
        assertEquals(request.getReviewImage(), response.getReviewImage());
        assertEquals(order.getId(), response.getOrderId());
        assertEquals(product.getId(), response.getProductId());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }


    // 후기 삭제 테스트
    @Test
    void deleteReview() {
        // given
        String phoneNumber = "123456789";
        Long reviewId = 1L;

        // 목 객체 생성
        User user = mock(User.class);
        Orders order = mock(Orders.class);
        Review review = mock(Review.class);

        // 목 객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));
        given(review.getOrders()).willReturn(order);

        // when (검증하고 싶은 서비스 실행)
        reviewService.deleteReview(reviewId, phoneNumber);

        // then
        verify(reviewValidator, times(1)).validateDeleteReview(user, review);
        verify(reviewRepository, times(1)).delete(review);
        verify(order, times(1)).unmarkAsReviewed();
    }


    // 특정 후기 조회 테스트
    @Test
    void getReviewById() {
        // given
        Long reviewId = 1L;

        // 목 객체 생성
        Review review = mock(Review.class);
        given(review.getId()).willReturn(reviewId);

        Orders order = mock(Orders.class);
        given(order.getId()).willReturn(1L);
        given(review.getOrders()).willReturn(order);

        Product product = mock(Product.class);
        given(product.getId()).willReturn(1L);
        given(review.getProduct()).willReturn(product);

        // 목 객체 설정
        given(reviewRepository.findById(reviewId)).willReturn(Optional.of(review));

        // when (검증하고 싶은 서비스 실행)
        ReviewGetResponse response = reviewService.getReviewById(reviewId);

        // then (response 검증)
        assertNotNull(response);
        assertEquals(review.getId(), response.getId());
        assertEquals(order.getId(), response.getOrderId());
        assertEquals(product.getId(), response.getProductId());
        verify(reviewRepository, times(1)).findById(reviewId);
    }


    // 특정 상품의 전체 후기 목록 조회 테스트
    @Test
    void getAllReviewsByProductId() {
        // given
        Long productId = 1L;

        // 목 객체 생성
        Orders order1 = mock(Orders.class);
        given(order1.getId()).willReturn(1L);

        Orders order2 = mock(Orders.class);
        given(order2.getId()).willReturn(2L);

        Product product = mock(Product.class);

        Review review1 = mock(Review.class);
        given(review1.getId()).willReturn(1L);
        given(review1.getContent()).willReturn("Content 1");
        given(review1.getReviewImage()).willReturn("Image 1");
        given(review1.getOrders()).willReturn(order1);
        given(review1.getProduct()).willReturn(product);

        Review review2 = mock(Review.class);
        given(review2.getId()).willReturn(2L);
        given(review2.getContent()).willReturn("Content 2");
        given(review2.getReviewImage()).willReturn("Image 2");
        given(review2.getOrders()).willReturn(order2);
        given(review2.getProduct()).willReturn(product);

        // reviews 리스트 객체 생성
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        // 목 객체 설정
        given(reviewRepository.findAllByProductId(productId)).willReturn(reviews);

        // when (검증하고 싶은 서비스 실행)
        List<ReviewListByProductResponse> responses = reviewService.getAllReviewsByProductId(productId);

        // then (리스트 검증)
        assertNotNull(responses);
        assertEquals(2, responses.size());

        // 각 객체 검증
        for (int i = 0; i < reviews.size(); i++) {
            Review review = reviews.get(i);
            ReviewListByProductResponse response = responses.get(i);

            assertEquals(review.getId(), response.getId());
            assertEquals(review.getContent(), response.getContent());
            assertEquals(review.getReviewImage(), response.getReviewImage());
            assertEquals(review.getOrders().getId(), response.getOrderId());
            assertEquals(review.getProduct().getId(), response.getProductId());
        }

        verify(reviewRepository, times(1)).findAllByProductId(productId);
    }
}