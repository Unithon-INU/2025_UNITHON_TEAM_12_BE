package com.packit.api.domain.user.exception;

import com.packit.api.common.exception.BadRequestException;

public class NotSamePasswordException extends BadRequestException {

    public NotSamePasswordException(String message) {
        super(message);
    }
}
