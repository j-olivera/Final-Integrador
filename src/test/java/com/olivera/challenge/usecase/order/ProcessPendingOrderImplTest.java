package com.olivera.challenge.usecase.order;

import com.olivera.challenge.application.port.out.OrderRepositoryPort;
import com.olivera.challenge.application.services.TimeProvider;
import com.olivera.challenge.application.usecase.order.ProcessPendingOrderImpl;
import com.olivera.challenge.domain.entities.Order;
import com.olivera.challenge.domain.entities.User;
import com.olivera.challenge.domain.enums.order.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProcessPendingOrderImplTest {
    @Mock
    OrderRepositoryPort orderRepositoryPort;
    @Mock
    TimeProvider  timeProvider;
    @InjectMocks
    ProcessPendingOrderImpl processPendingOrderImpl;

    @Test
    void processPendingOrdersSuccess() {
  }
}
