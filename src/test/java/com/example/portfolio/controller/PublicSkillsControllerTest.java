package com.example.portfolio.controller;

import com.example.portfolio.response.PublicSkillsResponse;
import com.example.portfolio.service.PublicSkillsService;
import com.example.portfolio.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicSkillsController.class)
class PublicSkillsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PublicSkillsService publicSkillsService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("公開用スキル一覧が取得できること")
    void shouldReturnPublicSkills() throws Exception {
        when(publicSkillsService.getAllByUserId()).thenReturn(new PublicSkillsResponse());
        mockMvc.perform(get("/api/skills")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("スキル情報が登録されていない場合でも空のリストで200を返すこと")
    void shouldReturnEmptyListsWhenNoSkills() throws Exception {
        PublicSkillsResponse emptyResponse = new PublicSkillsResponse();
        emptyResponse.setLanguageResponse(Collections.emptyList());
        // 全て空のリストをセットした状態
        when(publicSkillsService.getAllByUserId()).thenReturn(emptyResponse);

        mockMvc.perform(get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.languageResponse").isArray())  // 配列であること
                .andExpect(jsonPath("$.languageResponse").isEmpty()); // 空であること
    }
}