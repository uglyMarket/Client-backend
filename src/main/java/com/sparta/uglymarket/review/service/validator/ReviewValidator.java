package com.sparta.uglymarket.review.service.validator;

import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.user.entity.User;
import org.springframework.stereotype.Component;


@Component
public interface ReviewValidator {

    void validate(Orders orders, User user);

    void validateDeleteReview(User user, Review review);

}
