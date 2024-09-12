package com.sparta.uglymarket.order.mapper;

import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.dto.OrderRequest;
import com.sparta.uglymarket.order.dto.OrderResponse;
import com.sparta.uglymarket.order.dto.PendingReviewOrderResponse;
import com.sparta.uglymarket.order.dto.WrittenReviewOrderResponse;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.user.entity.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdersMapper {


    // DTO -> Domain 변환
    public static OrdersDomain DtoToDomain(OrderRequest request) {
        return new OrdersDomain(
                null,
                null,
                request.getProductId(),
                request.getOrderStatus(),
                request.getQuantity(),
                LocalDateTime.now(),
                false
        );
    }


    // Domain -> DTO 변환
    public static OrderResponse DomainToDto(OrdersDomain ordersDomain) {
        return new OrderResponse(
                ordersDomain.getId(),
                ordersDomain.getUserId(),
                ordersDomain.getProductId(),
                ordersDomain.getOrderStatus(),
                ordersDomain.getQuantity(),
                ordersDomain.getOrderDate()
        );
    }

    // 엔티티 -> 도메인 변환
    public static OrdersDomain toDomain(Orders orders) {
        return new OrdersDomain(
                orders.getId(),
                orders.getUser().getId(),
                orders.getProduct().getId(),
                orders.getOrderStatus(),
                orders.getQuantity(),
                orders.getOrderDate(),
                orders.isReviewed()
        );
    }


    // 도메인 -> 엔티티 변환
    public static Orders toEntity(OrdersDomain ordersDomain, User user, Product product) {
        return new Orders(
                ordersDomain.getId(),
                user,
                product,
                ordersDomain.getOrderStatus(),
                ordersDomain.getQuantity(),
                ordersDomain.getOrderDate(),
                ordersDomain.isReviewed()
        );
    }


    // 엔티티 리스트 -> 도메인 리스트 변환
    public static List<OrdersDomain> toDomainList(List<Orders> ordersList) {

        List<OrdersDomain> ordersDomainList = new ArrayList<>();
        for (Orders orders : ordersList) {
            ordersDomainList.add(toDomain(orders));
        }
        return ordersDomainList;
    }


    // 도메인 리스트 -> PendingReviewOrderResponse DTO 리스트 변환
    public static List<PendingReviewOrderResponse> toPendingReviewDtoList(List<OrdersDomain> ordersDomainList) {

       List<PendingReviewOrderResponse> responseList = new ArrayList<>();
        for (OrdersDomain ordersDomain : ordersDomainList) {
            responseList.add(new PendingReviewOrderResponse(ordersDomain.getId(), ordersDomain.getProductId(), ordersDomain.getOrderStatus()));
        }
        return responseList;
    }

    // 도메인 리스트 -> WrittenReviewOrderResponse DTO 리스트 변환
    public static List<WrittenReviewOrderResponse> toWrittenReviewDtoList(List<OrdersDomain> ordersDomainList) {
        List<WrittenReviewOrderResponse> responseList = new ArrayList<>();
        for (OrdersDomain ordersDomain : ordersDomainList) {
            responseList.add(new WrittenReviewOrderResponse(ordersDomain.getId(), ordersDomain.getProductId(), ordersDomain.getOrderStatus()));
        }
        return responseList;
    }

}
