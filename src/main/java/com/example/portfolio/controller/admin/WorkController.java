package com.example.portfolio.controller.admin;

import com.example.portfolio.request.WorkCreateRequest;
import com.example.portfolio.request.WorkUpdateRequest;
import com.example.portfolio.response.common.ApiErrorResponse;
import com.example.portfolio.response.admin.WorkResponse;
import com.example.portfolio.service.admin.AdminWorkService;
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
@Tag(name = "Work", description = "成果物情報 API")
@RequestMapping("/api/admin/works")
@RequiredArgsConstructor
public class WorkController {
    private final AdminWorkService adminWorkService;

    @Operation(summary = "成果物をまとめて取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<WorkResponse>> getAll(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        List<WorkResponse> works = adminWorkService.getAllWorks(userId);
        return ResponseEntity.ok(works);
    }

    @Operation(summary = "成果物を取得")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "取得成功"),
            @ApiResponse(responseCode = "403", description = "他人のデータは操作できません",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<WorkResponse> getById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        WorkResponse work = adminWorkService.getWork(id,userId);
        if (work != null) {
            return ResponseEntity.ok(work);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "成果物を作成")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "取得作成"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PostMapping
    public ResponseEntity<WorkResponse> create(@Valid @RequestBody WorkCreateRequest createRequest, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        WorkResponse createdWork = adminWorkService.createWork(createRequest,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWork);
    }

    @Operation(summary = "成果物を更新")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "他人のデータは操作できません",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "対象が存在しない",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<WorkResponse> update(@PathVariable Long id, @Valid @RequestBody WorkUpdateRequest updateRequest, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        WorkResponse updatedWork = adminWorkService.updateWork(id,updateRequest,userId);
        if (updatedWork != null) {
            return ResponseEntity.ok(updatedWork);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "成果物を削除")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "削除成功"),
            @ApiResponse(responseCode = "400", description = "バリデーションエラー",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "他人のデータは操作できません",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "対象が存在しない",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        boolean deleted = adminWorkService.deleteWork(id, userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
