package com.packit.api.common.exception.base;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    String getErrorCode();         // 예: "USR_001"
    String getMessage();           // 예: "존재하지 않는 사용자입니다."
    HttpStatus getHttpStatus();   // 예: HttpStatus.NOT_FOUND
}