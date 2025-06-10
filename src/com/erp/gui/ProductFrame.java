package com.erp.gui;

import com.erp.dao.ProductDAO;
import com.erp.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductFrame extends JFrame {

    private JTable productTable;
    private JComboBox<String> categoryFilter;
    private ProductDAO productDAO;

    public ProductFrame() {
        setTitle("Liste des produits");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        productDAO = new ProductDAO();

        // Table
        productTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Filtre par catégorie
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Filtrer par catégorie :"));

        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("Toutes");

        for (String cat : productDAO.getAllCategories()) {
            categoryFilter.addItem(cat);
        }

        categoryFilter.addActionListener(e -> loadProducts());

        topPanel.add(categoryFilter);
        add(topPanel, BorderLayout.NORTH);

        // Charger les produits
        loadProducts();
    }

    private void loadProducts() {
        List<Product> products;

        String selectedCategory = (String) categoryFilter.getSelectedItem();
        if (selectedCategory != null && !selectedCategory.equals("Toutes")) {
            products = productDAO.getProductsByCategory(selectedCategory);
        } else {
            products = productDAO.getAllProducts();
        }

        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nom", "Prix", "Catégorie"}, 0);

        for (Product p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getUnitPrice(), p.getCategory()});
        }

        productTable.setModel(model);
    }
}
