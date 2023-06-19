package br.com.ecommerce.controllers;

import java.math.BigDecimal;
import java.util.List;

public class Cart {
    private int id;
    private boolean paid;
    private List<CartProduct> products;
    private BigDecimal totalPrice;

    public Cart() {
        this.totalPrice = setTotalPrice();
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

    public List<CartProduct> getCartProducts() {
        return products;
    }

    public void setCartProducts(List<CartProduct> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal setTotalPrice() {
        BigDecimal totalValue = BigDecimal.ZERO;
        if(products != null){
            for (CartProduct cartProduct : products) {
                var p = cartProduct.getProduct();
                BigDecimal productValue = p.getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantityInCart()));
                totalValue = totalValue.add(productValue);
            }
        }
        return totalValue;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", paid=" + paid +
                '}';
    }
}
