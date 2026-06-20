package com.shop.model;

// INHERITANCE: Electronics extends Product
public class Electronics extends Product {

    private String brand;
    private int warrantyMonths;

    public Electronics(String name, double price, int stock, String brand, int warrantyMonths) {
        super(name, price, stock, "🖥️");
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    // POLYMORPHISM: Electronics get a 5% discount
    @Override
    public double getDiscount() {
        return 0.05;
    }

    @Override
    public String getCategory() {
        return "Electronics";
    }

    public String getBrand() { return brand; }
    public int getWarrantyMonths() { return warrantyMonths; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Brand: %s | Warranty: %d mo", brand, warrantyMonths);
    }
}
