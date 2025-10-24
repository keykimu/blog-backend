package com.example.portfolio.util;

import com.example.portfolio.response.AuthCheckResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // プリフライト OPTIONS は認証スキップ
        if ("OPTIONS".equalsIgnoreCase(method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Swagger UI と OpenAPI を除外
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 認証不要なパスを除外
        if (isPublicEndpoint(path, method)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 認証が必要な場合
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            unauthorized(response, "認証トークンが必要です");
            return;
        }

        String token = authHeader.substring(7);

        try {
            AuthCheckResponse auth = jwtUtil.validateToken(token);
            request.setAttribute("userId", auth.getUserId());
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            unauthorized(response, ex.getMessage());
        }
    }

    /** 公開エンドポイント判定 */
    private boolean isPublicEndpoint(String path, String method) {
        // 管理者ログインと管理者用API以外はすべて公開
        return path.startsWith("/api/admin/auth/login") ||!path.startsWith("/api/admin");
    }

    /** 認証失敗レスポンス */
    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.getWriter().write("{\"status\":401, \"message\":\"" + message + "\"}");
    }
}
