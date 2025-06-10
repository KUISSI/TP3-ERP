package com.erp.gui;

import com.erp.dao.OrderDAO;
import com.erp.dao.CustomerDAO;
import com.erp.model.Customer;
import com.erp.model.OrderLine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HistoriqueCommandesFrame extends JFrame {

    private JComboBox<Customer> customerBox;
    private JTable historyTable;

    public HistoriqueCommandesFrame() {
        setTitle("Historique Commandes Client");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top – Sélection client
        JPanel topPanel = new JPanel();
        customerBox = new JComboBox<>();
        for (Customer c : new CustomerDAO().getAllCustomers()) {
            customerBox.addItem(c);
        }
        JButton searchButton = new JButton("Afficher les commandes");
        searchButton.addActionListener(e -> loadOrders());
        topPanel.add(new JLabel("Client :"));
        topPanel.add(customerBox);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);

        // Center – Tableau des commandes
        historyTable = new JTable();
        add(new JScrollPane(historyTable), BorderLayout.CENTER);
    }

    private void loadOrders() {
        Customer selected = (Customer) customerBox.getSelectedItem();
        if (selected == null) return;

        List<OrderLine> lines = new OrderDAO().getOrderLinesByCustomerId(selected.getCustomerId());

        DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Commande ID", "Date", "Produit", "Quantité", "PU", "Montant ligne"}, 0);

        for (OrderLine line : lines) {
            model.addRow(new Object[]{
                line.getOrderId(),
                line.getOrderDate(),
                line.getProductName(),
                line.getQuantity(),
                line.getUnitPrice(),
                line.getQuantity() * line.getUnitPrice()
            });
        }

        historyTable.setModel(model);
    }

}

