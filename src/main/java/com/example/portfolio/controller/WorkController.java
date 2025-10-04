package com.example.portfolio.controller;

import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.WorkResponse;
import com.example.portfolio.service.WorkService;
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
@Tag(name = "Work", description = "成果物情報 API")
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

    @Operation(summary = "成果物をまとめて取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @GetMapping
    public ResponseEntity<List<WorkResponse>> getAll() {
        List<WorkResponse> works = workService.getAllWorks();
        return ResponseEntity.ok(works);
    }

    @Operation(summary = "成果物を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "404", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkResponse> getById(@PathVariable Long id) {
        WorkResponse work = workService.getWork(id);
        if (work != null) {
            return ResponseEntity.ok(work);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "成果物を作成")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "取得作成"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @PostMapping
    public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest request) {
        WorkResponse createdWork = workService.createWork(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWork);
    }

    @Operation(summary = "成果物を更新")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "404", description = "バリデーションエラー"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @PutMapping("/{id}")
    public ResponseEntity<WorkResponse> update(@PathVariable Long id, @Valid @RequestBody WorkUpdateRequest request) {
        WorkResponse updatedWork = workService.updateWork(id, request);
        if (updatedWork != null) {
            return ResponseEntity.ok(updatedWork);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "成果物を削除")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "削除成功"),
            @ApiResponse(responseCode = "404", description = "指定IDの成果物が存在しない"),
            @ApiResponse(responseCode = "500", description = "サーバーエラー")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = workService.deleteWork(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
