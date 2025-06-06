import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ProductViewer extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnLoad, btnAdd, btnDelete;
    private ProductDAO dao;

    public ProductViewer() {
        dao = new ProductDAO();

        setTitle("Gestion Produits - Mini ERP");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nom", "Prix", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Boutons
        JPanel panelButtons = new JPanel();
        btnLoad = new JButton("Charger produits");
        btnAdd = new JButton("Ajouter produit");
        btnDelete = new JButton("Supprimer produit");
        panelButtons.add(btnLoad);
        panelButtons.add(btnAdd);
        panelButtons.add(btnDelete);
        add(panelButtons, BorderLayout.SOUTH);

        // Listeners
        btnLoad.addActionListener(e -> loadProducts());
        btnAdd.addActionListener(e -> openAddProductForm());
        btnDelete.addActionListener(e -> deleteSelectedProduct());

        setLocationRelativeTo(null);
    }

    private void loadProducts() {
        List<Product> list = dao.getAllProducts();
        tableModel.setRowCount(0);
        for (Product p : list) {
            tableModel.addRow(new Object[]{p.getId(), p.getName(), p.getPrice(), p.getStock()});
        }
    }

    private void openAddProductForm() {
        AddProductForm form = new AddProductForm(this);
        form.setVisible(true);
    }

    public void refreshProductList() {
        loadProducts();
    }

    private void deleteSelectedProduct() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un produit à supprimer.");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Supprimer le produit ID " + id + " ?", "Confirmer", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteProduct(id)) {
                JOptionPane.showMessageDialog(this, "Produit supprimé.");
                refreshProductList();
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la suppression.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductViewer().setVisible(true);
        });
    }
}
