package com.sparta.uglymarket.order.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.mapper.OrdersMapper;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FinderService finderService;
    private final ProductRepository productRepository;


    public OrderService(OrderRepository orderRepository, FinderService finderService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.finderService = finderService;
        this.productRepository = productRepository;
    }


    //주문 생성
    public OrdersDomain createOrder(OrdersDomain ordersDomain, String phoneNumber) {

        //주문한 유저 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);

        //주문상품 찾기
        Product product = productRepository.findById(ordersDomain.getProductId())
                .orElseThrow(() -> new CustomException(ErrorMsg.PRODUCT_NOT_FOUND));


        //비즈니스 로직 사용 (주문 상태가 null이 아닌지 확인)
        if (!ordersDomain.isValidOrderStatus()) {
            throw new CustomException(ErrorMsg.ORDER_NOT_FOUND);
        }

        // 도메인 -> 엔티티 변환
        Orders ordersEntity = OrdersMapper.toEntity(ordersDomain, user, product);

        // 주문 저장
        Orders savedOrders = orderRepository.save(ordersEntity);

        // 엔티티 -> 도메인 변환 후 반환
        return OrdersMapper.toDomain(savedOrders);

    }
}
