package com.olivera.challenge.infrastructure.controllers.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.request.UpdateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.in.order.CancelOrder;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.application.port.in.order.GetOrdersByUserActiveEmail;
import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import com.olivera.challenge.application.port.in.order.UpdateOrder;
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
    private final UpdateOrder updateOrder;
    private final CancelOrder cancelOrder;

    public OrderController(CreateOrder createOrder, GetOrdersByUserActiveEmail getOrders, UpdateOrder updateOrder, CancelOrder cancelOrder) {
        this.createOrder = createOrder;
        this.getOrders = getOrders;
        this.updateOrder = updateOrder;
        this.cancelOrder = cancelOrder;
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

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest request, @AuthenticationPrincipal String email) {
        OrderResponse response = updateOrder.execute(id, request, email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id, @AuthenticationPrincipal String email) {
        OrderResponse response = cancelOrder.execute(id, email);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
// se agrego el get para mostrar las ordenes en el front
// se cambio el metodo de autenticacion para agregar un auth-guard y un metodo isLogged() en el auth-service del front