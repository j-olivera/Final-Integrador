package com.olivera.challenge.infrastructure.time;

import com.olivera.challenge.application.services.TimeProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class SystemTimeProvider implements TimeProvider {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
