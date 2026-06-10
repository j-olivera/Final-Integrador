package com.olivera.challenge.infrastructure.controllers.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.application.port.in.order.GetOrdersByUserActiveEmail;
import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrder createOrder;
    private final GetOrdersByUserActiveEmail getOrders;
    public OrderController(CreateOrder createOrder,  GetOrdersByUserActiveEmail getOrders) {
        this.createOrder = createOrder;
        this.getOrders = getOrders;
    }

    @PostMapping //cambio para más tarde
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest, @AuthenticationPrincipal String email) { // cambiariamos esto, usando Auth
        OrderResponse response = createOrder.createOrder(createOrderRequest,email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByEmail(@AuthenticationPrincipal String email){
        List<OrderResponse> orders = getOrders.execute(email);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
// se agrego el get para mostrar las ordenes en el front
// se cambio el metodo de autenticacion para agregar un auth-guard y un metodo isLogged() en el auth-service del front