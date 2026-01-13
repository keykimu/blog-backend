package com.example.portfolio.controller.admin;

import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.ProfileItemsResponse;
import com.example.portfolio.service.admin.AdminProfileItemsService;
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

@WebMvcTest(ProfileItemsController.class)
class ProfileItemsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminProfileItemsService adminProfileItemsService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("管理者プロフィール項目一覧が取得できること")
    void shouldGetAllProfileItems() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        when(adminProfileItemsService.getAllByUserId(userId)).thenReturn(new ProfileItemsResponse());

        mockMvc.perform(get("/api/admin/profile-items")
                .cookie(new Cookie("jwt", mockToken))
                .requestAttr("userId", userId))
                .andExpect(status().isOk());
    }
}