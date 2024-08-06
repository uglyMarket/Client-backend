package com.sparta.uglymarket.review.service.impl;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.review.dto.ReviewCreateRequest;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.review.service.validator.ReviewValidator;
import com.sparta.uglymarket.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DefaultReviewValidator implements ReviewValidator {

    //주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증
    @Override
    public void validate(Orders orders, User user) {
        if (!orders.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }

    //리뷰의 유저 아이디와, 토큰에서 가져온 유저 아이디가 같은지 검증
    @Override
    public void validateDeleteReview(User user, Review review) {
        if (!review.getOrders().getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }


}
