package com.sparta.uglymarket.user.controller;

import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
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
        JoinResponse response = userJoinService.JoinUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //로그인 컨트롤러
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userLoginService.loginUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
