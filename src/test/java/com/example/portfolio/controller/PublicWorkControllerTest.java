package com.example.portfolio.controller;

import com.example.portfolio.exception.NotFoundException;
import com.example.portfolio.response.PublicWorkResponse;
import com.example.portfolio.service.PublicWorksService;
import com.example.portfolio.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

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

    @Test
    @DisplayName("指定したIDの成果物が取得できること")
    void shouldGetWorkById() throws Exception {
        PublicWorkResponse mockWork = new PublicWorkResponse();
        mockWork.setTitle("ポートフォリオサイト");

        when(publicWorksService.getWorks(1L)).thenReturn(mockWork);

        mockMvc.perform(get("/api/works/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("ポートフォリオサイト"));
    }

    @Test
    @DisplayName("他人の成果物を取得しようとした場合に403を返すこと")
    void shouldReturn403WhenAccessingOtherUsersWork() throws Exception {
        // 403エラーを投げるように設定
        when(publicWorksService.getWorks(999L))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "他人のデータは操作できません"));

        mockMvc.perform(get("/api/works/999"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("存在しない成果物IDを指定した場合に404を返すこと")
    void shouldReturn404WhenWorkNotFound() throws Exception {
        when(publicWorksService.getWorks(100L))
                .thenThrow(new NotFoundException("対象の Work が存在しません"));

        mockMvc.perform(get("/api/works/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"));
    }
}