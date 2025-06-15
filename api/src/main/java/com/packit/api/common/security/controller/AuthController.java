package com.packit.api.common.security.controller;


import com.packit.api.common.response.SingleResponse;
import com.packit.api.common.security.dto.LoginRequestDto;
import com.packit.api.common.security.dto.SignupRequestDto;
import com.packit.api.common.security.service.AuthService;
import com.packit.api.common.security.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth API")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;

    @Operation(summary = "회원가입", description = "중복 체크 이후 최종 회원가입 진행.")
    @PostMapping("/signup")
    public ResponseEntity<SingleResponse<?>> signUpUser(
            @RequestBody @Valid SignupRequestDto signupRequestDto) {
        authService.createUser(signupRequestDto);
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "회원가입 성공", null));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<SingleResponse<?>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        jwtService.createToken(response);

        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "로그인 성공",null));
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<SingleResponse<?>> logout() {
        jwtService.deleteToken();
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "로그아웃 성공",null));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<SingleResponse<?>> reissue(HttpServletRequest request, HttpServletResponse response) {
        jwtService.reissueToken(request, response);
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "토큰 재발급",null));
    }

}
