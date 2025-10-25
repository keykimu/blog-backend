package com.example.portfolio.controller.admin;

import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.response.admin.AuthCheckResponse;
import com.example.portfolio.response.admin.AuthResponse;
import com.example.portfolio.response.admin.UserResponse;
import com.example.portfolio.service.admin.AdminAuthService;
import com.example.portfolio.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "サーバーエラー",
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
            description = "ユーザー名とパスワードでログインし、JWTトークンを取得します。",
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
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }

    @Operation(
            summary = "JWTトークン検証",
            description = "Authorizationヘッダーに渡されたJWTトークンの有効性を確認します。",
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
    public ResponseEntity<AuthCheckResponse> check(@RequestHeader("Authorization") String token)  {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return ResponseEntity.ok(jwtUtil.validateToken(token));
    }
}
