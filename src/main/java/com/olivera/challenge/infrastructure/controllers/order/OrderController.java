package com.olivera.challenge.infrastructure.controllers.order;

import com.olivera.challenge.application.dto.request.CreateOrderRequest;
import com.olivera.challenge.application.dto.response.OrderResponse;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("http://localhost:4200")
public class OrderController {

    private final CreateOrder createOrder;
    private final RetrieveAllOrders retrieveAllOrders;
    public OrderController(CreateOrder createOrder, RetrieveAllOrders retrieveAllOrders) {
        this.createOrder = createOrder;
        this.retrieveAllOrders = retrieveAllOrders;
    }

    @PostMapping //cambio para más tarde
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest createOrderRequest, @AuthenticationPrincipal String email) { // cambiariamos esto, usando Auth
        OrderResponse response = createOrder.createOrder(createOrderRequest,email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrder(@AuthenticationPrincipal String email){
        List<OrderResponse> orders = retrieveAllOrders.execute();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }
}
// se agrego el get para mostrar las ordenes en el front
// se cambio el metodo de autenticacion para agregar un auth-guard y un metodo isLogged() en el auth-service del front