import javax.swing.*;
import java.awt.*;

public class AddProductForm extends JDialog {
    private JTextField tfName, tfPrice, tfStock;
    private JButton btnSave, btnCancel;
    private ProductDAO dao;
    private ProductViewer parent;

    public AddProductForm(ProductViewer parent) {
        super(parent, "Ajouter un produit", true);
        this.parent = parent;
        this.dao = new ProductDAO();

        setLayout(new GridLayout(4, 2, 10, 10));
        setSize(300, 200);

        add(new JLabel("Nom :"));
        tfName = new JTextField();
        add(tfName);

        add(new JLabel("Prix :"));
        tfPrice = new JTextField();
        add(tfPrice);

        add(new JLabel("Stock :"));
        tfStock = new JTextField();
        add(tfStock);

        btnSave = new JButton("Ajouter");
        btnCancel = new JButton("Annuler");
        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(e -> {
            try {
                String name = tfName.getText();
                double price = Double.parseDouble(tfPrice.getText());
                int stock = Integer.parseInt(tfStock.getText());

                Product p = new Product(0, name, price, stock);
                if (dao.insertProduct(p)) {
                    JOptionPane.showMessageDialog(this, "Produit ajouté !");
                    parent.refreshProductList();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur ajout produit.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Champs invalides.");
            }
        });

        btnCancel.addActionListener(e -> dispose());
        setLocationRelativeTo(parent);
    }
}
