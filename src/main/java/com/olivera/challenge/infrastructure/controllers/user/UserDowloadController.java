package com.olivera.challenge.infrastructure.controllers.user;

import com.olivera.challenge.application.port.in.user.RetrieveAllUsers;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.infrastructure.csv.CsvUserGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDowloadController {
    private final RetrieveAllUsers retrieveAllUsers;

    public UserDowloadController(RetrieveAllUsers retrieveAllUsers) {
        this.retrieveAllUsers = retrieveAllUsers;
    }

    @GetMapping
    public void exportUsersToCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
        List<User> users = retrieveAllUsers.execute();
        CsvUserGenerator.writeOrder(response.getWriter(),users);
    }
}
