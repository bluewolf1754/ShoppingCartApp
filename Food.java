package com.shop.model;

// INHERITANCE: Food extends Product
public class Food extends Product {

    private String expiryDate;
    private boolean isOrganic;

    public Food(String name, double price, int stock, String expiryDate, boolean isOrganic) {
        super(name, price, stock, "🍎");
        this.expiryDate = expiryDate;
        this.isOrganic = isOrganic;
    }

    // POLYMORPHISM: Food gets no discount unless organic
    @Override
    public double getDiscount() {
        return isOrganic ? 0.03 : 0.0;
    }

    @Override
    public String getCategory() {
        return "Food";
    }

    public String getExpiryDate() { return expiryDate; }
    public boolean isOrganic() { return isOrganic; }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Expiry: %s | Organic: %s", expiryDate, isOrganic ? "Yes" : "No");
    }
}
