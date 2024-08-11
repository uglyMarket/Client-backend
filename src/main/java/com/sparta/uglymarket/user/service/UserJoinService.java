package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.exception.CustomException;
import com.sparta.uglymarket.exception.ErrorMsg;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.repository.UserRepository;
import com.sparta.uglymarket.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public UserJoinService(UserRepository userRepository, PasswordEncoderUtil passwordEncoderUtil) {
        this.userRepository = userRepository;
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

    //회원가입 메서드
    public JoinResponse JoinUser(JoinRequest request) {

        // 전화번호 중복인지 확인
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new CustomException(ErrorMsg.DUPLICATE_PHONE_NUMBER);
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoderUtil.encodePassword(request.getPassword());

        // Role 설정
        Role role = Role.valueOf(request.getRole());

        // User 엔티티를 생성하고 저장
        User user = new User(request, encodedPassword, role);
        User savedUser = userRepository.save(user);

        // UserResponseDTO로 변환하여 반환
        return new JoinResponse(savedUser);
    }
}
