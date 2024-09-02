package com.sparta.uglymarket.order.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.repository.OrderRepository;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.product.repository.ProductRepository;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import org.springframework.stereotype.Service;

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
    public OrderResponse createOrder(OrderRequest request, String phoneNumber) {

        //주문한 유저 찾기
        User user = finderService.findUserByPhoneNumber(phoneNumber);


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

}
