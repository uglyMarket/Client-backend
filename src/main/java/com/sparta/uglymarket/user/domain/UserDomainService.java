package com.sparta.uglymarket.user.domain;


import com.sparta.uglymarket.util.PasswordEncoderUtil;
import org.springframework.stereotype.Service;

@Service
public class UserDomainService {

    private final PasswordEncoderUtil passwordEncoderUtil;

    public UserDomainService(PasswordEncoderUtil passwordEncoderUtil) {
        this.passwordEncoderUtil = passwordEncoderUtil;
    }

    // 비밀번호 인코딩
    public void encodePassword(UserDomain userDomain) {
        String encodedPassword = passwordEncoderUtil.encodePassword(userDomain.getPassword());
        userDomain.updatePassword(encodedPassword);
    }

    // 비밀번호 검증
    public boolean validatePassword(UserDomain userDomain, String rawPassword) {
        return passwordEncoderUtil.validatePassword(userDomain.getPassword(), rawPassword);
    }
}
