package com.olivera.challenge.infrastructure.scheduler;

import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class ProcessPendingOrderScheduler {
    private static final Logger log = LoggerFactory.getLogger(ProcessPendingOrderScheduler.class);
    private final ProcessPendingOrders useCase;

    public ProcessPendingOrderScheduler(ProcessPendingOrders useCase) {
        this.useCase = useCase;
    }

    @Scheduled(fixedDelay = 60000)
    public void processPendingOrder() {
        List<Integer> list = useCase.execute();
        try{
            log.info("{} Orders pending",list.getFirst());
            log.info("{} Orders processed",list.get(1));
            log.info("{} Orders approved",list.get(2));
            log.info("{} Orders rejected",list.get(3));
        }catch(Exception e){
            log.info("Something went wrong");
        }
    }
}
