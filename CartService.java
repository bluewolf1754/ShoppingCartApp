package com.shop.service;

import com.shop.model.CartItem;
import com.shop.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// SERVICE layer: handles all cart business logic (separation of concerns)
public class CartService {

    private List<CartItem> cartItems = new ArrayList<>();

    public void addToCart(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock for: " + product.getName());
        }

        // If product already in cart, increase quantity
        Optional<CartItem> existing = cartItems.stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst();

        if (existing.isPresent()) {
            int newQty = existing.get().getQuantity() + quantity;
            if (newQty > product.getStock()) {
                throw new IllegalArgumentException("Cannot add more than available stock.");
            }
            existing.get().setQuantity(newQty);
        } else {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    public void removeFromCart(Product product) {
        cartItems.removeIf(item -> item.getProduct().getId() == product.getId());
    }

    public void updateQuantity(Product product, int quantity) {
        if (quantity <= 0) {
            removeFromCart(product);
            return;
        }
        cartItems.stream()
                .filter(item -> item.getProduct().getId() == product.getId())
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotal() {
        return cartItems.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    public int getTotalItems() {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
