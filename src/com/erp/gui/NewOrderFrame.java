package com.erp.gui;

import com.erp.dao.CustomerDAO;
import com.erp.dao.ProductDAO;
import com.erp.dao.OrderDAO;
import com.erp.model.Customer;
import com.erp.model.Order;
import com.erp.model.OrderLine;
import com.erp.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewOrderFrame extends JFrame {
    private JComboBox<Customer> customerBox;
    private JTable productTable;
    private JButton validateButton;

    public NewOrderFrame() {
        setTitle("Nouvelle Commande");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel – Sélection client
        JPanel topPanel = new JPanel(new FlowLayout());
        customerBox = new JComboBox<>();
        loadCustomers();
        topPanel.add(new JLabel("Client :"));
        topPanel.add(customerBox);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel – Table des produits
        productTable = new JTable();
        loadProducts();
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // Bottom Panel – Bouton validation
        JPanel bottomPanel = new JPanel();
        validateButton = new JButton("Valider la commande");
        validateButton.addActionListener(e -> submitOrder());
        bottomPanel.add(validateButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCustomers() {
        List<Customer> customers = new CustomerDAO().getAllCustomers();
        for (Customer c : customers) {
            customerBox.addItem(c);
        }
    }

    private void loadProducts() {
        List<Product> products = new ProductDAO().getAllProducts();

        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nom", "Prix unitaire", "Quantité"}, 0);
        for (Product p : products) {
            model.addRow(new Object[]{p.getId(), p.getName(), p.getUnitPrice(), 0});
        }
        productTable.setModel(model);
    }

    private void submitOrder() {
        Customer selectedCustomer = (Customer) customerBox.getSelectedItem();
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(this, "Veuillez choisir un client.");
            return;
        }

        List<OrderLine> lines = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        double net = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            int quantity = (int) model.getValueAt(i, 3);
            if (quantity > 0) {
                int productId = (int) model.getValueAt(i, 0);
                double unitPrice = (double) model.getValueAt(i, 2);
                OrderLine line = new OrderLine();
                line.setProductId(productId);
                line.setQuantity(quantity);
                line.setUnitPrice(unitPrice);
                lines.add(line);
                net += quantity * unitPrice;
            }
        }

        if (lines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez saisir au moins un produit.");
            return;
        }

        double tax = net * 0.2;
        double total = net + tax;

        Order order = new Order();
        order.setCustomerId(selectedCustomer.getCustomerId());
        order.setOrderDate(LocalDate.now());
        order.setNetAmount(net);
        order.setTax(tax);
        order.setTotalAmount(total);
        order.setLines(lines);

        boolean success = new OrderDAO().createOrder(order);
        if (success) {
            JOptionPane.showMessageDialog(this, "Commande enregistrée avec succès !");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création de la commande.");
        }

        ProductDAO productDAO = new ProductDAO();

        for (OrderLine line : lines) {
            boolean updated = productDAO.updateStock(line.getProductId(), line.getQuantity());
            if (!updated) {
                JOptionPane.showMessageDialog(this, "Stock insuffisant pour le produit ID " + line.getProductId());
                return;
            }
        }
    }
}