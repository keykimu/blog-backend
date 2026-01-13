package com.example.portfolio.controller.admin;

import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.SkillsResponse;
import com.example.portfolio.service.admin.AdminSkillsService;
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

@WebMvcTest(SkillsController.class)
class SkillsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminSkillsService adminSkillsService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("スキル一覧が正常に取得できること")
    void shouldGetAllSkills() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        when(adminSkillsService.getAllByUserId(userId)).thenReturn(new SkillsResponse());

        mockMvc.perform(get("/api/admin/skills")
                .cookie(new Cookie("jwt", mockToken))
                .requestAttr("userId", userId))
                .andExpect(status().isOk());
    }
}