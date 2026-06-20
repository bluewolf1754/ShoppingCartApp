package com.shop.model;

// ENCAPSULATION: Wraps a Product with a quantity for the cart
public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return product.getFinalPrice() * quantity;
    }

    // Getters & Setters
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("%s x%d = $%.2f", product.getName(), quantity, getSubtotal());
    }
}
