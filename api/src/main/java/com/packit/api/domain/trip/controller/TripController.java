package com.packit.api.domain.trip.controller;

import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.trip.dto.request.TripCreateRequest;
import com.packit.api.domain.trip.dto.request.TripUpdateRequest;
import com.packit.api.domain.trip.dto.response.TripResponse;
import com.packit.api.domain.trip.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@Tag(name = "Trip", description = "여행 생성 및 관리 API")
@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @Operation(
            summary = "여행 생성",
            description = "여행 제목, 지역, 시작일/종료일, 유형, 설명을 입력받아 새로운 여행을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<TripResponse> createTrip(
            @RequestBody @Valid TripCreateRequest request) {
        TripResponse response = tripService.createTrip(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "내 여행 목록 조회", description = "로그인한 사용자의 전체 여행 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<TripResponse>> getTripList() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripService.getTripList(userId));
    }

    @Operation(summary = "여행 상세 조회", description = "여행 ID에 해당하는 상세 정보를 조회합니다.")
    @Parameter(name = "id", description = "조회할 여행 ID", required = true)
    @GetMapping("/{id}")
    public ResponseEntity<TripResponse> getTripDetail(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripService.getTripDetail(id, userId));
    }

    @Operation(summary = "여행 수정", description = "여행 ID에 해당하는 여행 정보를 수정합니다.")
    @Parameters({
            @Parameter(name = "id", description = "수정할 여행 ID", required = true),
            @Parameter(name = "request", description = "수정할 여행 정보", required = true)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<TripResponse> updateTrip(
            @PathVariable Long id,
            @RequestBody @Valid TripUpdateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(tripService.updateTrip(id, userId, request));
    }

    @Operation(summary = "여행 삭제", description = "여행 ID에 해당하는 여행을 삭제합니다.")
    @Parameter(name = "id", description = "삭제할 여행 ID", required = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripService.deleteTrip(id, userId);
        return ResponseEntity.noContent().build();
    }
}
