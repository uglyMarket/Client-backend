package com.sparta.uglymarket.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.order.service.OrderService;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository, userRepository, productRepository);
    }

    // 주문 생성 테스트
    @Test
    void createOrder() {
        // given
        String phoneNumber = "123456789";

        // OrderRequest 객체 생성 및 설정 (목 객체 사용)
        OrderRequest request = mock(OrderRequest.class);
        given(request.getProductId()).willReturn(1L);
        given(request.getOrderStatus()).willReturn("주문완료");
        given(request.getQuantity()).willReturn(2);

        //user, product 목 객체 생성 및 설정
        User user = mock(User.class);
        Product product = mock(Product.class);
        // order 객체 생성 및 설정 (이미 생성자가 있기에 그대로 사용)
        Orders order = new Orders(user, product, request);

        //목객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
        given(orderRepository.save(any(Orders.class))).willReturn(order);

        // when (검증하고 싶은 서비스 실행)
        OrderResponse response = orderService.createOrder(request, phoneNumber);

        // then (response 검증)
        assertNotNull(response);
        assertEquals(order.getId(), response.getOrderId());
        assertEquals(order.getUser().getId(), response.getUserId());
        assertEquals(order.getUser().getNickname(), response.getUsernickname());
        assertEquals(order.getProduct().getId(), response.getProductId());
        assertEquals(order.getProduct().getTitle(), response.getProductTitle());
        assertEquals(order.getOrderStatus(), response.getOrderStatus());
        assertEquals(order.getQuantity(), response.getQuantity());
        assertEquals(order.getOrderDate(), response.getOrderDate());
        verify(orderRepository, times(1)).save(any(Orders.class));
    }

    // 사용자가 후기를 작성할 수 있는 주문 목록 조회 테스트
    @Test
    void getPendingReviewOrdersByUser() {
        // given
        String phoneNumber = "123456789";

        // 목 객체 생성 및 설정
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        Product product1 = mock(Product.class);
        given(product1.getId()).willReturn(1L);
        given(product1.getTitle()).willReturn("Product 1");

        Product product2 = mock(Product.class);
        given(product2.getId()).willReturn(2L);
        given(product2.getTitle()).willReturn("Product 2");

        Orders order1 = mock(Orders.class);
        given(order1.getId()).willReturn(1L);
        given(order1.getProduct()).willReturn(product1);
        given(order1.getOrderStatus()).willReturn("주문완료");

        Orders order2 = mock(Orders.class);
        given(order2.getId()).willReturn(2L);
        given(order2.getProduct()).willReturn(product2);
        given(order2.getOrderStatus()).willReturn("배송대기");


        // orders 리스트 객체 생성
        List<Orders> orders = Arrays.asList(order1, order2);

        // 목객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(orderRepository.findAllByUserIdAndReviewedFalse(user.getId())).willReturn(orders);

        // when (검증하고 싶은 서비스 실행)
        List<PendingReviewOrderResponse> responses = orderService.getPendingReviewOrdersByUserId(phoneNumber);

        // then (리스트 검증)
        assertNotNull(responses);
        assertEquals(2, responses.size());

        // 각 객체 검증
        for (int i = 0; i < orders.size(); i++) {
            Orders order = orders.get(i);
            PendingReviewOrderResponse response = responses.get(i);

            assertEquals(order.getId(), response.getOrderId());
            assertEquals(order.getProduct().getId(), response.getProductId());
            assertEquals(order.getProduct().getTitle(), response.getProductName());
            assertEquals(order.getOrderStatus(), response.getOrderStatus());
        }

        verify(orderRepository, times(1)).findAllByUserIdAndReviewedFalse(user.getId());
    }

    // 사용자가 후기를 작성한 주문 목록 조회 테스트
    @Test
    void getWrittenReviewOrdersByUser() {
        // given
        String phoneNumber = "123456789";

        // 목 객체 생성 및 설정
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        Product product1 = mock(Product.class);
        given(product1.getId()).willReturn(1L);
        given(product1.getTitle()).willReturn("Product 1");

        Product product2 = mock(Product.class);
        given(product2.getId()).willReturn(2L);
        given(product2.getTitle()).willReturn("Product 2");

        Orders order1 = mock(Orders.class);
        given(order1.getId()).willReturn(1L);
        given(order1.getProduct()).willReturn(product1);
        given(order1.getOrderStatus()).willReturn("주문완료");

        Orders order2 = mock(Orders.class);
        given(order2.getId()).willReturn(2L);
        given(order2.getProduct()).willReturn(product2);
        given(order2.getOrderStatus()).willReturn("배송완료");

        // orders 리스트 객체 생성
        List<Orders> orders = Arrays.asList(order1, order2);

        // 목 객체 설정
        given(userRepository.findByPhoneNumber(phoneNumber)).willReturn(Optional.of(user));
        given(orderRepository.findAllByUserIdAndReviewedTrue(user.getId())).willReturn(orders);

        // when (검증하고 싶은 서비스 실행)
        List<WrittenReviewOrderResponse> responses = orderService.getWrittenReviewOrdersByUserId(phoneNumber);

        // then (리스트 검증)
        assertNotNull(responses);
        assertEquals(2, responses.size());

        // 각 객체 검증
        for (int i = 0; i < orders.size(); i++) {
            Orders order = orders.get(i);
            WrittenReviewOrderResponse response = responses.get(i);

            assertEquals(order.getId(), response.getOrderId());
            assertEquals(order.getProduct().getId(), response.getProductId());
            assertEquals(order.getProduct().getTitle(), response.getProductName());
            assertEquals(order.getOrderStatus(), response.getOrderStatus());
        }

        verify(orderRepository, times(1)).findAllByUserIdAndReviewedTrue(user.getId());
    }
}