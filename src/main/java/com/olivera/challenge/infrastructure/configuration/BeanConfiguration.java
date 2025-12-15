package com.olivera.challenge.infrastructure.configuration;

import com.olivera.challenge.application.mappers.order.OrderMapper;
import com.olivera.challenge.application.mappers.user.UserMapper;
import com.olivera.challenge.application.port.in.order.CreateOrder;
import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.port.in.order.RetrieveAllOrders;
import com.olivera.challenge.application.port.in.user.ActivateUser;
import com.olivera.challenge.application.port.in.user.ExpireUser;
import com.olivera.challenge.application.port.in.user.RegisterUser;
import com.olivera.challenge.application.port.in.user.RetrieveAllUsers;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.port.out.UserRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.CreateOrderImpl;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import com.olivera.challenge.application.usecase.order.RetrieveAllOrdersImpl;
import com.olivera.challenge.application.usecase.user.ActivateUserImpl;
import com.olivera.challenge.application.usecase.user.ExpireUserImpl;
import com.olivera.challenge.application.usecase.user.RegisterUserImpl;
import com.olivera.challenge.application.usecase.user.RetrieveAllUsersImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    //mappers
    @Bean
    public UserMapper userMapper() {
        return new UserMapper();
    }
    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }
    //UseCase
    //User
    @Bean
    public RegisterUser registerUser(UserRepositoryPort userReportsitoryPort, TimeProvider timeProvider) {
        return new RegisterUserImpl(userReportsitoryPort, timeProvider);
    }
    @Bean
    public ActivateUser activateUser(UserRepositoryPort userReportsitoryPort, TimeProvider timeProvider) {
        return new ActivateUserImpl(userReportsitoryPort, timeProvider);
    }
    @Bean
    public ExpireUser expireUser (UserRepositoryPort userReportsitoryPort, TimeProvider timeProvider) {
        return new ExpireUserImpl(userReportsitoryPort, timeProvider);
    }
    @Bean
    public RetrieveAllUsers retrieveAllUsers(UserRepositoryPort userReportsitoryPort) {
        return new RetrieveAllUsersImpl(userReportsitoryPort);
    }
    //Order
    @Bean
    public ProcessPendingOrders processPendingOrders(OrderRepositoryPort orderRepositoryPort, TimeProvider timeProvider){
        return new ProcessPendingOrderImpl(orderRepositoryPort, timeProvider);
    }
    @Bean
    public CreateOrder createOrder(OrderRepositoryPort orderRepositoryPort, UserRepositoryPort userRepositoryPort, TimeProvider timeProvider){
        return new CreateOrderImpl(orderRepositoryPort, userRepositoryPort, timeProvider);
    }
    @Bean
    public RetrieveAllOrders retrieveAllOrders(OrderRepositoryPort orderRepositoryPort){
        return new RetrieveAllOrdersImpl(orderRepositoryPort);
    }
}
//completar BeanConfiguration