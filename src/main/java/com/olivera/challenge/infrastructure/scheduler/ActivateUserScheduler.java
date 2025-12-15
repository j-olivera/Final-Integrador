package com.olivera.challenge.infrastructure.scheduler;

import com.olivera.challenge.application.port.in.user.ActivateUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActivateUserScheduler {
    private static final Logger log = LoggerFactory.getLogger(ActivateUserScheduler.class);
    private final ActivateUser activateUser;

    public ActivateUserScheduler(ActivateUser activateUser) {
        this.activateUser = activateUser;
    }

    @Scheduled(fixedRate = 300000)
    public void execute(){
        int cant=activateUser.execute();
        try {
            if(cant==0){
                log.info("No users to be activated");
            }else{
                log.info("{} Users activated", cant);
            }
        }catch (Exception e){
            log.error("Error while activating users",e);
        }
    }

}
