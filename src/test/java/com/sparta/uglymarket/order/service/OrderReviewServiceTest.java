//package com.sparta.uglymarket.order.service;
//
//import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
//import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
//import com.sparta.uglymarket.order.entity.Orders;
//import com.sparta.uglymarket.order.repository.OrderRepository;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.user.entity.User;
//import com.sparta.uglymarket.util.FinderService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//class OrderReviewServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private FinderService finderService;
//
//    @InjectMocks
//    private OrderReviewService orderReviewService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        orderReviewService = new OrderReviewService(orderRepository, finderService);
//    }
//
//
//
//    // 사용자가 후기를 작성할 수 있는 주문 목록 조회 테스트
//    @Test
//    @DisplayName("사용자가 후기를 작성할 수 있는 주문 목록 조회")
//    void getPendingReviewOrdersByUser() {
//        // given
//        String phoneNumber = "123456789";
//
//        // 목 객체 생성 및 설정
//        User user = mock(User.class);
//        given(user.getId()).willReturn(1L);
//
//        Product product1 = mock(Product.class);
//        given(product1.getId()).willReturn(1L);
//        given(product1.getTitle()).willReturn("Product 1");
//
//        Product product2 = mock(Product.class);
//        given(product2.getId()).willReturn(2L);
//        given(product2.getTitle()).willReturn("Product 2");
//
//        Orders order1 = mock(Orders.class);
//        given(order1.getId()).willReturn(1L);
//        given(order1.getProduct()).willReturn(product1);
//        given(order1.getOrderStatus()).willReturn("주문완료");
//
//        Orders order2 = mock(Orders.class);
//        given(order2.getId()).willReturn(2L);
//        given(order2.getProduct()).willReturn(product2);
//        given(order2.getOrderStatus()).willReturn("배송대기");
//
//
//        // orders 리스트 객체 생성
//        List<Orders> orders = Arrays.asList(order1, order2);
//
//        // 목객체 설정
//        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
//        given(orderRepository.findAllByUserIdAndReviewedFalse(user.getId())).willReturn(orders);
//
//        // when (검증하고 싶은 서비스 실행)
//        List<PendingReviewOrderResponse> responses = orderReviewService.getPendingReviewOrdersByUserId(phoneNumber);
//
//        // then (리스트 검증)
//        assertNotNull(responses);
//        assertEquals(2, responses.size());
//
//        // 각 객체 검증
//        for (int i = 0; i < orders.size(); i++) {
//            Orders order = orders.get(i);
//            PendingReviewOrderResponse response = responses.get(i);
//
//            assertEquals(order.getId(), response.getOrderId());
//            assertEquals(order.getProduct().getId(), response.getProductId());
//            assertEquals(order.getProduct().getTitle(), response.getProductName());
//            assertEquals(order.getOrderStatus(), response.getOrderStatus());
//        }
//
//        verify(orderRepository, times(1)).findAllByUserIdAndReviewedFalse(user.getId());
//    }
//
//    // 사용자가 후기를 작성한 주문 목록 조회 테스트
//    @Test
//    @DisplayName("사용자가 후기를 작성한 주문 목록 조회")
//    void getWrittenReviewOrdersByUser() {
//        // given
//        String phoneNumber = "123456789";
//
//        // 목 객체 생성 및 설정
//        User user = mock(User.class);
//        given(user.getId()).willReturn(1L);
//
//        Product product1 = mock(Product.class);
//        given(product1.getId()).willReturn(1L);
//        given(product1.getTitle()).willReturn("Product 1");
//
//        Product product2 = mock(Product.class);
//        given(product2.getId()).willReturn(2L);
//        given(product2.getTitle()).willReturn("Product 2");
//
//        Orders order1 = mock(Orders.class);
//        given(order1.getId()).willReturn(1L);
//        given(order1.getProduct()).willReturn(product1);
//        given(order1.getOrderStatus()).willReturn("주문완료");
//
//        Orders order2 = mock(Orders.class);
//        given(order2.getId()).willReturn(2L);
//        given(order2.getProduct()).willReturn(product2);
//        given(order2.getOrderStatus()).willReturn("배송완료");
//
//        // orders 리스트 객체 생성
//        List<Orders> orders = Arrays.asList(order1, order2);
//
//        // 목 객체 설정
//        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
//        given(orderRepository.findAllByUserIdAndReviewedTrue(user.getId())).willReturn(orders);
//
//        // when (검증하고 싶은 서비스 실행)
//        List<WrittenReviewOrderResponse> responses = orderReviewService.getWrittenReviewOrdersByUserId(phoneNumber);
//
//        // then (리스트 검증)
//        assertNotNull(responses);
//        assertEquals(2, responses.size());
//
//        // 각 객체 검증
//        for (int i = 0; i < orders.size(); i++) {
//            Orders order = orders.get(i);
//            WrittenReviewOrderResponse response = responses.get(i);
//
//            assertEquals(order.getId(), response.getOrderId());
//            assertEquals(order.getProduct().getId(), response.getProductId());
//            assertEquals(order.getProduct().getTitle(), response.getProductName());
//            assertEquals(order.getOrderStatus(), response.getOrderStatus());
//        }
//
//        verify(orderRepository, times(1)).findAllByUserIdAndReviewedTrue(user.getId());
//    }
//}