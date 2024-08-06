package com.sparta.uglymarket.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

public interface TokenService {

    String getPhoneNumberFromRequest(HttpServletRequest request);

}
