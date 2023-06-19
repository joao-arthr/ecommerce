package br.com.ecommerce.controllers;

public class CartProduct {
    private int id;
    private Cart cart;
    private Product product;
    private int QuantityInCart;

    public CartProduct() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInCart() {
        return QuantityInCart;
    }

    public void setQuantityInCart(int QuantityInCart) {
        this.QuantityInCart = QuantityInCart;
    }
}
