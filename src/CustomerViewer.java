import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerViewer extends JFrame {
    private JTable table;
    private JButton btnLoad;

    public CustomerViewer() {
        setTitle("Liste des clients");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Bouton de chargement
        btnLoad = new JButton("Charger clients");
        btnLoad.addActionListener(e -> loadCustomers());

        // Tableau vide au départ
        table = new JTable(new DefaultTableModel(new Object[]{"ID", "Prénom", "Nom"}, 0));

        add(btnLoad, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadCustomers() {
        CustomerDAO dao = new CustomerDAO();
        List<Customer> customers = dao.getAllCustomers();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Vide le tableau

        for (Customer c : customers) {
            model.addRow(new Object[]{c.getId(), c.getFirstname(), c.getLastname()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerViewer().setVisible(true);
        });
    }
}
