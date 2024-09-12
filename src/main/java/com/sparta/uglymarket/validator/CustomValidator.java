package com.sparta.uglymarket.validator;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import org.springframework.stereotype.Component;

@Component
public class CustomValidator implements Validator {

    //주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증
    @Override
    public void validate(Long orderUserId, Long userId) {
        if (!orderUserId.equals(userId)) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }

    //리뷰의 유저 아이디와, 토큰에서 가져온 유저 아이디가 같은지 검증
    @Override
    public void validateDeleteReview(Long reviewUserId, Long userId) {
        if (!reviewUserId.equals(userId)) {
            throw new CustomException(ErrorMsg.UNAUTHORIZED_MEMBER);
        }
    }


}
