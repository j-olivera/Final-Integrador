package com.olivera.challenge.infrastructure.controllers.order;

import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.infrastructure.csv.CsvOrderGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderDowloadController {
    private final RetrieveAllOrders retrieveAllOrders;

    public OrderDowloadController(RetrieveAllOrders retrieveAllOrders) {
        this.retrieveAllOrders = retrieveAllOrders;
    }

    @GetMapping("/export")
    public void exportOrdersToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"orders.csv\"");
        List<Order> orders = retrieveAllOrders.execute();
        CsvOrderGenerator.writeOrder(response.getWriter(), orders);
    }
}
