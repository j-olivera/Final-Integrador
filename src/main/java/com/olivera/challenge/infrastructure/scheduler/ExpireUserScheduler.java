package com.olivera.challenge.infrastructure.scheduler;

import com.olivera.challenge.application.port.in.user.ExpireUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

public class ExpireUserScheduler {
    private static final Logger log = LoggerFactory.getLogger(ExpireUserScheduler.class);
    private final ExpireUser expireUser;

    public ExpireUserScheduler(ExpireUser expireUser) {
        this.expireUser = expireUser;
    }

    @Scheduled(fixedRate = 300000)
    public void expireUser() {
        log.info("Expiring users..");
        try{
            expireUser.expire();
            log.info("Users expired.");
        } catch (Exception e) {
            log.error("Error while expiring users..", e);
        }
    }
}
