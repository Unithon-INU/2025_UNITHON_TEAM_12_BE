package com.packit.api.domain.user.exception;

public class UserCreateFailException extends RuntimeException {
    public UserCreateFailException(String message) {
        super(message);
    }
}
