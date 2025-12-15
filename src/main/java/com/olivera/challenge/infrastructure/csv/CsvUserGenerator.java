package com.olivera.challenge.infrastructure.csv;

import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;

import java.io.PrintWriter;
import java.util.List;

public class CsvUserGenerator {
    public static void writeOrder(PrintWriter writer, List<User> users){
        writer.println("User ID, Email, Status, Expiration Date");
        for(User user : users){
            writer.println(user.getId()+","+user.getEmail()+","+user.getStatus()+","+user.getActivationExpiresAt());
        }
    }
}
