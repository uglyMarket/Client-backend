package com.sparta.uglymarket.util;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderUtil {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderUtil() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //패스워드 인코딩
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    //입력된 비번과 인코딩된 비번이 같은지 검사
    public void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
        }
    }
}
