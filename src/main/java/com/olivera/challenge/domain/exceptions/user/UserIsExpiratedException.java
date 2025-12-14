package com.olivera.challenge.domain.exceptions.user;

public class UserIsExpiratedException extends RuntimeException {
    public UserIsExpiratedException(String message) {
        super(message);
    }
}
