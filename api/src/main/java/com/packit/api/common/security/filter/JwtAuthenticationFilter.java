package com.packit.api.common.security.filter;

import com.packit.api.common.dto.PermitAllProperties;
import com.packit.api.common.security.jwt.JwtAuthenticationToken;
import com.packit.api.common.security.jwt.JwtTokenProvider;
import com.packit.api.common.security.jwt.JwtUtils;
import com.packit.api.common.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 토큰을 검증하여 인증하는 필터
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final PermitAllProperties permitAllProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (permitAllProperties.getUrls().stream().anyMatch(url -> pathMatcher.match(url, requestURI))) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtUtils.getTokenFromHeader(request);

        // Access Token이 있는 경우
        if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
            setAuthentication(accessToken);
        }
        // Refresh Token이 있는 경우 (Access Token 만료) Access Token, Refresh Token 재발급
//        else if (refreshToken != null) {
//            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
//                setAuthentication(refreshToken);
//                jwtService.reissueToken(refreshToken, response);
//            }
//        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        String username = jwtTokenProvider.getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 토큰이 유효하고, SecurityContext에 Authentication 객체가 없는 경우
        if (userDetails != null) {
            // Authentication 객체 생성 후 SecurityContext에 저장 (인증 완료)
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(userDetails, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }


}
