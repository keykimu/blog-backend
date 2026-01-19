package com.example.portfolio.controller.admin;

import com.example.portfolio.request.HobbyCreateRequest;
import com.example.portfolio.request.ProfileItemsRequest;
import com.example.portfolio.request.wrap.HobbyListRequest;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.ProfileItemsResponse;
import com.example.portfolio.service.admin.AdminProfileItemsService;
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

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProfileItemsController.class)
class ProfileItemsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminProfileItemsService adminProfileItemsService;
    @MockitoBean private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("クッキーがない場合に401エラーになること（趣味・経歴）")
    void shouldReturn401WhenNoCookieOnGetProfileItems() throws Exception {
        mockMvc.perform(get("/api/admin/profile-items"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("登録データが不正な場合にバリデーションエラー(400)を返すこと")
    void shouldReturn400WhenSaveItemsDataIsInvalid() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        // 認証OKの設定
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        HobbyCreateRequest hobby = new HobbyCreateRequest();
        hobby.setName(""); // 空で登録できないことをテスト

        HobbyListRequest hobbyList = new HobbyListRequest();
        hobbyList.setHobbies(List.of(hobby));

        ProfileItemsRequest invalidRequest = new ProfileItemsRequest();
        invalidRequest.setHobbyListRequest(hobbyList);

        mockMvc.perform(post("/api/admin/profile-items")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.error").value(containsString("趣味名は必須です")));
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること（趣味・経歴）")
    void shouldReturn401WhenNoCookieOnSaveProfileItems() throws Exception {
        mockMvc.perform(post("/api/admin/profile-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProfileItemsRequest())))
                .andExpect(status().isUnauthorized());
    }
}