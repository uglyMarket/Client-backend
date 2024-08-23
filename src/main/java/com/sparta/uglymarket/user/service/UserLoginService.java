package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.JwtUtil;
import com.sparta.uglymarket.util.PasswordEncoderUtil;
import com.sparta.uglymarket.util.UserFinder;
import org.springframework.stereotype.Service;


@Service
public class UserLoginService {

    private final UserFinder userFinder;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final JwtUtil jwtUtil;

    public UserLoginService(UserFinder userFinder, PasswordEncoderUtil passwordEncoderUtil, JwtUtil jwtUtil) {
        this.userFinder = userFinder;
        this.passwordEncoderUtil = passwordEncoderUtil;
        this.jwtUtil = jwtUtil;
    }


    //로그인 메서드
    public LoginResponse loginUser(LoginRequest request) {
        //전화번호(아이디) 확인
        User user = userFinder.findUserByPhoneNumber(request.getPhoneNumber());

        //비밀번호 확인
        passwordEncoderUtil.validatePassword(request.getPassword(), user.getPassword());

        //토큰 생성하기
        String token = jwtUtil.generateToken(user.getPhoneNumber()); //핸드폰 번호를 주제로 토큰생성

        //DTO에 담아 토큰 반환하기
        return new LoginResponse(token);
    }

}
