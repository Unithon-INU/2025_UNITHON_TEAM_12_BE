package com.packit.api.common.exception.security;

import com.packit.api.common.exception.base.CustomException;

public class CustomSecurityError extends CustomException {
    public CustomSecurityError(SecurityErrorCode errorCode) {
        super(errorCode);
    }
}