package com.sparta.uglymarket.config;

import com.sparta.uglymarket.filter.JwtAuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//필터 등록하기
@Configuration
public class WebConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    //필터 등록 메서드
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegistration() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtAuthenticationFilter); // 필터 설정
        registrationBean.addUrlPatterns("/api/*"); // 보호할 URL 패턴 설정
        return registrationBean; // 필터 등록 빈 반환
    }


}
