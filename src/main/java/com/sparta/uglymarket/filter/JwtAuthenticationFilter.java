package com.sparta.uglymarket.filter;

import com.sparta.uglymarket.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        // Authorization 헤더에서 토큰을 추출
        String token = getJwtFromRequest(request);

        // 토큰이 유효한지 검사하고 유효하다면 사용자 정보 설정
        if (token != null && jwtUtil.validateToken(token)) {//토큰이 null이 아니고, 유효하다면
            Claims claims = jwtUtil.getAllClaimsFromToken(token); // 토큰에서 클레임 추출
            String phoneNumber = claims.getSubject(); // 클레임에서 phoneNumber 추출
            request.setAttribute("phoneNumber", phoneNumber); // request에 phoneNumber 설정
        } else {
            // 토큰이 유효하지 않으면 요청을 거부
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
            return;
        }

        // 다음 필터로 요청과 응답을 전달
        filterChain.doFilter(request, response);
    }


    //헤더에서 토큰을 추출하는 메서드
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) { //토큰이 있다면(Bearer시작이면 JWT토큰)
            return bearerToken.substring(7); //7번째에서 자르기
        }
        return null; //토큰 없으면 null 반환
    }
}

