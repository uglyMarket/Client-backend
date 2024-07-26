package com.sparta.uglymarket.order.controller;


import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    //주문 생성
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request, HttpServletRequest httpRequest) {
        String phoneNumber = (String) httpRequest.getAttribute("phoneNumber");//헤더에서 폰번호 찾기

        OrderResponse response = orderService.createOrder(request, phoneNumber);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 사용자가 후기를 작성할 수 있는 주문 목록 조회
    @GetMapping("/user/pending-reviews")
    public ResponseEntity<List<PendingReviewOrderResponse>> getPendingReviewOrdersByUser(HttpServletRequest httpRequest) {
        String phoneNumber = (String) httpRequest.getAttribute("phoneNumber");//헤더에서 폰번호 찾기

        List<PendingReviewOrderResponse> orders = orderService.getPendingReviewOrdersByUserId(phoneNumber);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 사용자가 후기를 작성한 주문 목록 조회
    @GetMapping("/user/written-reviews")
    public ResponseEntity<List<WrittenReviewOrderResponse>> getWrittenReviewOrdersByUser(HttpServletRequest httpRequest) {
        String phoneNumber = (String) httpRequest.getAttribute("phoneNumber");//헤더에서 폰번호 찾기

        List<WrittenReviewOrderResponse> orders = orderService.getWrittenReviewOrdersByUserId(phoneNumber);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
