package com.packit.api.common.security.dto;

import com.packit.api.domain.user.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
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

    @Schema(description = "이름", example = "김기수")
    @NotBlank
    private String name;

    @Schema(description = "나이", example = "25")
    @NotNull
    private Integer age;

    @Schema(description = "성별 (MALE / FEMALE / OTHER)", example = "MALE")
    @NotNull
    private Gender gender;
}
