package com.example.portfolio.controller;

import com.example.portfolio.response.PublicWorkResponse;
import com.example.portfolio.service.PublicWorksService;
import com.example.portfolio.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicWorkController.class)
class PublicWorkControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PublicWorksService publicWorksService;
    @MockitoBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("公開用成果物一覧が取得できること")
    void shouldReturnAllPublicWorks() throws Exception {
        PublicWorkResponse work1 = new PublicWorkResponse();
        work1.setId(1L);
        work1.setTitle("作品1");
        work1.setDescription("説明1");

        List<PublicWorkResponse> mockResponse = List.of(work1);

        when(publicWorksService.getAllByUserId()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/works"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("作品1"));
    }
}