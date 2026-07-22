package com.example.portfolio.controller.admin;

import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.WorkResponse;
import com.example.portfolio.service.admin.AdminWorkService;
import com.example.portfolio.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WorkController.class)
class WorkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminWorkService adminWorkService;
    @MockitoBean private JwtUtil jwtUtil;
    @Autowired
    private JsonMapper mapper;

    @Test
    @DisplayName("管理者用の成果物一覧が取得できること")
    void shouldGetAllWorks() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        when(adminWorkService.getAllWorks(userId)).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/works")
                .cookie(new Cookie("jwt", mockToken))
                .requestAttr("userId", userId))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること（スキル）")
    void shouldReturn401WhenNoCookieOnGetWorks() throws Exception {
        mockMvc.perform(get("/api/admin/works"))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("指定したIDの成果物を正常に取得できること")
    void shouldGetWorkByIdSuccessfully() throws Exception {
        Long userId = 1L;
        Long workId = 10L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));
        WorkResponse mockResponse = new WorkResponse();
        mockResponse.setId(workId);
        mockResponse.setTitle("テスト作品");

        when(adminWorkService.getWork(workId, userId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/admin/works/{id}", workId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(workId))
                .andExpect(jsonPath("$.title").value("テスト作品"));
    }

    @Test
    @DisplayName("他人の成果物を取得しようとした場合に403を返すこと")
    void shouldReturn403WhenGettingOtherUserWork() throws Exception {
        Long userId = 1L;
        Long otherWorkId = 999L;
        String mockToken = "valid-token";

        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        when(adminWorkService.getWork(otherWorkId, userId))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "他人のデータは操作できません"));

        mockMvc.perform(get("/api/admin/works/{id}", otherWorkId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("クッキーがない場合に401エラーになること")
    void shouldReturn401WhenNoCookie() throws Exception {
        Long workId = 10L;

        mockMvc.perform(get("/api/admin/works/{id}", workId))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("成果物を正しく作成でき、201を返すこと")
    void shouldCreateWorkSuccessfully() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.png",
                MediaType.IMAGE_PNG_VALUE,
                "dumy image content".getBytes()
        );

        WorkResponse mockResponse = new WorkResponse();
        mockResponse.setId(100L);
        mockResponse.setTitle("マイポートフォリオ");

        when(adminWorkService.createWork(any(WorkCreateRequest.class), eq(userId))).thenReturn(mockResponse);

        mockMvc.perform(multipart("/api/admin/works")
                        .file(mockMultipartFile)
                        .param("title","マイポートフォリオ")
                        .param("description","Spring bootで作った作品です")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated()) // ここが 201 よ！
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.title").value("マイポートフォリオ"));
    }

    @Test
    @DisplayName("タイトルが空の場合に400エラーになること")
    void shouldReturn400WhenTitleIsEmpty() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        WorkCreateRequest invalidRequest = new WorkCreateRequest();
        invalidRequest.setTitle("");
        invalidRequest.setDescription("説明はあるけどタイトルがない");

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file","test.png", MediaType.IMAGE_PNG_VALUE,"dumy".getBytes()
        );

        mockMvc.perform(multipart("/api/admin/works")
                        .file(mockMultipartFile)
                        .param("title","")
                        .param("description","説明はあるがタイトルがない")
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("クッキーがない場合に作成を拒否し401を返すこと")
    void shouldReturn401WhenNoCookieOnCreate() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file","test.png", MediaType.IMAGE_PNG_VALUE,"dumy".getBytes()
        );

        mockMvc.perform(multipart("/api/admin/works")
                        .file(mockMultipartFile)
                        .param("title","タイトル")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("成果物を正常に更新でき、200を返すこと")
    void shouldUpdateWorkSuccessfully() throws Exception {
        Long userId = 1L;
        Long workId = 10L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "update.png", MediaType.IMAGE_PNG_VALUE, "updated content".getBytes()
        );

        WorkResponse mockResponse = new WorkResponse();
        mockResponse.setId(workId);
        mockResponse.setTitle("更新後のタイトル");

        when(adminWorkService.updateWork(eq(workId), any(WorkUpdateRequest.class), eq(userId)))
                .thenReturn(mockResponse);

        mockMvc.perform(multipart("/api/admin/works/{id}", workId)
                        .file(mockFile)
                        .param("title", "更新後のタイトル")
                        .param("description", "更新後の説明文です")
                        .with(request -> { request.setMethod("PUT"); return request; }) // ★ ここで PUT メソッドに変更するわよ！
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("更新後のタイトル"));
    }

    @Test
    @DisplayName("更新内容が不正な場合に400エラーになること")
    void shouldReturn400WhenUpdateDataIsInvalid() throws Exception {
        Long userId = 1L;
        Long workId = 10L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.png", MediaType.IMAGE_PNG_VALUE, "dummy".getBytes()
        );

        mockMvc.perform(multipart("/api/admin/works/{id}", workId)
                        .file(mockFile)
                        .param("title", "") // タイトルを空にしてバリデーションエラーを起こすわ
                        .param("description", "説明文")
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("他人の成果物を更新しようとした場合に403を返すこと")
    void shouldReturn403WhenUpdatingOtherUserWork() throws Exception {
        Long userId = 1L;
        Long otherWorkId = 999L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.png", MediaType.IMAGE_PNG_VALUE, "dummy".getBytes()
        );

        when(adminWorkService.updateWork(eq(otherWorkId), any(), eq(userId)))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "他人のデータは操作できません"));

        mockMvc.perform(multipart("/api/admin/works/{id}", otherWorkId)
                        .file(mockFile)
                        .param("title", "バリデーション通るタイトル")
                        .param("description", "バリデーション通る説明")
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("更新対象が存在しない場合に404を返すこと")
    void shouldReturn404WhenWorkToUpdateNotFound() throws Exception {
        Long userId = 1L;
        Long missingId = 404L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.png", MediaType.IMAGE_PNG_VALUE, "dummy".getBytes()
        );

        when(adminWorkService.updateWork(eq(missingId), any(), eq(userId))).thenReturn(null);

        mockMvc.perform(multipart("/api/admin/works/{id}", missingId)
                        .file(mockFile)
                        .param("title", "バリデーション通るタイトル")
                        .param("description", "バリデーション通る説明")
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("クッキーがない場合に更新を拒否し401を返すこと")
    void shouldReturn401WhenNoCookieOnUpdate() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.png", MediaType.IMAGE_PNG_VALUE, "dummy".getBytes()
        );

        mockMvc.perform(multipart("/api/admin/works/{id}", 10L)
                        .file(mockFile)
                        .param("title", "タイトル")
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isUnauthorized());
    }


    @Test
    @DisplayName("成果物を正常に削除でき、204を返すこと")
    void shouldDeleteWorkSuccessfully() throws Exception {
        Long userId = 1L;
        Long workId = 10L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        when(adminWorkService.deleteWork(workId, userId)).thenReturn(true);

        mockMvc.perform(delete("/api/admin/works/{id}", workId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("他人の成果物を削除しようとした場合に403を返すこと")
    void shouldReturn403WhenDeletingOtherUserWork() throws Exception {
        Long userId = 1L;
        Long otherWorkId = 999L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        when(adminWorkService.deleteWork(otherWorkId, userId))
                .thenThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "他人のデータは操作できません"));

        mockMvc.perform(delete("/api/admin/works/{id}", otherWorkId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("削除対象が存在しない場合に404を返すこと")
    void shouldReturn404WhenWorkToDeleteNotFound() throws Exception {
        Long userId = 1L;
        Long missingId = 404L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        when(adminWorkService.deleteWork(missingId, userId)).thenReturn(false);

        mockMvc.perform(delete("/api/admin/works/{id}", missingId)
                        .cookie(new Cookie("jwt", mockToken))
                        .requestAttr("userId", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("クッキーがない場合に削除を拒否し401を返すこと")
    void shouldReturn401WhenNoCookieOnDelete() throws Exception {
        mockMvc.perform(delete("/api/admin/works/{id}", 10L))
                .andExpect(status().isUnauthorized());
    }
}