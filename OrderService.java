package com.shop.service;

import com.shop.model.CartItem;
import com.shop.model.Customer;
import com.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private List<Order> orders = new ArrayList<>();

    public Order placeOrder(Customer customer, CartService cartService) {
        if (cartService.isEmpty()) {
            throw new IllegalStateException("Cart is empty. Cannot place order.");
        }

        // Deduct stock for each item
        for (CartItem item : cartService.getCartItems()) {
            int newStock = item.getProduct().getStock() - item.getQuantity();
            item.getProduct().setStock(newStock);
        }

        Order order = new Order(customer, cartService.getCartItems(), cartService.getTotal());
        orders.add(order);
        cartService.clearCart();
        return order;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orders);
    }
}
