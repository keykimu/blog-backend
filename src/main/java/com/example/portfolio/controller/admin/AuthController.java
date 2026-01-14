package com.example.portfolio.controller.admin;

import com.example.portfolio.exception.AuthFailedException;
import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.AuthResponse;
import com.example.portfolio.response.admin.UserResponse;
import com.example.portfolio.service.admin.AdminAuthService;
import com.example.portfolio.util.CookieProperties;
import com.example.portfolio.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Auth", description = "認証関連エンドポイント")
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AdminAuthService adminAuthService;
    private final JwtUtil jwtUtil;
    private final CookieProperties cookieSecure;
    private final CookieProperties cookieSameSite;

    @Operation(
            summary = "管理者一覧取得",
            description = "登録されている管理者（ユーザー）の一覧を取得します。",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Bearerトークン形式のJWT（管理者専用）",
                            required = true,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6..."
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "取得成功",
                            content = @Content(schema = @Schema(implementation = UserResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "認証エラー（トークン無効・期限切れ）",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    )
            }
    )
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAdmins() {
        return ResponseEntity.ok(adminAuthService.getAllAdmins());
    }

    @Operation(
            summary = "ログイン認証",
            description = "ユーザー名とパスワードで認証し、JWT を HttpOnly Cookie に返します",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ログイン成功",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "入力値エラー",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "認証失敗",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        AuthResponse auth = adminAuthService.login(request);
        // JWTをHttpOnly Cookieとしてセット
        ResponseCookie cookie = ResponseCookie.from("jwt", auth.getToken())
                .httpOnly(true)
                .secure(cookieSecure.isSecure()) // https利用時はtrueに
                .path("/")
                .sameSite(cookieSameSite.getSameSite())
                .maxAge(3600)
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "ログアウト",
            description = "JWT Cookie を削除してログアウトします。",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "ログアウト成功"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "未認証またはトークンが存在しない場合"
                    )
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(cookieSecure.isSecure())
                .path("/")
                .maxAge(0)
                .sameSite(cookieSameSite.getSameSite())
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "JWTトークン検証",
            description = "JWT CookieのJWTトークンの有効性を確認します。",
            parameters = {
                    @Parameter(
                            name = "Authorization",
                            description = "Bearerトークン形式のJWT",
                            required = true,
                            example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6..."
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "トークン有効",
                            content = @Content(schema = @Schema(implementation = AuthCheckResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "トークン期限切れまたは不正",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
                    )
            }
    )
    @GetMapping("/check")
    public ResponseEntity<AuthCheckResponse> check(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            throw new AuthFailedException("トークンが存在しません");
        }

        return ResponseEntity.ok(jwtUtil.validateToken(token));
    }
}
