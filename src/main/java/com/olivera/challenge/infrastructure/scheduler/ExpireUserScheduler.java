package com.olivera.challenge.infrastructure.scheduler;

import com.olivera.challenge.application.port.in.user.ExpireUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpireUserScheduler {
    private static final Logger log = LoggerFactory.getLogger(ExpireUserScheduler.class);
    private final ExpireUser expireUser;

    public ExpireUserScheduler(ExpireUser expireUser) {
        this.expireUser = expireUser;
    }

    @Scheduled(fixedRate = 60000)
    public void expireUser() {
        int exp=expireUser.expire();
        if(exp==0){
            log.info("No users to be expired");
        }else{
            log.info("{} Users expired", exp);
        }
    }
}
