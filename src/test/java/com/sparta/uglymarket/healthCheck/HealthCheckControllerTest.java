package com.sparta.uglymarket.healthCheck;

import com.sparta.uglymarket.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.TreeMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthCheckController.class)
@TestPropertySource(properties = {
        "server.env=local",
        "server.port=8080",
        "server.serveraddress=127.0.0.1",
        "servername=testServer"
})
@AutoConfigureMockMvc(addFilters = false)  // 시큐리티 필터 비활성화
class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("헬스 체크 API 테스트")
    void healthCheck_Success() throws Exception {
        // expected 데이터
        Map<String, String> expectedResponse = new TreeMap<>();
        expectedResponse.put("serverName", "testServer");
        expectedResponse.put("serverAddress", "127.0.0.1");
        expectedResponse.put("serverPort", "8080");
        expectedResponse.put("env", "local");

        // when & then
        mockMvc.perform(get("/hc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serverName").value("testServer"))
                .andExpect(jsonPath("$.serverAddress").value("127.0.0.1"))
                .andExpect(jsonPath("$.serverPort").value("8080"))
                .andExpect(jsonPath("$.env").value("local"));
    }

    @Test
    @DisplayName("env 정보 반환 테스트")
    void getEnv_Success() throws Exception {
        // when & then
        mockMvc.perform(get("/env"))
                .andExpect(status().isOk())
                .andExpect(content().string("local"));
    }
}
