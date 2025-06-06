import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

public class AddCustomerForm extends JFrame {
    private JTextField txtFirstname, txtLastname, txtPhone, txtEmail;
    private JButton btnAdd;
    private CustomerDAO dao;
    private CustomerViewer parentViewer;

    public AddCustomerForm(CustomerViewer viewer) {
        this.parentViewer = viewer;

        try {
            dao = new CustomerDAO(); // Connexion DAO
        } catch (SQLException e) {
            showError("Erreur de connexion à la base de données : " + e.getMessage());
            dispose();
            return;
        }

        setTitle("Ajout d'un nouveau client");
        setSize(400, 250);
        setLayout(new GridLayout(5, 2, 10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        add(new JLabel("Prénom :"));
        txtFirstname = new JTextField();
        add(txtFirstname);

        add(new JLabel("Nom :"));
        txtLastname = new JTextField();
        add(txtLastname);

        add(new JLabel("Téléphone :"));
        txtPhone = new JTextField();
        add(txtPhone);

        add(new JLabel("Email :"));
        txtEmail = new JTextField();
        add(txtEmail);

        btnAdd = new JButton("Ajouter");
        add(btnAdd);
        add(new JLabel("")); // Pour alignement

        btnAdd.addActionListener(e -> handleAddCustomer());
    }

    private void handleAddCustomer() {
        try {
            String firstname = validateField(txtFirstname, "Prénom");
            String lastname = validateField(txtLastname, "Nom");
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();

            boolean inserted = dao.addCustomer(firstname, lastname, phone, email);
            if (inserted) {
                JOptionPane.showMessageDialog(this, "Client ajouté avec succès !");
                clearFields();

                if (parentViewer != null) {
                    parentViewer.refreshCustomerList();
                }
                dispose();
            } else {
                showError("L'ajout du client a échoué.");
            }
        } catch (IllegalArgumentException ex) {
            showError("Saisie invalide : " + ex.getMessage());
        } catch (SQLException ex) {
            showError("Erreur BD : " + ex.getMessage());
        }
    }

    private String validateField(JTextField field, String name) {
        String val = field.getText().trim();
        if (val.isEmpty()) throw new IllegalArgumentException(name + " est obligatoire.");
        return val;
    }

    private void clearFields() {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}
