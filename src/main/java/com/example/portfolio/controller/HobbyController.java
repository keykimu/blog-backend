package com.example.portfolio.controller;


import com.example.portfolio.request.wrap.HobbyListRequest;
import com.example.portfolio.response.HobbyResponse;
import com.example.portfolio.service.HobbyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Hobby", description = "趣味情報 API")
@RequestMapping("/api/hobby")
@RequiredArgsConstructor
public class HobbyController {
    private final HobbyService hobbyService;

    @Operation(summary = "趣味を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @GetMapping
    public ResponseEntity<List<HobbyResponse>> getAll() {
        List<HobbyResponse> hobbies = hobbyService.getAllHobbies();
        return ResponseEntity.ok(hobbies);
    }

    @Operation(summary = "趣味をまとめて登録")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "登録成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @PostMapping
    public ResponseEntity<List<HobbyResponse>> create(@Valid @RequestBody HobbyListRequest request) {
        List<HobbyResponse> created = hobbyService.createHobby(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
