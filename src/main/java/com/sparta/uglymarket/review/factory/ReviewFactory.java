package com.sparta.uglymarket.review.factory;

import com.sparta.uglymarket.order.domain.OrdersDomain;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.order.mapper.OrdersMapper;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.domain.ReviewDomain;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.user.entity.User;

public class ReviewFactory {

    public static Review createReviewFromDomain(ReviewDomain reviewDomain, OrdersDomain ordersDomain, Product product, User user) {
        Orders ordersEntity = OrdersMapper.toEntity(ordersDomain, user, product); // 변환은 외부에서 처리

        return new Review(
                reviewDomain.getContent(),
                reviewDomain.getReviewImage(),
                ordersEntity, // 변환된 엔티티를 사용
                product,
                user
        );
    }

}
