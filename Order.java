package com.shop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {

    private static int orderCounter = 1000;

    private int orderId;
    private Customer customer;
    private List<CartItem> items;
    private double total;
    private String status;
    private String orderDate;

    public Order(Customer customer, List<CartItem> items, double total) {
        this.orderId = orderCounter++;
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.status = "Confirmed";
        this.orderDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"));
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrderDate() { return orderDate; }

    @Override
    public String toString() {
        return String.format("Order #%d | %s | %s | $%.2f | %s",
                orderId, orderDate, customer.toString(), total, status);
    }
}
