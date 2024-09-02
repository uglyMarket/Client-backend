package com.sparta.uglymarket.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.uglymarket.enums.Role;
import com.sparta.uglymarket.user.dto.JoinRequest;
import com.sparta.uglymarket.user.dto.JoinResponse;
import com.sparta.uglymarket.user.dto.LoginRequest;
import com.sparta.uglymarket.user.dto.LoginResponse;
import com.sparta.uglymarket.user.entity.User;
import com.sparta.uglymarket.user.service.UserJoinService;
import com.sparta.uglymarket.user.service.UserLoginService;
import com.sparta.uglymarket.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserJoinService userJoinService;

    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 컨트롤러 테스트")
    void joinUser_Success() throws Exception {

        // given
        JoinRequest joinRequest = mock(JoinRequest.class);
        given(joinRequest.getNickname()).willReturn("testuser");
        given(joinRequest.getPassword()).willReturn("password");
        given(joinRequest.getPhoneNumber()).willReturn("123456789");
        given(joinRequest.getProfileImageUrl()).willReturn("imageUrl");
        given(joinRequest.getRole()).willReturn("USER");

        User user = mock(User.class);
        given(user.getNickname()).willReturn("testuser");
        given(user.getPhoneNumber()).willReturn("123456789");
        given(user.getProfileImageUrl()).willReturn("imageUrl");
        given(user.getRole()).willReturn(Role.USER);

        JoinResponse joinResponse = new JoinResponse(user);

        // UserJoinService Mock 설정
        given(userJoinService.JoinUser(any(JoinRequest.class))).willReturn(joinResponse);

        // when & then
        mockMvc.perform(post("/api/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(joinResponse)));
    }

    @Test
    @DisplayName("로그인 컨트롤러 테스트")
    void loginUser_Success() throws Exception {
        // given
        LoginRequest loginRequest = mock(LoginRequest.class);
        given(loginRequest.getPhoneNumber()).willReturn("123456789");
        given(loginRequest.getPassword()).willReturn("password");

        LoginResponse loginResponse = new LoginResponse("mockedJwtToken");

        // UserLoginService Mock 설정
        given(userLoginService.loginUser(any(LoginRequest.class))).willReturn(loginResponse);

        // when & then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(loginResponse)));
    }
}