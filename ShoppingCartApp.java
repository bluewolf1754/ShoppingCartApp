package com.shop;

import com.shop.ui.MainWindow;
import javax.swing.SwingUtilities;

public class ShoppingCartApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setVisible(true);
        });
    }
}
