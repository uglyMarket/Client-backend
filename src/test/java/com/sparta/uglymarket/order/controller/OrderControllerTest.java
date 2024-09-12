//package com.sparta.uglymarket.order.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.uglymarket.order.dto.OrderRequest;
//import com.sparta.uglymarket.order.dto.OrderResponse;
//import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
//import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
//import com.sparta.uglymarket.order.entity.Orders;
//import com.sparta.uglymarket.order.service.OrderReviewService;
//import com.sparta.uglymarket.order.service.OrderService;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.user.entity.User;
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
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(OrderController.class)
//class OrderControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private OrderService orderService;
//
//    @MockBean
//    private OrderReviewService orderReviewService;
//
//    @MockBean
//    private TokenService tokenService;
//
//    @MockBean
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper; //JOSN 직렬화, 역직렬화
//
//    private final String token = "Bearer some-jwt-token";
//    private final String phoneNumber = "123456789";
//
//    @BeforeEach
//    public void setUp() {
//        // 필요한 초기화 작업이 있다면 추가
//        given(tokenService.getPhoneNumberFromRequest(any(HttpServletRequest.class)))
//                .willReturn(phoneNumber);
//        given(jwtUtil.validateToken(anyString())).willReturn(true); //JwtUtil의 validateToken을 항상 true를 반환하도록
//
//        Claims claims = mock(Claims.class);
//        given(claims.getSubject()).willReturn(phoneNumber);
//        given(jwtUtil.getAllClaimsFromToken(anyString())).willReturn(claims); //JWT 토큰에서 모든 클레임을 추출
//    }
//
//    @Test
//    @DisplayName("주문 생성 성공")
//    void createOrder_Success() throws Exception {
//        // given
//        OrderRequest orderRequest = mock(OrderRequest.class);
//        given(orderRequest.getProductId()).willReturn(1L);
//        given(orderRequest.getQuantity()).willReturn(2);
//        given(orderRequest.getOrderStatus()).willReturn("주문완료");
//
//        User user = new User(); // 실제 User 객체 생성
//        Product product = new Product(); // 실제 Product 객체 생성
//        Orders orders = new Orders(user, product, orderRequest); // Orders 객체 생성
//
//        OrderResponse orderResponse = new OrderResponse(orders);
//
//        given(orderService.createOrder(any(OrderRequest.class), any(String.class))).willReturn(orderResponse);
//
//        // when & then
//        mockMvc.perform(post("/api/orders")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(orderRequest)))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(objectMapper.writeValueAsString(orderResponse)));
//    }
//
//    @Test
//    @DisplayName("사용자가 후기를 작성할 수 있는 주문 목록 조회 성공")
//    void getPendingReviewOrdersByUser_Success() throws Exception {
//        // given
//        PendingReviewOrderResponse response1 = mock(PendingReviewOrderResponse.class);
//        given(response1.getOrderId()).willReturn(1L);
//        given(response1.getProductId()).willReturn(1L);
//        given(response1.getProductName()).willReturn("Product 1");
//        given(response1.getOrderStatus()).willReturn("주문완료");
//
//        PendingReviewOrderResponse response2 = mock(PendingReviewOrderResponse.class);
//        given(response2.getOrderId()).willReturn(2L);
//        given(response2.getProductId()).willReturn(2L);
//        given(response2.getProductName()).willReturn("Product 2");
//        given(response2.getOrderStatus()).willReturn("배송대기");
//
//        List<PendingReviewOrderResponse> responses = Arrays.asList(response1, response2);
//
//        given(tokenService.getPhoneNumberFromRequest(any())).willReturn(phoneNumber);
//        given(orderReviewService.getPendingReviewOrdersByUserId(phoneNumber)).willReturn(responses);
//
//        // when & then
//        mockMvc.perform(get("/api/orders/user/pending-reviews")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
//    }
//
//    @Test
//    @DisplayName("사용자가 후기를 작성한 주문 목록 조회 성공")
//    void getWrittenReviewOrdersByUser_Success() throws Exception {
//        // given
//        WrittenReviewOrderResponse response1 = mock(WrittenReviewOrderResponse.class);
//        given(response1.getOrderId()).willReturn(1L);
//        given(response1.getProductId()).willReturn(1L);
//        given(response1.getProductName()).willReturn("Product 1");
//        given(response1.getOrderStatus()).willReturn("주문완료");
//
//        WrittenReviewOrderResponse response2 = mock(WrittenReviewOrderResponse.class);
//        given(response2.getOrderId()).willReturn(2L);
//        given(response2.getProductId()).willReturn(2L);
//        given(response2.getProductName()).willReturn("Product 2");
//        given(response2.getOrderStatus()).willReturn("배송완료");
//
//        List<WrittenReviewOrderResponse> responses = Arrays.asList(response1, response2);
//
//        given(tokenService.getPhoneNumberFromRequest(any())).willReturn(phoneNumber);
//        given(orderReviewService.getWrittenReviewOrdersByUserId(phoneNumber)).willReturn(responses);
//
//        // when & then
//        mockMvc.perform(get("/api/orders/user/written-reviews")
//                        .header("Authorization", token)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(responses)));
//    }
//}