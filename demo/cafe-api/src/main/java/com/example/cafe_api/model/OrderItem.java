package com.example.cafe_api.model;

public class OrderItem {
    private int productId;
    private int cantidad;

    public OrderItem() {}

    public OrderItem(int productId, int cantidad) {
        this.productId = productId;
        this.cantidad = cantidad;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
