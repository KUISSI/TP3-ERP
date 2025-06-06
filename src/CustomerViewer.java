import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnLoad, btnAdd, btnDelete;
    private CustomerDAO dao;

    public CustomerViewer() {
        try {
            dao = new CustomerDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Erreur connexion BD : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Gestion Clients - Mini ERP");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Initialisation JTable et modèle
        tableModel = new DefaultTableModel(new Object[]{"ID", "Prénom", "Nom", "Téléphone", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Désactive l'édition directe dans la table
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel boutons
        JPanel panelButtons = new JPanel();
        btnLoad = new JButton("Charger clients");
        btnAdd = new JButton("Ajouter client");
        btnDelete = new JButton("Supprimer client");
        panelButtons.add(btnLoad);
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        // Actions boutons
        btnLoad.addActionListener(e -> loadCustomers());
        btnAdd.addActionListener(e -> openAddCustomerForm());
        btnDelete.addActionListener(e -> deleteSelectedCustomer());

        setLocationRelativeTo(null);
    }

    private void loadCustomers() {
        try {
            List<Customer> clients = dao.getAllCustomers();
            tableModel.setRowCount(0); // vider le tableau
            for (Customer c : clients) {
                tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getFirstname(),
                    c.getLastname(),
                    c.getPhone(),
                    c.getEmail()
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Erreur chargement clients : " + ex.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddCustomerForm() {
        AddCustomerForm form = new AddCustomerForm(this);
        form.setVisible(true);
    }

    // Pour recharger la liste clients après ajout/suppression
    public void refreshCustomerList() {
        try {
            loadCustomers();
        } catch (Exception e) {
            // En théorie loadCustomers ne lance pas d'exception non capturée,
            // mais on garde ce catch pour robustesse
            JOptionPane.showMessageDialog(this,
                "Erreur lors du rafraîchissement de la liste : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Sélectionnez un client dans le tableau.",
                "Attention",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment supprimer le client avec l'ID " + id + " ?",
            "Confirmation suppression",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (dao.deleteCustomer(id)) {
                    JOptionPane.showMessageDialog(this,
                        "Client supprimé avec succès !");
                    refreshCustomerList();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Suppression échouée.",
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Erreur suppression client : " + ex.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Look and Feel natif
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new CustomerViewer().setVisible(true);
        });
    }
}
