package com.example.cafe_api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cafe_api.model.Product;

@RestController
@RequestMapping("/products")
public class ProductController {

    // 🔹 Lista en memoria para simular la base de datos
    private List<Product> productos = new ArrayList<>();

    // Constructor: cargamos algunos productos iniciales
    public ProductController() {
        productos.add(new Product(1, "Espresso", 2.50));
        productos.add(new Product(2, "Cappuccino", 3.00));
        productos.add(new Product(3, "Latte", 3.50));
    }

    // GET → listar todos los productos
    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productos);
    }

    // POST → agregar un nuevo producto
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product nuevo) {
        for (Product p : productos) {
            if (p.getId() == nuevo.getId()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST) // 400
                        .body(Map.of(
                                "status", 400,
                                "error", "Producto con ID " + nuevo.getId() + " ya existe."));
            }
        }

        productos.add(nuevo);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201
                .body(Map.of(
                        "status", 201,
                        "message", "Producto creado correctamente",
                        "producto", nuevo));
    }

    // PUT → actualizar precio del producto
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Map<String, Object> body) {
        for (Product p : productos) {
            if (p.getId() == id) {
                try {
                    double nuevoPrecio = Double.parseDouble(body.get("precio").toString());
                    p.setPrecio(nuevoPrecio);
                    return ResponseEntity.ok(Map.of(
                            "status", 200,
                            "message", "Producto con ID " + id + " actualizado.",
                            "producto", p));
                } catch (Exception e) {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(Map.of(
                                    "status", 400,
                                    "error", "El cuerpo de la petición debe contener un campo 'precio' numérico."));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", 404,
                "error", "Producto con ID " + id + " no encontrado."));
    }

    // DELETE → eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        for (Product p : productos) {
            if (p.getId() == id) {
                productos.remove(p);
                return ResponseEntity.ok(Map.of(
                        "status", 200,
                        "message", "Producto con ID " + id + " eliminado."));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "status", 404,
                "error", "Producto con ID " + id + " no encontrado."));
    }
}
