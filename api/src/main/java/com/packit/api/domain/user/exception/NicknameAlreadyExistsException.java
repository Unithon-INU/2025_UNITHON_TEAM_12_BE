package com.packit.api.domain.user.exception;

import com.packit.api.common.exception.BadRequestException;

public class NicknameAlreadyExistsException extends BadRequestException {
    public NicknameAlreadyExistsException(String message) {
        super(message);
    }
}
