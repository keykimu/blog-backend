package com.example.portfolio.controller;

import com.example.portfolio.response.PublicProfileItemsResponse;
import com.example.portfolio.service.PublicProfileItemsService;
import com.example.portfolio.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicProfileItemController.class)
class PublicProfileItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PublicProfileItemsService publicProfileItemsService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("公開用プロフィール項目が取得できること")
    void shouldReturnPublicProfileItems() throws Exception {
        when(publicProfileItemsService.getAllByUserId()).thenReturn(new PublicProfileItemsResponse());
        mockMvc.perform(get("/api/profile-items")).andExpect(status().isOk());
    }

    @DisplayName("公開用アイテムが正常に取得できること(データが空でも200を返す)")
    void shouldGetPublicProfileItemsEvenIfEmpty() throws Exception {
        // 全て空のレスポンスを返すように設定
        PublicProfileItemsResponse mockResponse = new PublicProfileItemsResponse();
        // 内部のリストを空で初期化してセット
        mockResponse.setHobbyResponse(Collections.emptyList());
        mockResponse.setCareerResponse(Collections.emptyList());

        when(publicProfileItemsService.getAllByUserId()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/profile/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hobbyResponse").isArray())  // 配列であること
                .andExpect(jsonPath("$.hobbyResponse").isEmpty()); // 空であること
    }
}