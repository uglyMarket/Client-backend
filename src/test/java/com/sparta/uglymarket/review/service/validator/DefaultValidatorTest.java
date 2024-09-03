package com.sparta.uglymarket.review.service.validator;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.order.entity.Orders;
import com.sparta.uglymarket.review.entity.Review;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.validator.DefaultValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultValidatorTest {

    private DefaultValidator reviewValidator;

    @BeforeEach
    void setUp() {
        reviewValidator = new DefaultValidator();
    }

    //주문의 유저아이디와, 토큰에서 가져온 유저의 아이디가 같은지 검증
    @Test
    @DisplayName("validate 메서드가 정상적으로 작동하는지 테스트")
    void validate_NoExceptionThrown() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        Orders orders = mock(Orders.class);
        given(orders.getUser()).willReturn(user);

        // when & then
        assertDoesNotThrow(() -> reviewValidator.validate(orders, user));
    }

    @Test
    @DisplayName("validate 메서드가 다른 사용자를 감지할 때 예외를 던지는지 테스트")
    void validate_ThrowsException() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        User differentUser = mock(User.class);
        given(differentUser.getId()).willReturn(2L);

        Orders orders = mock(Orders.class);
        given(orders.getUser()).willReturn(differentUser);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> reviewValidator.validate(orders, user));
        assertEquals(ErrorMsg.UNAUTHORIZED_MEMBER, exception.getErrorMsg());
    }

    //리뷰의 유저 아이디와, 토큰에서 가져온 유저 아이디가 같은지 검증
    @Test
    @DisplayName("validateDeleteReview 메서드가 정상적으로 작동하는지 테스트")
    void validateDeleteReview_NoExceptionThrown() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        Orders orders = mock(Orders.class);
        given(orders.getUser()).willReturn(user);

        Review review = mock(Review.class);
        given(review.getOrders()).willReturn(orders);

        // when & then
        assertDoesNotThrow(() -> reviewValidator.validateDeleteReview(user, review));
    }

    @Test
    @DisplayName("validateDeleteReview 메서드가 다른 사용자를 감지할 때 예외를 던지는지 테스트")
    void validateDeleteReview_ThrowsException() {
        // given
        User user = mock(User.class);
        given(user.getId()).willReturn(1L);

        User differentUser = mock(User.class);
        given(differentUser.getId()).willReturn(2L);

        Orders orders = mock(Orders.class);
        given(orders.getUser()).willReturn(differentUser);

        Review review = mock(Review.class);
        given(review.getOrders()).willReturn(orders);

        // when & then
        CustomException exception = assertThrows(CustomException.class, () -> reviewValidator.validateDeleteReview(user, review));
        assertEquals(ErrorMsg.UNAUTHORIZED_MEMBER, exception.getErrorMsg());
    }
}