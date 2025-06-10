package com.erp.gui;

import com.erp.dao.CustomerDAO;
import com.erp.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

// ... imports

public class CustomerFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JButton loadButton;
    private CustomerDAO customerDAO;
    private JButton addButton;

    public CustomerFrame() {
        customerDAO = new CustomerDAO();

        setTitle("Liste des Clients");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = { "ID", "Prénom", "Nom", "Email", "Téléphone", "Ville" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(22);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));

        loadButton = new JButton("Charger clients");
        loadButton.addActionListener(e -> loadCustomers());

        addButton = new JButton("Ajouter Client");
        addButton.addActionListener(e -> showAddCustomerDialog());

        JPanel panel = new JPanel();
        panel.add(loadButton);
        panel.add(addButton);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    private void loadCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        tableModel.setRowCount(0);

        for (Customer c : customers) {
            tableModel.addRow(new Object[]{
                c.getCustomerId(),
                c.getFirstname(),
                c.getLastname(),
                c.getEmail(),
                c.getPhone(),
                c.getCity()
            });
        }
    }

    private void showAddCustomerDialog() {
    JTextField firstnameField = new JTextField(10);
    JTextField lastnameField = new JTextField(10);
    JTextField emailField = new JTextField(15);
    JTextField phoneField = new JTextField(15);
    JTextField cityField = new JTextField(15);

    JPanel dialogPanel = new JPanel(new GridLayout(0, 2, 5, 5));
    dialogPanel.add(new JLabel("Prénom:")); dialogPanel.add(firstnameField);
    dialogPanel.add(new JLabel("Nom:")); dialogPanel.add(lastnameField);
    dialogPanel.add(new JLabel("Email:")); dialogPanel.add(emailField);
    dialogPanel.add(new JLabel("Téléphone:")); dialogPanel.add(phoneField);
    dialogPanel.add(new JLabel("Ville:")); dialogPanel.add(cityField);

    int result = JOptionPane.showConfirmDialog(this, dialogPanel, "Ajouter un nouveau client", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        String firstname = firstnameField.getText().trim();
        String lastname = lastnameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String city = cityField.getText().trim();

        if (!firstname.isEmpty() && !lastname.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !city.isEmpty()) {
            boolean success = customerDAO.addCustomer(firstname, lastname, email, phone, city);
            if (success) {
                JOptionPane.showMessageDialog(this, "Client ajouté avec succès !");
                loadCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Attention", JOptionPane.WARNING_MESSAGE);
        }
    }
}
}