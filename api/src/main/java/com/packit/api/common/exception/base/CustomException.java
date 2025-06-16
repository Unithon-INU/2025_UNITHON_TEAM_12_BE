package com.packit.api.common.exception.base;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    private final BaseErrorCode errorCode;

    protected CustomException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
