package com.olivera.challenge.domain.exceptions.order;

public class OrderForbiddenException extends RuntimeException {
    public OrderForbiddenException(String message) {
        super(message);
    }
}
