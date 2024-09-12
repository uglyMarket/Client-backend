package com.sparta.uglymarket.user.service;

import com.sparta.uglymarket.user.domain.UserDomain;
import com.sparta.uglymarket.user.domain.UserDomainService;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.mapper.UserMapper;
import com.sparta.uglymarket.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserJoinService {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final PhoneNumberValidatorService phoneNumberValidatorService;

    public UserJoinService(UserRepository userRepository, UserDomainService userDomainService, PhoneNumberValidatorService phoneNumberValidatorService) {
        this.userRepository = userRepository;
        this.userDomainService = userDomainService;
        this.phoneNumberValidatorService = phoneNumberValidatorService;
    }

    //회원가입 메서드
    public UserDomain joinUser(UserDomain userDomain) {

        // 전화번호 중복 확인
        phoneNumberValidatorService.validatePhoneNumber(userDomain.getPhoneNumber());

        // 비밀번호 인코딩 (도메인 서비스에서 처리)
        userDomainService.encodePassword(userDomain);

        // 엔티티 변환 후 저장
        User savedUser = userRepository.save(UserMapper.toEntity(userDomain));

        // 엔티티 -> 도메인 변환 후 반환
        return UserMapper.fromEntity(savedUser);
    }
}
