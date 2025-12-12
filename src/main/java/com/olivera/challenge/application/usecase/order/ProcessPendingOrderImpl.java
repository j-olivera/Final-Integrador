package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;
import java.util.List;
//casos de uso Schedulder sin Anotaciones
public class ProcessPendingOrderImpl implements ProcessPendingOrders {

    private final OrderRepositoryPort orderRepositoryPort;
    private final TimeProvider  timeProvider;

    public ProcessPendingOrderImpl(OrderRepositoryPort orderRepositoryPort, TimeProvider timeProvider) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.timeProvider = timeProvider;
    }

    @Override
    public void execute() {
        LocalDateTime now = timeProvider.now();
        //pending a proccesing
        List<Order> procces = orderRepositoryPort.findByStatus(OrderStatus.PROCESSING);
        for (Order order : procces) {
            if(order.getUser().isActive()){
                order.procces(now);
                orderRepositoryPort.save(order);//se guarda, no se crea, es una orden ya cargada en la bd
            }else if(order.getUser().isExpired()){
                order.cancel(now);
                orderRepositoryPort.save(order);//no se si se elimina, pero por las dudas la guardo
            }
        }
        //repetir

        //proccesing a approved
        List<Order> approved = orderRepositoryPort.findByStatus(OrderStatus.APPROVED);
        for (Order order : approved) {
            if(order.getUser().isActive()){
                order.approve(now);
                orderRepositoryPort.save(order);
            }else if(order.getUser().isExpired()){
                order.cancel(now);
                orderRepositoryPort.save(order);
            }
        }

        //
    }
}
