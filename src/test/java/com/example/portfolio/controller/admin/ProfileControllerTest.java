package com.example.portfolio.controller.admin;

import com.example.portfolio.exception.NotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    @DisplayName("管理画面でプロフィールが存在しない場合に404を返すこと")
    void shouldReturn404WhenAdminProfileNotFound() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        when(adminProfileService.getProfile(userId))
                .thenThrow(new NotFoundException("プロフィールが見つかりません"));

        mockMvc.perform(get("/api/admin/profile")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること")
    void shouldReturn401WhenNoCookieOngetProfile() throws Exception {
        mockMvc.perform(get("/api/admin/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("名前が空欄の場合にバリデーションエラー(400)を返すこと")
    void shouldReturn400WhenNameIsEmpty() throws Exception {
        Long userId = 1L;
        Long profileId = 100L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        // 不正なリクエストデータ
        ProfileUpdateRequest invalidRequest = createValidRequest();
        invalidRequest.setName(""); // ここがエラーになるはず

        mockMvc.perform(put("/api/admin/profile/{id}", profileId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.error").value(containsString("名前は必須です")));
    }

    @Test
    @DisplayName("更新対象のプロフィールが存在しない場合に404を返すこと")
    void shouldReturn404WhenProfileToUpdateDoesNotExist() throws Exception {
        Long userId = 1L;
        Long targetId = 999L; // 存在しないID
        String mockToken = "valid-token";

        // 認証は通るように設定
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        // サービスが404例外を投げるように設定
        when(adminProfileService.updateProfile(eq(targetId), any(), eq(userId)))
                .thenThrow(new NotFoundException("対象のプロフィールが存在しません"));

        mockMvc.perform(put("/api/admin/profile/{id}", targetId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createValidRequest())))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること")
    void shouldReturn401WhenNoCookie() throws Exception {
        // cookie() をあえて指定せずにリクエスト
        mockMvc.perform(put("/api/admin/profile/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createValidRequest())))
                .andExpect(status().isUnauthorized());
    }

    private ProfileUpdateRequest createValidRequest() {
        ProfileUpdateRequest request = new ProfileUpdateRequest();
        request.setName("テスト太郎");
        request.setNickname("タロウ");
        request.setNameEn("Taro Test");
        request.setIntro("こんにちは");
        request.setBio("自己紹介文です");
        request.setImageName("icon.png");
        request.setMail("test@example.com");
        request.setGithub("https://github.com/test");
        return request;
    }
}