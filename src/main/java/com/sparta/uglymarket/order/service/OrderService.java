package com.sparta.uglymarket.order.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final JwtUtil jwtUtil;


    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, JwtUtil jwtUtil) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
    }


    //주문 생성
    public OrderResponse createOrder(OrderRequest request, String phoneNumber) {

        //주문한 유저 찾기
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.USER_NOT_FOUND));

        //주문상품 찾기
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));

        //주문 객체 생성
        Orders orders = new Orders(user, product, request);

        //주문 저장
        Orders savedOrders = orderRepository.save(orders);

        //주문 객체 -> DTO로 생성해서 반환
        return new OrderResponse(savedOrders);

    }

    // 사용자가 후기를 작성할 수 있는 주문 목록 조회
    public List<PendingReviewOrderResponse> getPendingReviewOrdersByUserId(String phoneNumber) {

        //유저 찾기
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.USER_NOT_FOUND));

        //후기가 작성되지 않은 주문을 전체조회
        List<Orders> orders = orderRepository.findAllByUserIdAndReviewedFalse(user.getId());

        //반환해줄 DTO 리스트 타입 선언
        List<PendingReviewOrderResponse> responseList = new ArrayList<>();
        for (Orders order : orders) {
            responseList.add(new PendingReviewOrderResponse(order));
        }
        return responseList;
    }

    // 사용자가 후기를 작성한 주문 목록 조회
    public List<WrittenReviewOrderResponse> getWrittenReviewOrdersByUserId(String phoneNumber) {

        //유저 찾기
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.USER_NOT_FOUND));

        //후기가 작성된 주문 전체조회
        List<Orders> orders = orderRepository.findAllByUserIdAndReviewedTrue(user.getId());

        //반환해줄 DTO 리스트 타입 선언
        List<WrittenReviewOrderResponse> responseList = new ArrayList<>();
        for (Orders order : orders) {
            responseList.add(new WrittenReviewOrderResponse(order));
        }
        return responseList;
    }

}
