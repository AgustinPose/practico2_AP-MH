package com.example.cafe_api.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private LocalDateTime fecha;
    private List<OrderItem> items;

    public Order() {}

    public Order(int id, LocalDateTime fecha, List<OrderItem> items) {
        this.id = id;
        this.fecha = fecha;
        this.items = items;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
