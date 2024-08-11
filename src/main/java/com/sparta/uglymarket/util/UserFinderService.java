package com.sparta.uglymarket.util;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserFinderService {

    private final UserRepository userRepository;

    public UserFinderService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_INVALID));
    }
}
