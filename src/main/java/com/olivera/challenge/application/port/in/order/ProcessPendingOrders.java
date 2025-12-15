package com.olivera.challenge.application.port.in.order;

import java.util.List;

public interface ProcessPendingOrders {
    List<Integer> execute();
}
