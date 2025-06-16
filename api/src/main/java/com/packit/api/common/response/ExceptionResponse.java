package com.packit.api.common.response;

import com.packit.api.common.exception.base.BaseErrorCode;
import lombok.Getter;

@Getter

public class ExceptionResponse extends CommonResponse {

    public ExceptionResponse(String message, int code) {
        super(false, code, message);
    }
}