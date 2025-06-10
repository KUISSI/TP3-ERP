package com.erp;

import com.erp.gui.CustomerFrame;
import javax.swing.SwingUtilities;
import com.erp.gui.ProductFrame;
import com.erp.gui.NewOrderFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame menuFrame = new JFrame("Mini ERP - Menu");
            menuFrame.setSize(300, 200);
            menuFrame.setLocationRelativeTo(null);
            menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();

            JButton btnClients = new JButton("Gérer les clients");
            btnClients.addActionListener(e -> new CustomerFrame().setVisible(true));

            JButton btnProduits = new JButton("Visualiser les produits");
            btnProduits.addActionListener(e -> new ProductFrame().setVisible(true));

            JButton btnCommandes = new JButton("Créer une commande");
            btnCommandes.addActionListener(e -> new NewOrderFrame().setVisible(true));
            
            
            panel.add(btnCommandes);

            panel.add(btnClients);
            panel.add(btnProduits);

            menuFrame.add(panel);
            menuFrame.setVisible(true);
        });
    }
}