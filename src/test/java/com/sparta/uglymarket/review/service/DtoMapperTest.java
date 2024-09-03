package com.sparta.uglymarket.review.service;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.dto.ReviewCreateResponse;
import com.sparta.uglymarket.review.dto.ReviewDeleteResponse;
import com.sparta.uglymarket.review.dto.ReviewGetResponse;
import com.sparta.uglymarket.review.dto.ReviewListByProductResponse;
import com.sparta.uglymarket.review.entity.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class DtoMapperTest {

    private DtoMapper dtoMapper;

    @BeforeEach
    void setUp() {
        dtoMapper = new DtoMapper();
    }

    @Test
    @DisplayName("리뷰생성 반환DTO 변환 테스트")
    void toReviewCreateResponse() {
        // given
        Review review = mock(Review.class);
        given(review.getId()).willReturn(1L);
        given(review.getContent()).willReturn("상품 좋아요!");
        given(review.getReviewImage()).willReturn("이미지.jpg");

        Orders orders = mock(Orders.class);
        given(orders.getId()).willReturn(1L);
        given(review.getOrders()).willReturn(orders);

        Product product = mock(Product.class);
        given(product.getId()).willReturn(1L);
        given(review.getProduct()).willReturn(product);

        // when
        ReviewCreateResponse response = dtoMapper.toReviewCreateResponse(review);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("상품 좋아요!", response.getContent());
        assertEquals("이미지.jpg", response.getReviewImage());
        assertEquals(1L, response.getOrderId());
        assertEquals(1L, response.getProductId());
    }


    @Test
    @DisplayName("리뷰삭제 반환DTO 변환 테스트")
    void toReviewDeleteResponse() {
        // given
        Long reviewId = 1L;

        // when
        ReviewDeleteResponse response = dtoMapper.toReviewDeleteResponse(reviewId);

        // then
        assertNotNull(response);
        assertEquals(reviewId, response.getId());
        assertEquals("리뷰가 삭제되었습니다.", response.getMessage());
    }

    @Test
    @DisplayName("특정 후기 조회 DTO변환 테스트")
    void toReviewGetResponse() {
        // given

        Orders orders = mock(Orders.class);
        given(orders.getId()).willReturn(1L);

        Product product = mock(Product.class);
        given(product.getId()).willReturn(1L);

        Review review = mock(Review.class);
        given(review.getId()).willReturn(1L);
        given(review.getContent()).willReturn("상품 좋아요!");
        given(review.getReviewImage()).willReturn("이미지.jpg");
        given(review.getOrders()).willReturn(orders);
        given(review.getProduct()).willReturn(product);


        // when
        ReviewGetResponse response = dtoMapper.toReviewGetResponse(review);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("상품 좋아요!", response.getContent());
        assertEquals("이미지.jpg", response.getReviewImage());
        assertEquals(1L, response.getOrderId());
        assertEquals(1L, response.getProductId());
    }

    @Test
    @DisplayName("상품에 달려있는 리뷰 찾아서 DTO 변환 테스트")
    void toReviewListByProductResponse() {
        // given
        Orders orders = mock(Orders.class);
        given(orders.getId()).willReturn(1L);

        Product product = mock(Product.class);
        given(product.getId()).willReturn(1L);

        Review review = mock(Review.class);
        given(review.getId()).willReturn(1L);
        given(review.getContent()).willReturn("상품 좋아요!");
        given(review.getReviewImage()).willReturn("이미지.jpg");
        given(review.getOrders()).willReturn(orders);
        given(review.getProduct()).willReturn(product);

        // when
        ReviewListByProductResponse response = dtoMapper.toReviewListByProductResponse(review);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("상품 좋아요!", response.getContent());
        assertEquals("이미지.jpg", response.getReviewImage());
        assertEquals(1L, response.getOrderId());
        assertEquals(1L, response.getProductId());
    }

    @Test
    @DisplayName("리스트로 상품에 달려있는 리뷰 DTO 변환 리스트 테스트")
    void toReviewListByProductResponseList() {
        // given
        Product product1 = mock(Product.class);
        given(product1.getId()).willReturn(1L);

        Product product2 = mock(Product.class);
        given(product2.getId()).willReturn(2L);

        Orders orders1 = mock(Orders.class);
        given(orders1.getId()).willReturn(1L);

        Orders orders2 = mock(Orders.class);
        given(orders2.getId()).willReturn(2L);

        Review review1 = mock(Review.class);
        given(review1.getId()).willReturn(1L);
        given(review1.getContent()).willReturn("상품 좋아요!");
        given(review1.getReviewImage()).willReturn("이미지1.jpg");
        given(review1.getOrders()).willReturn(orders1);
        given(review1.getProduct()).willReturn(product1);

        Review review2 = mock(Review.class);
        given(review2.getId()).willReturn(2L);
        given(review2.getContent()).willReturn("상품 별로에요");
        given(review2.getReviewImage()).willReturn("이미지2.jpg");
        given(review2.getOrders()).willReturn(orders2);
        given(review2.getProduct()).willReturn(product2);

        List<Review> reviews = new ArrayList<>();
        reviews.add(review1);
        reviews.add(review2);

        // when
        List<ReviewListByProductResponse> responseList = dtoMapper.toReviewListByProductResponseList(reviews);

        // then
        assertNotNull(responseList);
        assertEquals(2, responseList.size());

        // 첫 번째 리뷰 검증 (각자검증)
        ReviewListByProductResponse response1 = responseList.get(0);
        assertEquals(1L, response1.getId());
        assertEquals("상품 좋아요!", response1.getContent());
        assertEquals("이미지1.jpg", response1.getReviewImage());
        assertEquals(1L, response1.getOrderId());
        assertEquals(1L, response1.getProductId());

        // 두 번째 리뷰 검증
        ReviewListByProductResponse response2 = responseList.get(1);
        assertEquals(2L, response2.getId());
        assertEquals("상품 별로에요", response2.getContent());
        assertEquals("이미지2.jpg", response2.getReviewImage());
        assertEquals(2L, response2.getOrderId());
        assertEquals(2L, response2.getProductId());

        verify(review1, times(1)).getId();
        verify(review2, times(1)).getId();

    }
}