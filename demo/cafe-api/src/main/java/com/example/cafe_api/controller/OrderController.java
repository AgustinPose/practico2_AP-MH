package com.example.cafe_api.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cafe_api.metrics.OrderMetrics;
import com.example.cafe_api.model.Order;
import com.example.cafe_api.model.OrderItem;
import com.example.cafe_api.model.Product;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderMetrics orderMetrics;

    private List<Order> ordenes = new ArrayList<>();
    private int nextId = 1;

    @GetMapping
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(ordenes);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> body) {
        try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> itemsJson = (List<Map<String, Object>>) body.get("items");

            List<OrderItem> items = new ArrayList<>();
            double total = 0.0;

            for (Map<String, Object> item : itemsJson) {
                int productId = (int) item.get("productId");
                int cantidad = (int) item.get("cantidad");

                Product producto = ProductController.productos.stream()
                        .filter(p -> p.getId() == productId)
                        .findFirst()
                        .orElse(null);

                if (producto == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Map.of("status", 400, "error", "Producto con ID " + productId + " no existe."));
                }

                total += producto.getPrecio() * cantidad;
                orderMetrics.contarOrden(producto.getNombre());
                items.add(new OrderItem(productId, cantidad));
            }

            Order nueva = new Order(nextId++, LocalDateTime.now(), items, total);
            ordenes.add(nueva);

            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "status", 201,
                    "message", "Orden creada correctamente",
                    "orden", nueva));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", 400, "error", "Formato de orden inválido: " + e.getMessage()));
        }
    }

}
