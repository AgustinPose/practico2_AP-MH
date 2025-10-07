package com.example.cafe_api.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {
    private final MeterRegistry registry;

    public OrderMetrics(MeterRegistry registry) {
        this.registry = registry;
    }

    public void contarOrden(String producto) {
        Counter.builder("cafeteria_ordenes_total")
                .description("Cantidad total de órdenes por producto")
                .tag("producto", producto)
                .register(registry)
                .increment();
    }
}
