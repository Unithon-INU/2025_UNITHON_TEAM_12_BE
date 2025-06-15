package com.packit.api.common.security.filter;


import com.packit.api.common.response.ExceptionResponse;
import com.packit.api.common.security.exception.SecurityErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 예외 처리 필터
 * - 예외 발생 시, 클라이언트에게 응답을 보내는 필터
 * - JwtException 발생 시, INVALID_TOKEN 응답
 * - 그 외 예외 발생 시, INVALID_TOKEN 응답
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("[doFilterInternal] Exception in ExceptionHandlerFilter: ", e);
            sendErrorResponse(response, SecurityErrorCode.INVALID_TOKEN);
        }
    }

    private void sendErrorResponse(HttpServletResponse response, SecurityErrorCode code) throws IOException {
        ResponseEntity<?> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse(code.getMessage(), HttpStatus.UNAUTHORIZED.value()));

        // Set the HttpServletResponse properties
        response.setStatus(responseEntity.getStatusCode().value());
        response.setContentType("application/json");

        // Use an ObjectMapper to write the ResponseEntity body as JSON to the response output stream
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), responseEntity.getBody());
    }

}
