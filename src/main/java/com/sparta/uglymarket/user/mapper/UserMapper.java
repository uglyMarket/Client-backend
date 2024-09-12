package com.sparta.uglymarket.user.mapper;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.user.domain.UserDomain;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;

import java.util.ArrayList;

public class UserMapper {


    // 회원가입 DTO -> Domain 변환
    public static UserDomain fromJoinReq(JoinRequest req) {
        return new UserDomain(
                null, // ID는 등록 시 자동 생성되므로 null 처리
                req.getNickname(),
                req.getPassword(),
                req.getPhoneNumber(),
                req.getProfileImageUrl(),
                Role.valueOf(req.getRole()),
                new ArrayList<>(),
                null
        );
    }


    // Domain -> JoinResponse 변환
    public static JoinResponse toJoinRes(UserDomain domain) {
        return new JoinResponse(
                domain.getId(),
                domain.getNickname(),
                domain.getPhoneNumber(),
                domain.getProfileImageUrl(),
                domain.getRole().name()
        );
    }


    // Entity -> Domain 변환
    public static UserDomain fromEntity(User entity) {
        return new UserDomain(
                entity.getId(),
                entity.getNickname(),
                entity.getPassword(),
                entity.getPhoneNumber(),
                entity.getProfileImageUrl(),
                entity.getRole(),
                new ArrayList<>(),
                null // 토큰 값은 로그인 후 생성
        );
    }


    // Domain -> Entity 변환
    public static User toEntity(UserDomain domain) {
        return new User(
                domain.getId(),
                domain.getNickname(),
                domain.getPassword(),
                domain.getPhoneNumber(),
                domain.getProfileImageUrl(),
                domain.getRole(),
                new ArrayList<>()
        );
    }


    // 로그인 DTO -> Domain 변환
    public static UserDomain fromLoginReq(LoginRequest req) {
        return new UserDomain(
                null,
                null,
                req.getPassword(),
                req.getPhoneNumber(),
                null,
                null,
                null,
                null
        );
    }

    // Domain -> LoginResponse 변환
    public static LoginResponse toLoginRes(UserDomain domain) {
        return new LoginResponse(domain.getToken()); // 생성된 토큰 반환
    }
}
