package com.packit.api.common.exception.global;

import com.packit.api.common.exception.base.BaseErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum GlobalErrorCode implements BaseErrorCode {

    INVALID_INPUT_VALUE("GLOBAL_001", "잘못된 입력 값입니다.", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND("GLOBAL_002", "리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("GLOBAL_003", "서버 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    GlobalErrorCode(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
