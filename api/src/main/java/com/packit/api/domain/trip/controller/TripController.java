package com.packit.api.domain.trip.controller;

import com.packit.api.common.response.ListResponse;
import com.packit.api.common.response.SingleResponse;
import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.trip.dto.request.TripCreateRequest;
import com.packit.api.domain.trip.dto.request.TripUpdateRequest;
import com.packit.api.domain.trip.dto.response.TripNearestResponse;
import com.packit.api.domain.trip.dto.response.TripProgressCountResponse;
import com.packit.api.domain.trip.dto.response.TripProgressResponse;
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
    public ResponseEntity<SingleResponse<TripResponse>> createTrip(
            @RequestBody @Valid TripCreateRequest request) {
        TripResponse response = tripService.createTrip(request);
        return ResponseEntity.ok(new SingleResponse<>(200, "여행 생성 완료", response));
    }

    @Operation(summary = "내 여행 목록 조회", description = "로그인한 사용자의 전체 여행 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ListResponse<TripResponse>> getTripList() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<TripResponse> trips = tripService.getTripList(userId);
        return ResponseEntity.ok(new ListResponse<>(200, "내 여행 목록 조회 완료", trips));
    }

    @Operation(summary = "여행 상세 조회", description = "여행 ID에 해당하는 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<SingleResponse<TripResponse>> getTripDetail(
            @Parameter(name = "id", description = "조회할 여행 ID", required = true)
            @PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        TripResponse detail = tripService.getTripDetail(id, userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "여행 상세 조회 완료", detail));
    }


    @Operation(summary = "여행 수정", description = "여행 ID에 해당하는 여행 정보를 수정합니다.")
    @PatchMapping("/{tripId}")
    public ResponseEntity<SingleResponse<TripResponse>> updateTrip(
            @Parameter(name = "tripId", description = "수정할 여행 ID", required = true)
            @PathVariable Long tripId,
            @Parameter(name = "request", description = "수정할 여행 정보", required = true)
            @RequestBody @Valid TripUpdateRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        TripResponse updated = tripService.updateTrip(tripId, userId, request);
        return ResponseEntity.ok(new SingleResponse<>(200, "여행 수정 완료", updated));
    }

    @Operation(summary = "여행 삭제", description = "여행 ID에 해당하는 여행을 삭제합니다.")
    @DeleteMapping("/{tripId}")
    public ResponseEntity<SingleResponse<String>> deleteTrip(
            @Parameter(name = "tripId", description = "삭제할 여행 ID", required = true)
            @PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        tripService.deleteTrip(tripId, userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "여행 삭제 완료", "success"));
    }

    @Operation(summary = "전체 짐싸기 진행률 조회", description = "여행 내 전체 카테고리의 짐싸기 완료 상태를 기반으로 진행률을 반환합니다.")
    @GetMapping("/{tripId}/progress")
    public ResponseEntity<SingleResponse<TripProgressResponse>> getTripProgress(@PathVariable Long tripId) {
        Long userId = SecurityUtils.getCurrentUserId();
        TripProgressResponse progress = tripService.getTripProgress(tripId, userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "짐싸기 진행률 조회 완료", progress));
    }

    @GetMapping("/{tripId}/progress-count")
    @Operation(summary = "Trip 전체 진행률 조회", description = "해당 Trip의 전체 아이템 대비 체크된 항목 비율(%)을 반환합니다.")
    public ResponseEntity<SingleResponse<TripProgressCountResponse>> getTripProgressCount(@PathVariable Long tripId) {
        // 권한 체크 필요 시 TripService 내에서 수행
        TripProgressCountResponse response = tripService.getTripProgressCount(tripId);
        return ResponseEntity.ok(new SingleResponse<>(200, "여행 진행률 조회 성공", response));
    }

    @Operation(summary = "가장 가까운 완료된 여행 조회", description = "오늘 이후 출발하는 완료된 여행 중 가장 빠른 여행을 조회합니다.")
    @GetMapping("/trips/nearest-completed")
    public ResponseEntity<SingleResponse<TripNearestResponse>> getNearestCompletedTrip() {
        Long userId = SecurityUtils.getCurrentUserId();
        TripNearestResponse response = tripService.getNearestCompletedTrip(userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "조회 성공", response));
    }
}
