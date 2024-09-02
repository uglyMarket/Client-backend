package com.sparta.uglymarket.util;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.product.entity.Product;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.user.entity.User;

public interface FinderService {

    User findUserByPhoneNumber(String phoneNumber);
    Orders findOrderById(Long orderId);
    Product findProductById(Long productId);
    Review findReviewById(Long reviewId);

}
