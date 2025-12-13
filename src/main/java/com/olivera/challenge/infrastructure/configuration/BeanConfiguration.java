package com.olivera.challenge.infrastructure.configuration;

import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    //PARA PRUEBA
    @Bean
    public ProcessPendingOrders processPendingOrders(OrderRepositoryPort orderRepositoryPort, TimeProvider timeProvider){
        return new ProcessPendingOrderImpl(orderRepositoryPort, timeProvider);
    }
}
