package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.dto.*;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.repository.ReviewRepository;
import com.sparta.uglymarket.review.service.validator.ReviewValidator;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ReviewValidator reviewValidator;

    @Mock
    private FinderService finderService;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // 후기 등록 테스트
    @Test
    @DisplayName("리뷰등록 테스트")
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
        ReviewCreateResponse responseDto = new ReviewCreateResponse(review);

        // 목 객체 설정
        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
        given(finderService.findOrderById(request.getOrderId())).willReturn(order);
        given(finderService.findProductById(request.getProductId())).willReturn(product);
        given(reviewRepository.save(any(Review.class))).willReturn(review);
        given(dtoMapper.toReviewCreateResponse(review)).willReturn(responseDto);


        // when (검증하고 싶은 서비스 실행)
        ReviewCreateResponse response = reviewService.createReview(request, phoneNumber);

        // then (response 검증)
        assertNotNull(response);
        assertEquals(request.getContent(), response.getContent());
        assertEquals(request.getReviewImage(), response.getReviewImage());
        verify(reviewValidator, times(1)).validate(order, user); // 검증 로직이 호출되었는지 확인
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(dtoMapper, times(1)).toReviewCreateResponse(review);
    }


    // 후기 삭제 테스트
    @Test
    @DisplayName("후기 삭제 테스트")
    void deleteReview() {
        // given
        String phoneNumber = "123456789";
        Long reviewId = 1L;

        // 목 객체 생성
        User user = mock(User.class);
        Orders order = mock(Orders.class);
        Review review = mock(Review.class);
        ReviewDeleteResponse responseDto = new ReviewDeleteResponse(reviewId);  // 삭제 후 반환할 DTO 객체 생성


        // 목 객체 설정
        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
        given(finderService.findReviewById(reviewId)).willReturn(review);
        given(review.getOrders()).willReturn(order);
        given(dtoMapper.toReviewDeleteResponse(review.getId())).willReturn(responseDto);

        // when (검증하고 싶은 서비스 실행)
        ReviewDeleteResponse response = reviewService.deleteReview(reviewId, phoneNumber);

        // then
        verify(reviewValidator, times(1)).validateDeleteReview(user, review);
        verify(reviewRepository, times(1)).delete(review);
        verify(order, times(1)).unmarkAsReviewed();
        verify(dtoMapper, times(1)).toReviewDeleteResponse(review.getId());  // DTO 매핑이 제대로 이루어졌는지 검증

        // response 검증
        assertNotNull(response);
        assertEquals(reviewId, response.getId());  // 반환된 DTO의 id가 올바른지 검증
    }


    // 특정 후기 조회 테스트
    @Test
    @DisplayName("특정 후기 조회 테스트")
    void getReviewById() {
        // given
        Long reviewId = 1L;

        // 목 객체 생성
        Review review = mock(Review.class);
        given(review.getId()).willReturn(reviewId);

        Orders orders = mock(Orders.class);
        given(orders.getId()).willReturn(1L);
        given(review.getOrders()).willReturn(orders);

        Product product = mock(Product.class);
        given(product.getId()).willReturn(1L);
        given(review.getProduct()).willReturn(product);

        ReviewGetResponse responseDto = new ReviewGetResponse(review);

        // 목 객체 설정
        given(finderService.findReviewById(reviewId)).willReturn(review);
        given(dtoMapper.toReviewGetResponse(review)).willReturn(responseDto);

        // when (검증하고 싶은 서비스 실행)
        ReviewGetResponse response = reviewService.getReviewById(reviewId);

        // then (response 검증)
        assertNotNull(response);
        assertEquals(review.getId(), response.getId());
        assertEquals(orders.getId(), response.getOrderId());
        assertEquals(product.getId(), response.getProductId());
        verify(finderService, times(1)).findReviewById(reviewId);
        verify(dtoMapper, times(1)).toReviewGetResponse(review);
    }


    // 특정 상품의 전체 후기 목록 조회 테스트
    @Test
    @DisplayName("특정 상품의 전체 후기 목록 조회 테스트")
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
        given(review1.getContent()).willReturn("리뷰내용 1");
        given(review1.getReviewImage()).willReturn("이미지 1");
        given(review1.getOrders()).willReturn(order1);
        given(review1.getProduct()).willReturn(product);

        Review review2 = mock(Review.class);
        given(review2.getId()).willReturn(2L);
        given(review2.getContent()).willReturn("리뷰내용 2");
        given(review2.getReviewImage()).willReturn("이미지 2");
        given(review2.getOrders()).willReturn(order2);
        given(review2.getProduct()).willReturn(product);

        // reviews 리스트 객체 생성
        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        // 목 객체 설정
        given(reviewRepository.findAllByProductId(productId)).willReturn(reviews);

        // dto 변환 설정
        List<ReviewListByProductResponse> responseDtoList = new ArrayList<>();
        responseDtoList.add(new ReviewListByProductResponse(review1));
        responseDtoList.add(new ReviewListByProductResponse(review2));
        given(dtoMapper.toReviewListByProductResponseList(reviews)).willReturn(responseDtoList);

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
        verify(dtoMapper, times(1)).toReviewListByProductResponseList(reviews);
    }
}