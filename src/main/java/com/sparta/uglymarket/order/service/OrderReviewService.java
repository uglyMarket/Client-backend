package com.sparta.uglymarket.order.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.util.UserFinder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderReviewService {

    private final OrderRepository orderRepository;
    private final UserFinder userFinder;

    public OrderReviewService(OrderRepository orderRepository, UserFinder userFinder) {
        this.orderRepository = orderRepository;
        this.userFinder = userFinder;
    }


    // 후기를 작성할 수 있는 주문 목록 조회 메서드
    public List<PendingReviewOrderResponse> getPendingReviewOrdersByUserId(String phoneNumber) {

        // 유저 찾기
        User user = userFinder.findUserByPhoneNumber(phoneNumber);

        // 후기가 작성되지 않은 주문 전체 조회
        List<Orders> orders = orderRepository.findAllByUserIdAndReviewedFalse(user.getId());

        // DTO 리스트 생성 및 반환
        List<PendingReviewOrderResponse> responseList = new ArrayList<>();
        for (Orders order : orders) {
            responseList.add(new PendingReviewOrderResponse(order));
        }
        return responseList;
    }

    // 후기가 작성된 주문 목록 조회 메서드
    public List<WrittenReviewOrderResponse> getWrittenReviewOrdersByUserId(String phoneNumber) {

        // 유저 찾기
        User user = userFinder.findUserByPhoneNumber(phoneNumber);

        // 후기가 작성된 주문 전체 조회
        List<Orders> orders = orderRepository.findAllByUserIdAndReviewedTrue(user.getId());

        // DTO 리스트 생성 및 반환
        List<WrittenReviewOrderResponse> responseList = new ArrayList<>();
        for (Orders order : orders) {
            responseList.add(new WrittenReviewOrderResponse(order));
        }
        return responseList;
    }
}
