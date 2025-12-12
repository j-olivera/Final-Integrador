package com.olivera.challenge.domain.exceptions.user;

public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException(String message) {
        super(message);
    }
}
