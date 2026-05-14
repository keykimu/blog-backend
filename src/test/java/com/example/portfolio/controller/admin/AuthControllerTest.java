package com.example.portfolio.controller.admin;

import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.AuthResponse;
import com.example.portfolio.service.admin.AdminAuthService;
import com.example.portfolio.util.CookieProperties;
import com.example.portfolio.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AdminAuthService adminAuthService;
    @MockitoBean private JwtUtil jwtUtil;
    @MockitoBean private CookieProperties cookieProperties;

    @Test
    @DisplayName("ログイン成功時にJWTクッキーが発行されること")
    void login_ShouldReturnOkAndSetCookie() throws Exception {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("password");

        AuthResponse authResponse = new AuthResponse("mock-jwt-token");
        when(adminAuthService.login(authRequest)).thenReturn(authResponse);
        when(cookieProperties.getSameSite()).thenReturn("localhost");

        mockMvc.perform(post("/api/admin/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"admin\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"))
                .andExpect(cookie().value("jwt", authResponse.getToken()))
                .andExpect(cookie().httpOnly("jwt", true));
    }

    @Test
    @DisplayName("ログアウト時にJWTクッキーが削除されること")
    void logout_ShouldReturnOkAndClearCookie() throws Exception {
        String mockToken = "valid-token";
        // ログアウトも認証が必要な場合は、ここでもJwtUtilをモックする
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(1L, "admin"));
        when(cookieProperties.getSameSite()).thenReturn("localhost");

        mockMvc.perform(post("/api/admin/auth/logout")
                .cookie(new Cookie("jwt", mockToken)))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("jwt", 0));
    }

    @Test
    @DisplayName("有効なトークンがある場合に認証チェックが成功すること")
    void check_ShouldReturnOkWhenAuthenticated() throws Exception {
        Long userId = 1L;
        String mockToken = "valid-token";
        when(jwtUtil.validateToken(mockToken)).thenReturn(new AuthCheckResponse(userId, mockToken));

        mockMvc.perform(get("/api/admin/auth/check")
                .cookie(new Cookie("jwt", mockToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId));
    }
}