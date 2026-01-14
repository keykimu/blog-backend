package com.example.portfolio.controller.admin;

import com.example.portfolio.request.ProfileUpdateRequest;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.ProfileResponse;
import com.example.portfolio.service.admin.AdminProfileService;
import com.example.portfolio.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(ProfileController.class)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminProfileService adminProfileService;
    @MockitoBean
    private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("名前が空欄の場合にバリデーションエラー(400)を返すこと")
    void shouldReturn400WhenNameIsEmpty() throws Exception {
        // 準備（トークンやユーザーIDなどのモック設定）
        Long userId = 1L;
        Long profileId = 100L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        // 不正なリクエストデータ
        ProfileUpdateRequest invalidRequest = new ProfileUpdateRequest();
        invalidRequest.setName(""); // ここがエラーになるはず
        invalidRequest.setNickname("ニックネーム");
        invalidRequest.setNameEn("NameEn");
        invalidRequest.setIntro("一言");
        invalidRequest.setBio("自己紹介");
        invalidRequest.setImageName("icon.png");
        invalidRequest.setMail("test@example.com");
        invalidRequest.setGithub("https://github.com/...");

        mockMvc.perform(put("/api/admin/profile/{id}", profileId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.error").value(containsString("名前は必須です")));
    }
}