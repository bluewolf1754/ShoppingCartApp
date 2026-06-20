package com.shop.model;

// INHERITANCE: Clothing extends Product
public class Clothing extends Product {

    private String size;
    private String color;

    public Clothing(String name, double price, int stock, String size, String color) {
        super(name, price, stock, "👕");
        this.size = size;
        this.color = color;
    }

    // POLYMORPHISM: Clothing gets a 10% discount
    @Override
    public double getDiscount() {
        return 0.10;
    }

    @Override
    public String getCategory() {
        return "Clothing";
    }

    public String getSize() { return size; }
    public String getColor() { return color; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Size: %s | Color: %s", size, color);
    }
}
