package com.packit.api.common.response;

import lombok.Getter;

@Getter
public class ExceptionResponse extends CommonResponse {

    public ExceptionResponse(String message, int code) {
        super(false, code, message);
    }
}