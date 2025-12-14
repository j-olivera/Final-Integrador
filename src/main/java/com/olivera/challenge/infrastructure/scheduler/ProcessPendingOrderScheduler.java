package com.olivera.challenge.infrastructure.scheduler;

import com.olivera.challenge.application.port.in.order.ProcessPendingOrders;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ProcessPendingOrderScheduler {
    private static final Logger log = LoggerFactory.getLogger(ProcessPendingOrderScheduler.class);
    private final ProcessPendingOrders useCase;

    public ProcessPendingOrderScheduler(ProcessPendingOrders useCase) {
        this.useCase = useCase;
    }

    @Scheduled(fixedDelay = 300000)
    public void processPendingOrder() {
        log.info("Processing Order");
        try{
            useCase.execute();
            log.info("Processing Order Complete");
            //podría poner un informe de ordenes procesadas y/o aprovadas y eso
        }catch(Exception e){
            log.error("Processing Order Failed");
        }
    }
}
/*
    scheduler solo llama al puerto in
    luego se conecta en BeanConfiguration
    NO TIENE LOGICA
 */