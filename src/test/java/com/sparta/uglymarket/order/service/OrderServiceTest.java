//package com.sparta.uglymarket.order.service;
//
//import com.sparta.uglymarket.exception.CustomException;
//import com.sparta.uglymarket.exception.ErrorMsg;
//import com.sparta.uglymarket.order.dto.OrderRequest;
//import com.sparta.uglymarket.order.dto.OrderResponse;
//import com.sparta.uglymarket.order.entity.Orders;
//import com.sparta.uglymarket.order.repository.OrderRepository;
//import com.sparta.uglymarket.product.entity.Product;
//import com.sparta.uglymarket.product.repository.ProductRepository;
//import com.sparta.uglymarket.user.entity.User;
//import com.sparta.uglymarket.util.FinderService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private FinderService finderService;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    @InjectMocks
//    private OrderReviewService orderReviewService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        orderService = new OrderService(orderRepository, finderService, productRepository);
//    }
//
//    // 주문 생성 테스트
//    @Test
//    @DisplayName("주문생성 성공")
//    void createOrder() {
//        // given
//        String phoneNumber = "123456789";
//
//        // OrderRequest 객체 생성 및 설정 (목 객체 사용)
//        OrderRequest request = mock(OrderRequest.class);
//        given(request.getProductId()).willReturn(1L);
//        given(request.getOrderStatus()).willReturn("주문완료");
//        given(request.getQuantity()).willReturn(2);
//
//        //user, product 목 객체 생성 및 설정
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        // order 객체 생성 및 설정 (이미 생성자가 있기에 그대로 사용)
//        Orders order = new Orders(user, product, request);
//
//        //목객체 설정
//        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
//        given(productRepository.findById(request.getProductId())).willReturn(Optional.of(product));
//        given(orderRepository.save(any(Orders.class))).willReturn(order);
//
//        // when (검증하고 싶은 서비스 실행)
//        OrderResponse response = orderService.createOrder(request, phoneNumber);
//
//        // then (response 검증)
//        assertNotNull(response);
//        assertEquals(order.getId(), response.getOrderId());
//        assertEquals(order.getUser().getId(), response.getUserId());
//        assertEquals(order.getUser().getNickname(), response.getUsernickname());
//        assertEquals(order.getProduct().getId(), response.getProductId());
//        assertEquals(order.getProduct().getTitle(), response.getProductTitle());
//        assertEquals(order.getOrderStatus(), response.getOrderStatus());
//        assertEquals(order.getQuantity(), response.getQuantity());
//        assertEquals(order.getOrderDate(), response.getOrderDate());
//        verify(orderRepository, times(1)).save(any(Orders.class));
//    }
//
//    @Test
//    @DisplayName("주문생성 시 상품이 없는 오류")
//    void createOrder_ProductNotFound() {
//        // given
//        String phoneNumber = "123456789";
//        OrderRequest request = mock(OrderRequest.class);
//        given(request.getProductId()).willReturn(1L);
//        given(request.getOrderStatus()).willReturn("주문완료");
//        given(request.getQuantity()).willReturn(2);
//
//        User user = new User(); // 실제 객체 생성
//
//        given(finderService.findUserByPhoneNumber(phoneNumber)).willReturn(user);
//        given(productRepository.findById(request.getProductId())).willReturn(Optional.empty());
//
//        // when & then
//        CustomException exception = assertThrows(CustomException.class, () -> orderService.createOrder(request, phoneNumber));
//        assertEquals(ErrorMsg.PRODUCT_NOT_FOUND, exception.getErrorMsg());
//    }
//
//}