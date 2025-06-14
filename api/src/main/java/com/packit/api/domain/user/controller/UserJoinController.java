package com.packit.api.domain.user.controller;

import com.packit.api.common.response.SingleResponse;
import com.packit.api.domain.user.service.UserJoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User Join API", description = "회원가입 API")
@RequiredArgsConstructor
public class UserJoinController {

    private final UserJoinService userJoinService;

    @Operation(summary = "이메일 중복 확인", description = "입력한 이메일이 사용 가능한지 여부를 확인합니다.")
    @GetMapping("/check/email")
    public ResponseEntity<SingleResponse<String>> checkEmail(
            @Parameter(description = "중복 확인할 이메일", example = "test@example.com")
            @RequestParam String email) {

        userJoinService.validateEmailAvailable(email);
        return ResponseEntity.ok(new SingleResponse<>(200, "사용 가능한 이메일입니다.", "OK"));
    }

    @Operation(summary = "닉네임 중복 확인", description = "입력한 닉네임이 사용 가능한지 여부를 확인합니다.")
    @GetMapping("/check/nickname")
    public ResponseEntity<SingleResponse<String>> checkNickname(
            @Parameter(description = "중복 확인할 닉네임", example = "test")
            @RequestParam String nickname) {

        userJoinService.validateNicknameAvailable(nickname);
        return ResponseEntity.ok(new SingleResponse<>(200, "사용 가능한 닉네임입니다.", "OK"));
    }
}