package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // BCryptPasswordEncoder를 사용하여 비밀번호를 해시합니다.
        this.jwtUtil = jwtUtil;
    }


    //로그인 메서드
    public LoginResponse loginUser(LoginRequest request) {
        //전화번호(아이디) 확인
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow(() -> new CustomException(ErrorMsg.PHONE_NUMBER_INVALID));

        //비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
        }

        //토큰 생성하기
        String token = jwtUtil.generateToken(user.getPhoneNumber()); //핸드폰 번호를 주제로 토큰생성

        //DTO에 담아 토큰 반환하기
        return new LoginResponse(token);
    }

}
