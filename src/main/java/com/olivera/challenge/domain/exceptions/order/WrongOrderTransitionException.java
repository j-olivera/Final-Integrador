package com.olivera.challenge.domain.exceptions.order;

public class WrongOrderTransitionException extends RuntimeException {
    public WrongOrderTransitionException(String message) {
        super(message);
    }
}
