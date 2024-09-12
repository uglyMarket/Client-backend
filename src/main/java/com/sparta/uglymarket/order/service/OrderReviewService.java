package com.sparta.uglymarket.order.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.mapper.OrdersMapper;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderReviewService {

    private final OrderRepository orderRepository;
    private final FinderService finderService;

    public OrderReviewService(OrderRepository orderRepository, FinderService finderService) {
        this.orderRepository = orderRepository;
        this.finderService = finderService;
    }


    // 후기를 작성할 수 있는 주문 목록 조회 메서드
    public List<OrdersDomain> getPendingReviewOrdersByUserId(String phoneNumber) {
        // 유저 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 후기가 작성되지 않은 주문 전체 조회 (엔티티 -> 도메인 변환)
        List<Orders> ordersList = orderRepository.findAllByUserIdAndReviewedFalse(user.getId());

        //엔티티 -> 도메인 변환
        List<OrdersDomain> ordersDomainList = OrdersMapper.toDomainList(ordersList);

        return ordersDomainList;
    }

    // 후기가 작성된 주문 목록 조회 메서드
    public List<OrdersDomain> getWrittenReviewOrdersByUserId(String phoneNumber) {
        // 유저 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        // 후기가 작성된 주문 전체 조회 (엔티티 -> 도메인 변환)
        List<Orders> ordersList = orderRepository.findAllByUserIdAndReviewedTrue(user.getId());

        //엔티티 -> 도메인 변환
        List<OrdersDomain> ordersDomainList = OrdersMapper.toDomainList(ordersList);

        return ordersDomainList;
    }

    //주문 상태를 리뷰 작성상태로 변경
    @Transactional
    public void updateOrderStatusToReviewed(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ORDER_NOT_FOUND));

        //엔티티 -> 도메인
        OrdersDomain ordersDomain = OrdersMapper.toDomain(orders);

        //비즈니스 로직 작동
        ordersDomain.markAsReviewed();

        // 업데이트된 주문 상태 저장 (엔티티 -> 도메인)
        orderRepository.save(OrdersMapper.toEntity(ordersDomain, orders.getUser(), orders.getProduct()));

    }

    //주문상태를 리뷰 취소상태 변경
    @Transactional
    public void updateOrderStatusToUnreviewed(Long orderId) {
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorMsg.ORDER_NOT_FOUND));

        //엔티티 -> 도메인
        OrdersDomain ordersDomain = OrdersMapper.toDomain(orders);

        //비즈니스 로직 작동
        ordersDomain.unmarkAsReviewed();

        // 업데이트된 주문 상태 저장 (엔티티 -> 도메인)
        orderRepository.save(OrdersMapper.toEntity(ordersDomain, orders.getUser(), orders.getProduct()));

    }
}
