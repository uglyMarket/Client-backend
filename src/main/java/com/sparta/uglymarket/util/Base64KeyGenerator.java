package com.sparta.uglymarket.util;

import java.util.Base64;

//base64로 되어있는 시크릿키 생성기
public class Base64KeyGenerator {
    public static void main(String[] args) {

        byte[] key = new byte[64]; // 64바이트 길이의 키 생성 (512비트)
        new java.security.SecureRandom().nextBytes(key); //랜덤 시크릿키 생성

        // Base64 인코딩
        String base64Key = Base64.getEncoder().encodeToString(key);

        // 출력
        System.out.println("Base64 encoded key: " + base64Key);
    }
}
