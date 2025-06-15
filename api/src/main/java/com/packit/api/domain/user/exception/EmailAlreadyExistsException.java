package com.packit.api.domain.user.exception;

import com.packit.api.common.exception.BadRequestException;

public class EmailAlreadyExistsException extends BadRequestException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}