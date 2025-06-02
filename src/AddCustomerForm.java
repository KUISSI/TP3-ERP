import java.awt.*;
import javax.swing.*;

public class AddCustomerForm extends JFrame {
    private JTextField txtFirstname, txtLastname, txtPhone, txtEmail;
    private JButton btnAdd;
    private final CustomerDAO dao;

    public AddCustomerForm() {
        // Initialisation du DAO
        dao = new CustomerDAO();
        
        // Configuration de la fenêtre
        setTitle("Ajouter un client");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10)); // Ajout de marges
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Création et ajout des composants
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
        add(new JLabel("")); // Espace vide pour équilibrer la grille

        // Gestionnaire d'événement pour le bouton
        btnAdd.addActionListener(e -> {
            try {
                String prenom = txtFirstname.getText().trim();
                String nom = txtLastname.getText().trim();
                String telephone = txtPhone.getText().trim();
                String email = txtEmail.getText().trim();
                
                System.out.println("Tentative d'ajout: " + prenom + " " + nom);

                if (prenom.isEmpty() || nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Prénom et nom sont obligatoires.",
                        "Erreur de saisie",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean succes = dao.addCustomer(prenom, nom, telephone, email);

                if (succes) {
                    JOptionPane.showMessageDialog(this, 
                        "Client ajouté avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    throw new Exception("Échec de l'ajout dans la base de données");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Erreur lors de l'ajout du client : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Centrer la fenêtre
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Create and show form
                AddCustomerForm form = new AddCustomerForm();
                form.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Error starting application: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}