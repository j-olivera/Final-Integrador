package com.olivera.challenge.application.usecase.order;

import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.enums.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<Integer> execute() {
        LocalDateTime now = timeProvider.now();
        List<Integer> list = new ArrayList<>();
        Integer pendingOrders;
        Integer processingOrders=0;
        Integer approvedOrders=0;
        Integer rejectedOrders=0;
        //pending a proccesing
        List<Order> pending = orderRepositoryPort.findByStatus(OrderStatus.PENDING);
        pendingOrders = pending.size();
        for (Order order : pending) {
            if(order.getUser().isActive()){
                order.procces(now);
                processingOrders++;
                orderRepositoryPort.save(order);
            }else{
                order.cancel(now);
                rejectedOrders++;
                orderRepositoryPort.save(order);
            }
        }
        //PENDING -> CANCELLED (if UserStatus is EXPIRED)
        //proccesing a approved
        List<Order> procces = orderRepositoryPort.findByStatus(OrderStatus.PROCESSING);
        for (Order order : procces) {
            if(order.getUser().isActive()){
                order.approve(now);
                approvedOrders++;
                orderRepositoryPort.save(order);
            }
        }
        list.add(pendingOrders);
        list.add(processingOrders);
        list.add(approvedOrders);
        list.add(rejectedOrders);
        return list;
        //
    }
}
