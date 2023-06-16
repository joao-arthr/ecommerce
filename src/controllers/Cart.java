package controllers;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private int id;
    private boolean paid;
    private List<Product> products;
    private BigDecimal totalPrice;

    public Cart(int id, boolean paid, List<Product> products, BigDecimal totalPrice) {
        this.id = id;
        this.paid = paid;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public List<Product> getProducts() {

        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
