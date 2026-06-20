package com.shop.ui;

import com.shop.model.Customer;
import com.shop.model.Order;
import com.shop.service.CartService;
import com.shop.service.OrderService;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckoutDialog {
    private CheckoutDialog() {
    }

    public static Order showCheckoutDialog(Component parent, CartService cartService, OrderService orderService) {
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Contact Number:"));
        panel.add(contactField);

        JLabel totalLabel = new JLabel(String.format("Total Amount: $%.2f", cartService.getTotal()));
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(totalLabel);

        int result = JOptionPane.showConfirmDialog(
                parent,
                panel,
                "Checkout",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return null;
        }

        String name = nameField.getText().trim();
        String contactNumber = contactField.getText().trim();

        if (name.isEmpty() || contactNumber.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Please enter customer name and contact number.");
            return null;
        }

        Customer customer = new Customer(name, contactNumber);
        return orderService.placeOrder(customer, cartService);
    }
}
