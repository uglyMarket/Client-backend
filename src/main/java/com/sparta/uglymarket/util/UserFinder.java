package com.sparta.uglymarket.util;

import com.sparta.uglymarket.user.entity.User;

public interface UserFinder {

    User findUserByPhoneNumber(String phoneNumber);


}
