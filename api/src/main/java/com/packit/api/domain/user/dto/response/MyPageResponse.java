package com.packit.api.domain.user.dto.response;


import com.packit.api.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

public record MyPageResponse(
        @Schema(description = "회원 이메일", example = "test@example.com")
        String email,

        @Schema(description = "회원 닉네임", example = "gisu")
        String nickname
) {
    public static MyPageResponse of(String email, String nickname) {
        return new MyPageResponse(email, nickname);
    }
}