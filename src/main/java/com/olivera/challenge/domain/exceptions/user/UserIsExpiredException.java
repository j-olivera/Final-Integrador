package com.olivera.challenge.domain.exceptions.user;

public class UserIsExpiredException extends RuntimeException {
    public UserIsExpiredException(String message) {
        super(message);
    }
}
