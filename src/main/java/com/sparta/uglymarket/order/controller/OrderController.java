package com.sparta.uglymarket.order.controller;


import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.service.OrderService;
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


    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        OrderResponse response = orderService.createOrder(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }

    // 사용자가 후기를 작성할 수 있는 주문 목록 조회
    @GetMapping("/user/{userId}/pending-reviews")
    public ResponseEntity<List<PendingReviewOrderResponse>> getPendingReviewOrdersByUserId(@PathVariable Long userId) {
        List<PendingReviewOrderResponse> orders = orderService.getPendingReviewOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // 사용자가 후기를 작성한 주문 목록 조회
    @GetMapping("/user/{userId}/written-reviews")
    public ResponseEntity<List<WrittenReviewOrderResponse>> getWrittenReviewOrdersByUserId(@PathVariable Long userId) {
        List<WrittenReviewOrderResponse> orders = orderService.getWrittenReviewOrdersByUserId(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
