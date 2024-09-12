package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.domain.UserDomain;
import com.sparta.uglymarket.user.domain.UserDomainService;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.util.FinderService;
import com.sparta.uglymarket.util.JwtUtil;
import org.springframework.stereotype.Service;


@Service
public class UserLoginService {

    private final FinderService finderService;
    private final UserDomainService userDomainService;
    private final JwtUtil jwtUtil;

    public UserLoginService(FinderService finderService, UserDomainService userDomainService, JwtUtil jwtUtil) {
        this.finderService = finderService;
        this.userDomainService = userDomainService;
        this.jwtUtil = jwtUtil;
    }

    //로그인 메서드
    public UserDomain loginUser(UserDomain userDomain) {

        //전화번호(아이디)로 사용자 조회
        User user = finderService.findUserByPhoneNumber(userDomain.getPhoneNumber());

        // 비밀번호 확인 (도메인 서비스에서 처리)
        if (!userDomainService.validatePassword(userDomain, user.getPassword())) {
            throw new CustomException(ErrorMsg.PASSWORD_INCORRECT);
        }

        //토큰 생성하기
        String token = jwtUtil.generateToken(userDomain.getPhoneNumber());

        userDomain.token(token);


        // 토큰을 반환할 응답 객체에서 처리
        return userDomain;
    }

}
