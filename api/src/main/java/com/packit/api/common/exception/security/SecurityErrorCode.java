package com.packit.api.common.exception.security;

import com.packit.api.common.exception.base.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode implements BaseErrorCode {

    INVALID_TOKEN("SEC_001", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN("SEC_002", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_NOT_FOUND("SEC_003", "토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_SIGNATURE("SEC_004", "잘못된 토큰 서명입니다.", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("SEC_005", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ACCOUNT_LOCKED("SEC_006", "계정이 잠겼습니다. 관리자에게 문의하세요.", HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS("SEC_007", "잘못된 인증 정보입니다.", HttpStatus.UNAUTHORIZED);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
