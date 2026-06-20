package com.shop.service;

import com.shop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductService {

    private List<Product> products = new ArrayList<>();

    public ProductService() {
        loadSampleProducts();
    }

    private void loadSampleProducts() {
        // Electronics
        products.add(new Electronics("Laptop Pro 15\"", 1299.99, 10, "Dell", 24));
        products.add(new Electronics("Wireless Headphones", 199.99, 25, "Sony", 12));
        products.add(new Electronics("Smartphone X12", 899.99, 15, "Samsung", 12));
        products.add(new Electronics("4K Monitor", 449.99, 8, "LG", 36));

        // Clothing
        products.add(new Clothing("Classic White Tee", 29.99, 50, "M", "White"));
        products.add(new Clothing("Slim Fit Jeans", 59.99, 30, "32", "Blue"));
        products.add(new Clothing("Hoodie Zip-Up", 49.99, 20, "L", "Black"));
        products.add(new Clothing("Running Shoes", 89.99, 15, "42", "Red"));

        // Food
        products.add(new Food("Organic Almonds", 12.99, 100, "2025-12-31", true));
        products.add(new Food("Whole Grain Bread", 4.99, 40, "2025-06-15", false));
        products.add(new Food("Green Tea Pack", 9.99, 60, "2026-01-01", true));
        products.add(new Food("Dark Chocolate", 6.99, 80, "2025-09-30", false));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> getByCategory(String category) {
        if (category.equals("All")) return getAllProducts();
        return products.stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Product> search(String query) {
        String lower = query.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower)
                        || p.getCategory().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
