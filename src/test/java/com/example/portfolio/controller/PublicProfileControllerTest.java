package com.example.portfolio.controller;

import com.example.portfolio.exception.NotFoundException;
import com.example.portfolio.response.PublicProfileResponse;
import com.example.portfolio.service.PublicProfileService;
import com.example.portfolio.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicProfileController.class)
class PublicProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PublicProfileService publicProfileService;
    @MockitoBean private JwtUtil jwtUtil;

    @Test
    @DisplayName("公開用プロフィールが取得できること")
    void shouldReturnPublicProfile() throws Exception {
        when(publicProfileService.getProfile()).thenReturn(new PublicProfileResponse());
        mockMvc.perform(get("/api/profile")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("データが存在しない時に404を返すこと")
    void shouldReturn404WhenNotFound() throws Exception {
        when(publicProfileService.getProfile()).thenThrow(new NotFoundException("対象の Profile が存在しません"));

        mockMvc.perform(get("/api/profile"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("対象の Profile が存在しません"))
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }

}