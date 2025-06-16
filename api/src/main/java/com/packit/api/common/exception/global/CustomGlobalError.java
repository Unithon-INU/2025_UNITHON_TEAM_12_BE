package com.packit.api.common.exception.global;

import com.packit.api.common.exception.base.BaseErrorCode;
import com.packit.api.common.exception.base.CustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomGlobalError extends CustomException {
    public CustomGlobalError(GlobalErrorCode errorCode) {
        super(errorCode);
    }
}