package com.shop.model;

// ABSTRACTION: Abstract base class — cannot be instantiated directly
public abstract class Product {

    private static int idCounter = 1;

    private int id;
    private String name;
    private double price;
    private int stock;
    private String imageEmoji; // used in UI for visual flair

    public Product(String name, double price, int stock, String imageEmoji) {
        this.id = idCounter++;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageEmoji = imageEmoji;
    }

    // ABSTRACTION: Subclasses must define their category
    public abstract String getCategory();

    // ABSTRACTION: Subclasses can define a discount rule
    public abstract double getDiscount();

    // Returns price after discount
    public double getFinalPrice() {
        return price - (price * getDiscount());
    }

    // --- Getters & Setters (ENCAPSULATION) ---
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public String getImageEmoji() { return imageEmoji; }

    @Override
    public String toString() {
        return String.format("[%s] %s - $%.2f (Stock: %d)", getCategory(), name, getFinalPrice(), stock);
    }
}
