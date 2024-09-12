package com.sparta.uglymarket.user.controller;

import com.sparta.uglymarket.user.domain.UserDomain;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.mapper.UserMapper;
import com.sparta.uglymarket.user.service.UserJoinService;
import com.sparta.uglymarket.user.service.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserJoinService userJoinService;
    private final UserLoginService userLoginService;

    public UserController(UserLoginService userLoginService, UserJoinService userJoinService) {
        this.userLoginService = userLoginService;
        this.userJoinService = userJoinService;
    }


    //회원가입 컨트롤러
    @PostMapping("/join")
    public ResponseEntity<JoinResponse> join(@RequestBody JoinRequest request) {

        // DTO -> Domain 변환
        UserDomain userDomain = UserMapper.fromJoinReq(request);

        // 회원가입 처리
        UserDomain createdUser = userJoinService.joinUser(userDomain);

        // Domain -> DTO 변환 후 응답 반환
        JoinResponse response = UserMapper.toJoinRes(createdUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    //로그인 컨트롤러
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        // DTO -> Domain 변환
        UserDomain userDomain = UserMapper.fromLoginReq(request);

        // 로그인 처리
        UserDomain loggedInUser = userLoginService.loginUser(userDomain);

        // Domain -> DTO 변환 후 응답 반환
        LoginResponse response = UserMapper.toLoginRes(loggedInUser);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
