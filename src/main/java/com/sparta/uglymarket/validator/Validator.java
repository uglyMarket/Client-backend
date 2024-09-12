package com.sparta.uglymarket.validator;

import org.springframework.stereotype.Component;


@Component
public interface Validator {

    void validate(Long orderUserId, Long userId);

    void validateDeleteReview(Long reviewUserId, Long userId);

}
