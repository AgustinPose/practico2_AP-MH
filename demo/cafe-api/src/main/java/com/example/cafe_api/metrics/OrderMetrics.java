package com.example.cafe_api.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class OrderMetrics {
    private final MeterRegistry registry;
    private final Counter ordersCreatedCounter;
    private final Counter ordersDeliveredCounter;

    public OrderMetrics(MeterRegistry registry) {
        this.registry = registry;
        this.ordersCreatedCounter = Counter.builder("coffee_orders_created_total")
                .description("Cantidad total de pedidos creados")
                .register(registry);
        this.ordersDeliveredCounter = Counter.builder("coffee_orders_delivered_total")
                .description("Cantidad total de pedidos entregados")
                .register(registry);
    }

    public void contarOrden(String producto) {
        registry.counter("cafeteria_ordenes_total", "producto", producto).increment();
    }

    public void incrementarPedidosCreados() {
        ordersCreatedCounter.increment();
    }

    public void incrementarPedidosEntregados() {
        ordersDeliveredCounter.increment();
    }
}
 