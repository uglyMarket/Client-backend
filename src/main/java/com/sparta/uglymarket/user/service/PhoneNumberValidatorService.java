package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberValidatorService implements PhoneNumberValidator {

    private final UserRepository userRepository;

    public PhoneNumberValidatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validatePhoneNumber(String phoneNumber) {
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }
    }


}
