//package com.sparta.uglymarket.review.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.uglymarket.order.entity.Orders;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.review.dto.*;
//import com.sparta.uglymarket.review.entity.Review;
//import com.sparta.uglymarket.review.service.ReviewService;
//import com.sparta.uglymarket.util.JwtUtil;
//import com.sparta.uglymarket.util.TokenService;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.http.HttpServletRequest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(ReviewController.class)
//class ReviewControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ReviewService reviewService;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private final String token = "Bearer jwt_token";
//    private final String phoneNumber = "123456789";
//
//
//    @BeforeEach
//    public void setUp() {
//        // 기본 모킹 설정
//        given(tokenService.getPhoneNumberFromRequest(any(HttpServletRequest.class)))
//                .willReturn(phoneNumber);
//
//        given(jwtUtil.validateToken(anyString())).willReturn(true); //JwtUtil의 validateToken을 항상 true를 반환하도록
//
//        Claims claims = mock(Claims.class);
//        given(claims.getSubject()).willReturn(phoneNumber);
//        given(jwtUtil.getAllClaimsFromToken(anyString())).willReturn(claims); //JWT 토큰에서 모든 클레임을 추출
//    }
//
//
//    @Test
//    @DisplayName("후기 등록 성공")
//    void createReview_Success() throws Exception {
//        // given
//        ReviewCreateRequest request = mock(ReviewCreateRequest.class);
//
//        Orders orders = mock(Orders.class);
//        given(orders.getId()).willReturn(1L);
//
//        Product product = mock(Product.class);
//        given(product.getId()).willReturn(1L);
//
//        Review review = mock(Review.class);
//        given(review.getId()).willReturn(1L);
//        given(review.getContent()).willReturn("최고의 상품입니다!");
//        given(review.getReviewImage()).willReturn("이미지.jpg");
//        given(review.getOrders()).willReturn(orders);
//        given(review.getProduct()).willReturn(product);
//
//        ReviewCreateResponse response = new ReviewCreateResponse(review);
//
//        given(reviewService.createReview(any(ReviewCreateRequest.class), any(String.class)))
//                .willReturn(response);
//
//        // when & then
//        mockMvc.perform(post("/api/reviews")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.content").value("최고의 상품입니다!"))
//                .andExpect(jsonPath("$.reviewImage").value("이미지.jpg"))
//                .andExpect(jsonPath("$.orderId").value(1L))
//                .andExpect(jsonPath("$.productId").value(1L));
//    }
//
//    @Test
//    @DisplayName("후기 등록 실패: 인증되지 않은 사용자")
//    void createReview_Fail() throws Exception {
//        // given
//        ReviewCreateRequest request = new ReviewCreateRequest(); // 실제 DTO 객체를 사용하여 테스트
//
//        // TokenService가 phoneNumber를 반환하지 않도록 설정
//        given(tokenService.getPhoneNumberFromRequest(any(HttpServletRequest.class))).willReturn(null);
//
//        // when & then
//        mockMvc.perform(post("/api/reviews")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isUnauthorized());  // HTTP 401 상태 코드를 기대
//    }
//
//    @Test
//    @DisplayName("후기 삭제 성공")
//    void deleteReview_Success() throws Exception {
//        // given
//        Long reviewId = 1L;
//        ReviewDeleteResponse response = new ReviewDeleteResponse(reviewId);
//
//        // ReviewService의 deleteReview 메서드가 정상적으로 호출되는지 확인
//        // 해당 테스트에서는 ReviewService의 deleteReview 메서드는 아무 일도 하지 않음
//        given(reviewService.deleteReview(anyLong(), any(String.class))).willReturn(response);
//
//        // when & then
//        mockMvc.perform(delete("/api/reviews/delete/{reviewId}", reviewId)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent())
//                .andExpect(jsonPath("$.id").value(reviewId))
//                .andExpect(jsonPath("$.message").value("리뷰가 삭제되었습니다."));
//    }
//
//    @Test
//    @DisplayName("후기 삭제 실패: 인증되지 않은 사용자")
//    void deleteReview_Fail() throws Exception {
//        // given
//        Long reviewId = 1L;
//
//        // TokenService가 phoneNumber를 반환하지 않도록 설정 (null 반환)
//        given(tokenService.getPhoneNumberFromRequest(any(HttpServletRequest.class))).willReturn(null);
//
//        // when & then
//        mockMvc.perform(delete("/api/reviews/delete/{reviewId}", reviewId)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isUnauthorized())
//                .andExpect(jsonPath("$").doesNotExist()); // 응답 본문이 없는지 확인
//    }
//
//    @Test
//    @DisplayName("특정 후기 조회 성공")
//    void getReviewById_Success() throws Exception {
//        // given
//        Long reviewId = 1L;
//
//        Orders orders = mock(Orders.class);
//        given(orders.getId()).willReturn(1L);
//
//        Product product = mock(Product.class);
//        given(product.getId()).willReturn(1L);
//
//        Review review = mock(Review.class);
//        given(review.getId()).willReturn(reviewId);
//        given(review.getContent()).willReturn("최고의 상품입니다!");
//        given(review.getReviewImage()).willReturn("이미지.jpg");
//        given(review.getOrders()).willReturn(orders);
//        given(review.getProduct()).willReturn(product);
//
//        ReviewGetResponse response = new ReviewGetResponse(review);
//
//        given(reviewService.getReviewById(anyLong())).willReturn(response);
//
//        // when & then
//        mockMvc.perform(get("/api/reviews/{reviewId}", reviewId)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(reviewId))
//                .andExpect(jsonPath("$.content").value("최고의 상품입니다!"))
//                .andExpect(jsonPath("$.reviewImage").value("이미지.jpg"))
//                .andExpect(jsonPath("$.orderId").value(1L))
//                .andExpect(jsonPath("$.productId").value(1L));
//    }
//
//
//    @Test
//    @DisplayName("특정 상품의 전체 후기 목록 조회 성공")
//    void getAllReviewsByProductId_Success() throws Exception {
//        // given
//        Long productId = 1L;
//
//        Orders orders = mock(Orders.class);
//        given(orders.getId()).willReturn(1L);
//
//        Product product = mock(Product.class);
//        given(product.getId()).willReturn(productId);
//
//        Review review1 = mock(Review.class);
//        given(review1.getId()).willReturn(1L);
//        given(review1.getContent()).willReturn("첫 번째 리뷰 내용");
//        given(review1.getReviewImage()).willReturn("이미지1.jpg");
//        given(review1.getOrders()).willReturn(orders);
//        given(review1.getProduct()).willReturn(product);
//
//        Review review2 = mock(Review.class);
//        given(review2.getId()).willReturn(2L);
//        given(review2.getContent()).willReturn("두 번째 리뷰 내용");
//        given(review2.getReviewImage()).willReturn("이미지2.jpg");
//        given(review2.getOrders()).willReturn(orders);
//        given(review2.getProduct()).willReturn(product);
//
//        List<ReviewListByProductResponse> responses = List.of(
//                new ReviewListByProductResponse(review1),
//                new ReviewListByProductResponse(review2)
//        );
//
//        given(reviewService.getAllReviewsByProductId(anyLong())).willReturn(responses);
//
//        // when & then
//        mockMvc.perform(get("/api/reviews/product/{productId}", productId)
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].content").value("첫 번째 리뷰 내용"))
//                .andExpect(jsonPath("$[0].reviewImage").value("이미지1.jpg"))
//                .andExpect(jsonPath("$[0].orderId").value(1L))
//                .andExpect(jsonPath("$[0].productId").value(productId))
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].content").value("두 번째 리뷰 내용"))
//                .andExpect(jsonPath("$[1].reviewImage").value("이미지2.jpg"))
//                .andExpect(jsonPath("$[1].orderId").value(1L))
//                .andExpect(jsonPath("$[1].productId").value(productId));
//    }
//}