package com.packit.api.common.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SignupRequestDto {

    @Schema(description = "이메일 주소", example = "gisu1102@gmail.com")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "password")
    @NotBlank
    private String password;

    @Schema(description = "닉네임", example = "코딩")
    @NotBlank
    private String nickname;




}
