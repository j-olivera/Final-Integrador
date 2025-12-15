package com.olivera.challenge.infrastructure.controllers.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class OrderController {

    private final CreateOrder createOrder;

    public OrderController(CreateOrder createOrder) {
        this.createOrder = createOrder;
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest, @PathVariable Long userId) {
        OrderResponse response = createOrder.createOrder(createOrderRequest,userId); //recordar que se verifica en el usecase
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
