package com.example.portfolio.controller.admin;

import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.ProfileResponse;
import com.example.portfolio.service.admin.AdminProfileService;
import com.example.portfolio.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminProfileService adminProfileService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("管理者プロフィール情報が正常に取得できること")
    void shouldGetAdminProfile() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        when(adminProfileService.getProfile(userId)).thenReturn(new ProfileResponse());

        mockMvc.perform(get("/api/admin/profile")
                .cookie(new Cookie("jwt", mockToken))
                .requestAttr("userId", userId))
                .andExpect(status().isOk());
    }
}