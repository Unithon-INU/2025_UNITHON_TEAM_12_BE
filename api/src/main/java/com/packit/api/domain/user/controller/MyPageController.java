package com.packit.api.domain.user.controller;

import com.packit.api.common.response.SingleResponse;
import com.packit.api.common.security.util.SecurityUtils;
import com.packit.api.domain.user.dto.response.MyPageResponse;
import com.packit.api.domain.user.service.MyPageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/mypage")
@Tag(name = "MyPage", description = "마이페이지 관련 API")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    @Operation(summary = "내 정보 조회", description = "로그인한 회원의 이메일과 닉네임을 반환합니다.")
    public ResponseEntity<SingleResponse<MyPageResponse>> getMyInfo() {
        Long userId = SecurityUtils.getCurrentUserId();
        MyPageResponse response = myPageService.getMyInfo(userId);
        return ResponseEntity.ok(new SingleResponse<>(200, "마이페이지 조회 성공", response));
    }
}
