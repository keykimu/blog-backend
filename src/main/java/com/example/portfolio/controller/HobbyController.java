package com.example.portfolio.controller;


import com.example.portfolio.request.wrap.HobbyListRequest;
import com.example.portfolio.response.ApiErrorResponse;
import com.example.portfolio.response.HobbyResponse;
import com.example.portfolio.service.HobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Hobby", description = "趣味情報 API")
@RequestMapping("/api/admin/hobby")
@RequiredArgsConstructor
public class HobbyController {
    private final HobbyService hobbyService;

    @Operation(summary = "趣味を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "サーバーエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<HobbyResponse>> getAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<HobbyResponse> hobbies = hobbyService.getAllHobbies(userId);
        return ResponseEntity.ok(hobbies);
    }

    @Operation(summary = "趣味をまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "500", description = "サーバーエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<List<HobbyResponse>> create(@Valid @RequestBody HobbyListRequest createRequest,HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<HobbyResponse> created = hobbyService.createHobby(createRequest,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
