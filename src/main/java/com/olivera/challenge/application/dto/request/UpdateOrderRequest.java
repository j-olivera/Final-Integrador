package com.olivera.challenge.application.dto.request;

import com.olivera.challenge.domain.exceptions.InvalidDataException;

import java.math.BigDecimal;

public class UpdateOrderRequest {
  private BigDecimal amount;

  public UpdateOrderRequest() {
  }

  public UpdateOrderRequest(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void validate() {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidDataException("El monto debe ser proporcionado y mayor a cero");
    }
  }
}
