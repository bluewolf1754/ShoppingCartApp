package com.shop.model;

// ENCAPSULATION: Customer data is private, accessed via methods
public class Customer {

    private String name;
    private String contactNumber;

    public Customer(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    @Override
    public String toString() {
        return String.format("Customer: %s | Contact: %s", name, contactNumber);
    }
}
