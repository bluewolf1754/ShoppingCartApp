package com.shop.ui;

import com.shop.model.CartItem;
import com.shop.model.Order;
import com.shop.model.Product;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.ProductService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {
    private static final Color PAGE_BG = new Color(242, 246, 250);
    private static final Color HEADER_BG = new Color(24, 72, 92);
    private static final Color PANEL_BG = Color.WHITE;
    private static final Color ACCENT = new Color(23, 129, 151);
    private static final Color DANGER = new Color(196, 77, 88);

    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    private final DefaultListModel<Product> productListModel;
    private final DefaultListModel<CartItem> cartListModel;
    private final JList<Product> productList;
    private final JList<CartItem> cartList;
    private final JSpinner quantitySpinner;
    private final JLabel totalLabel;
    private final JTextArea orderHistoryArea;

    public MainWindow() {
        productService = new ProductService();
        cartService = new CartService();
        orderService = new OrderService();

        productListModel = new DefaultListModel<>();
        cartListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        cartList = new JList<>(cartListModel);
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        totalLabel = new JLabel("Total: $0.00");
        orderHistoryArea = new JTextArea();

        configureWindow();
        buildLayout();
        loadProducts(productService.getAllProducts());
        refreshCart();
    }

    private void configureWindow() {
        setTitle("Shopping Cart - Java OOP Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1120, 720);
        setMinimumSize(new Dimension(980, 620));
        setLocationRelativeTo(null);
        getContentPane().setBackground(PAGE_BG);
    }

    private void buildLayout() {
        setLayout(new BorderLayout(14, 14));
        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private Component createHeader() {
        JLabel title = new JLabel("Shopping Cart", SwingConstants.CENTER);
        title.setOpaque(true);
        title.setBackground(HEADER_BG);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(20, 8, 20, 8));
        return title;
    }

    private Component createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(PAGE_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(14, 18, 18, 18));
        panel.add(createProductsPanel(), BorderLayout.CENTER);
        panel.add(createCartPanel(), BorderLayout.EAST);
        panel.add(createOrdersPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private Component createProductsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 223, 232)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        productList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productList.setCellRenderer(new ProductRenderer());
        productList.setFixedCellHeight(74);
        productList.setBackground(new Color(250, 252, 254));

        JLabel heading = new JLabel("Available Products");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(27, 53, 69));

        JPanel addPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        addPanel.setBackground(PANEL_BG);
        addPanel.add(new JLabel("Quantity:"));
        addPanel.add(quantitySpinner);

        JButton addButton = createButton("Add to Cart", ACCENT);
        addButton.addActionListener(event -> addSelectedProduct());
        addPanel.add(addButton);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(productList), BorderLayout.CENTER);
        panel.add(addPanel, BorderLayout.SOUTH);
        return panel;
    }

    private Component createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(360, 0));
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 223, 232)),
                BorderFactory.createEmptyBorder(14, 14, 14, 14)));

        JLabel heading = new JLabel("Your Cart");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setForeground(new Color(27, 53, 69));

        cartList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartList.setFixedCellHeight(56);
        cartList.setBackground(new Color(250, 252, 254));

        JPanel buttons = new JPanel(new GridLayout(5, 1, 8, 8));
        buttons.setBackground(PANEL_BG);

        JButton updateButton = createButton("Update Quantity", new Color(85, 113, 139));
        updateButton.addActionListener(event -> updateSelectedCartItem());

        JButton removeButton = createButton("Remove Item", DANGER);
        removeButton.addActionListener(event -> removeSelectedCartItem());

        JButton clearButton = createButton("Clear Cart", new Color(104, 110, 118));
        clearButton.addActionListener(event -> {
            cartService.clearCart();
            refreshCart();
        });

        JButton checkoutButton = createButton("Checkout", ACCENT);
        checkoutButton.addActionListener(event -> checkout());

        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setForeground(new Color(20, 90, 105));

        buttons.add(totalLabel);
        buttons.add(updateButton);
        buttons.add(removeButton);
        buttons.add(clearButton);
        buttons.add(checkoutButton);

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(cartList), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);
        return panel;
    }

    private Component createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setPreferredSize(new Dimension(0, 150));
        panel.setBackground(PANEL_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(211, 223, 232)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        JLabel heading = new JLabel("Order History");
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setForeground(new Color(27, 53, 69));

        orderHistoryArea.setEditable(false);
        orderHistoryArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        orderHistoryArea.setText("No orders yet.");
        orderHistoryArea.setBackground(new Color(250, 252, 254));

        panel.add(heading, BorderLayout.NORTH);
        panel.add(new JScrollPane(orderHistoryArea), BorderLayout.CENTER);
        return panel;
    }

    private void loadProducts(List<Product> products) {
        productListModel.clear();
        for (Product product : products) {
            productListModel.addElement(product);
        }
    }

    private void addSelectedProduct() {
        Product product = productList.getSelectedValue();
        if (product == null) {
            showMessage("Please select a product first.");
            return;
        }

        int quantity = (Integer) quantitySpinner.getValue();
        try {
            cartService.addToCart(product, quantity);
            refreshCart();
        } catch (IllegalArgumentException exception) {
            showMessage(exception.getMessage());
        }
    }

    private void updateSelectedCartItem() {
        CartItem item = cartList.getSelectedValue();
        if (item == null) {
            showMessage("Please select a cart item first.");
            return;
        }

        int quantity = (Integer) quantitySpinner.getValue();
        if (quantity > item.getProduct().getStock()) {
            showMessage("Quantity cannot be greater than available stock.");
            return;
        }

        cartService.updateQuantity(item.getProduct(), quantity);
        refreshCart();
    }

    private void removeSelectedCartItem() {
        CartItem item = cartList.getSelectedValue();
        if (item == null) {
            showMessage("Please select a cart item first.");
            return;
        }

        cartService.removeFromCart(item.getProduct());
        refreshCart();
    }

    private void checkout() {
        if (cartService.isEmpty()) {
            showMessage("Cart is empty. Add products before checkout.");
            return;
        }

        Order order = CheckoutDialog.showCheckoutDialog(this, cartService, orderService);
        if (order != null) {
            refreshCart();
            loadProducts(productService.getAllProducts());
            refreshOrderHistory();
            showMessage("Order placed successfully. Order ID: " + order.getOrderId());
        }
    }

    private void refreshCart() {
        cartListModel.clear();
        for (CartItem item : cartService.getCartItems()) {
            cartListModel.addElement(item);
        }
        totalLabel.setText(String.format("Total: $%.2f", cartService.getTotal()));
    }

    private void refreshOrderHistory() {
        StringBuilder builder = new StringBuilder();
        for (Order order : orderService.getOrderHistory()) {
            builder.append(order).append(System.lineSeparator());
            for (CartItem item : order.getItems()) {
                builder.append("  - ").append(item).append(System.lineSeparator());
            }
            builder.append(System.lineSeparator());
        }
        orderHistoryArea.setText(builder.length() == 0 ? "No orders yet." : builder.toString());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private static class ProductRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Product product = (Product) value;
            label.setText(String.format("<html><b>%s</b><br>%s | Price: $%.2f | Stock: %d | Discount: %.0f%%</html>",
                    product.getName(),
                    product.getCategory(),
                    product.getFinalPrice(),
                    product.getStock(),
                    product.getDiscount() * 100));
            label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            return label;
        }
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        return button;
    }
}
