package com.example.cafe_api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cafe_api.model.Order;
import com.example.cafe_api.model.OrderItem;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private List<Order> ordenes = new ArrayList<>();
    private int nextId = 1;

    // GET → listar todas las órdenes
    @GetMapping
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(ordenes);
    }

    // POST → crear una nueva orden
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        try {
            // Extraer lista de items del body
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> itemsJson = (List<Map<String, Object>>) body.get("items");

            List<OrderItem> items = new ArrayList<>();
            for (Map<String, Object> item : itemsJson) {
                int productId = (int) item.get("productId");
                int cantidad = (int) item.get("cantidad");
                items.add(new OrderItem(productId, cantidad));
            }

            Order nueva = new Order(nextId++, LocalDateTime.now(), items);
            ordenes.add(nueva);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "status", 201,
                            "message", "Orden creada correctamente",
                            "orden", nueva));

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "status", 400,
                            "error", "Formato de orden inválido: " + e.getMessage()));
        }
    }
}
