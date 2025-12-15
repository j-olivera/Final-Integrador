package com.olivera.challenge.infrastructure.csv;

import com.olivera.challenge.domain.entities.Order;

import java.io.PrintWriter;
import java.util.List;

public class CsvOrderGenerator {
    public static void writeOrder(PrintWriter writer, List<Order> orders) {
        writer.println("Order ID, User Email, Amount, Status");
        for (Order order : orders) {
            writer.println(order.getId()+","+order.getUser().getEmail()+","+order.getAmount()+","+order.getStatus());
        }
    }
}
