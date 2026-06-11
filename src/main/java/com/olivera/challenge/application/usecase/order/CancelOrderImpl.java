package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.port.in.order.CancelOrder;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.exceptions.order.OrderForbiddenException;
import com.olivera.challenge.domain.exceptions.order.OrderNotFoundException;

public class CancelOrderImpl implements CancelOrder {

  private final OrderRepositoryPort orderRepositoryPort;
  private final TimeProvider timeProvider;

  public CancelOrderImpl(OrderRepositoryPort orderRepositoryPort, TimeProvider timeProvider) {
    this.orderRepositoryPort = orderRepositoryPort;
    this.timeProvider = timeProvider;
  }

  @Override
  public OrderResponse execute(Long id, String email) {
    Order order = orderRepositoryPort.findById(id)
        .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

    if (!order.getUser().getEmail().equals(email)) {
      throw new OrderForbiddenException("You do not have permission to cancel this order");
    } // esto no deberia pasar, gracias a springSecurity

    order.cancel(timeProvider.now());
    Order savedOrder = orderRepositoryPort.save(order);
    return OrderMapper.toOrderResponse(savedOrder);
  }
}
