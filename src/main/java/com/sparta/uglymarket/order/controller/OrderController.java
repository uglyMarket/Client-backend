package com.sparta.uglymarket.order.controller;


import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.mapper.OrdersMapper;
import com.sparta.uglymarket.order.service.OrderReviewService;
import com.sparta.uglymarket.order.service.OrderService;
import com.sparta.uglymarket.util.FinderService;
import com.sparta.uglymarket.util.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final TokenService tokenService;
    private final OrderReviewService orderReviewService;
    private final FinderService finderService;


    public OrderController(OrderService orderService, TokenService tokenService, OrderReviewService orderReviewService, FinderService finderService) {
        this.orderService = orderService;
        this.tokenService = tokenService;
        this.orderReviewService = orderReviewService;
        this.finderService = finderService;
    }


    //주문 생성
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request, HttpServletRequest httpRequest) {
        // 헤더에서 폰 번호 추출
        String phoneNumber = tokenService.getPhoneNumberFromRequest(httpRequest);

        // DTO -> Domain 변환 (OrderMapper 사용)
        OrdersDomain ordersDomain = OrdersMapper.DtoToDomain(request);

        // 서비스에 도메인 객체 전달
        OrdersDomain createdOrder = orderService.createOrder(ordersDomain, phoneNumber);

        // Domain -> DTO 변환 (OrderMapper 사용)
        OrderResponse response = OrdersMapper.DomainToDto(createdOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 사용자가 후기를 작성할 수 있는 주문 목록 조회
    @GetMapping("/user/pending-reviews")
    public ResponseEntity<List<PendingReviewOrderResponse>> getPendingReviewOrdersByUser(HttpServletRequest httpRequest) {
        // 헤더에서 폰 번호 추출
        String phoneNumber = tokenService.getPhoneNumberFromRequest(httpRequest);

        // 서비스에 폰 번호 전달하여 도메인 리스트 반환
        List<OrdersDomain> pendingOrders = orderReviewService.getPendingReviewOrdersByUserId(phoneNumber);

        // Domain 리스트 -> DTO 리스트 변환 (OrderMapper 사용)
        List<PendingReviewOrderResponse> responseList = OrdersMapper.toPendingReviewDtoList(pendingOrders);

        // 응답 반환
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    // 사용자가 후기를 작성한 주문 목록 조회
    @GetMapping("/user/written-reviews")
    public ResponseEntity<List<WrittenReviewOrderResponse>> getWrittenReviewOrdersByUser(HttpServletRequest httpRequest) {
        // 헤더에서 폰 번호 추출
        String phoneNumber = tokenService.getPhoneNumberFromRequest(httpRequest);

        // 서비스에 폰 번호 전달하여 도메인 리스트 반환
        List<OrdersDomain> writtenOrders = orderReviewService.getWrittenReviewOrdersByUserId(phoneNumber);

        // Domain 리스트 -> DTO 리스트 변환 (OrderMapper 사용)
        List<WrittenReviewOrderResponse> responseList = OrdersMapper.toWrittenReviewDtoList(writtenOrders);

        // 응답 반환
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

}
