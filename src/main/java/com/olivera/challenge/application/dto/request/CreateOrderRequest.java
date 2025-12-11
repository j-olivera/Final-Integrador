package com.olivera.challenge.application.dto.request;

import com.olivera.challenge.domain.exceptions.InvalidDataException;

import java.math.BigDecimal;

public class CreateOrderRequest {

    private BigDecimal amount;

    public CreateOrderRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void validar(){
        if(this.amount == null){
            throw new InvalidDataException("Monto no valido");
        }
    }
}
