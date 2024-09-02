package com.sparta.uglymarket.util;

import jakarta.servlet.http.HttpServletRequest;

public interface TokenService {

    String getPhoneNumberFromRequest(HttpServletRequest request);

}
