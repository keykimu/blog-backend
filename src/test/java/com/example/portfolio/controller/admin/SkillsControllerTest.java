package com.example.portfolio.controller.admin;

import com.example.portfolio.request.LanguageRequest;
import com.example.portfolio.request.SkillsRequest;
import com.example.portfolio.request.wrap.LanguageListRequest;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.SkillsResponse;
import com.example.portfolio.service.admin.AdminSkillsService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(SkillsController.class)
class SkillsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminSkillsService adminSkillsService;
    @MockitoBean private JwtUtil jwtUtil;
    @Autowired
    private ObjectMapper objectMapper;

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

    @Test
    @DisplayName("クッキーがない場合に401エラーになること（スキル）")
    void shouldReturn401WhenNoCookieOnGetSkills() throws Exception {
        mockMvc.perform(get("/api/admin/skills"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("言語・フレームワーク・その他を正しく一括登録できること")
    void shouldSaveSkillsSuccessfully() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        LanguageRequest lang = new LanguageRequest();
        lang.setName("Java");

        LanguageListRequest langList = new LanguageListRequest();
        langList.setLanguages(List.of(lang));

        SkillsRequest request = new SkillsRequest();
        request.setLanguageListRequest(langList);

        SkillsResponse mockResponse = new SkillsResponse();
        when(adminSkillsService.saveAll(any(SkillsRequest.class), eq(userId))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/admin/skills")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("スキルの言語名が空の場合に400を返すこと")
    void shouldReturn400WhenSkillNameIsEmpty() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        LanguageRequest lang = new LanguageRequest();
        lang.setName("");

        LanguageListRequest langList = new LanguageListRequest();
        langList.setLanguages(List.of(lang));

        SkillsRequest invalidRequest = new SkillsRequest();
        invalidRequest.setLanguageListRequest(langList);

        mockMvc.perform(post("/api/admin/skills")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること（スキル）")
    void shouldReturn401WhenNoCookieOnSaveSkills() throws Exception {
        mockMvc.perform(post("/api/admin/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new SkillsRequest())))
                .andExpect(status().isUnauthorized());
    }
}