/*
 * Squelette de projet Java Mini ERP
 * Technologies : Java, Swing, JDBC, PostgreSQL
 * Architecture : DAO + Modèle + Interface Swing
 */

// --- Modèle ---

public class Customer {
    private int customerId;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    // Ajouter les autres champs si besoin

    // Getters & Setters
    // Constructeurs
}

public class Product {
    private int productId;
    private String title;
    private String actor;
    private double price;
    private int category;

    // Getters & Setters
    // Constructeurs
}

public class Order {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private double netAmount;
    private double tax;
    private double totalAmount;

    // Getters & Setters
    // Constructeurs
}

// --- DAO ---

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your_password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

public class CustomerDAO {
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT customerid, firstname, lastname, email, username FROM customers")) {
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customerid"));
                c.setFirstname(rs.getString("firstname"));
                c.setLastname(rs.getString("lastname"));
                c.setEmail(rs.getString("email"));
                c.setUsername(rs.getString("username"));
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public int addCustomerUsingFunction(Customer c) {
        String sql = "SELECT * FROM new_customer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, c.getFirstname());
            stmt.setString(2, c.getLastname());
            stmt.setString(3, "Address1");
            stmt.setString(4, "Address2");
            stmt.setString(5, "City");
            stmt.setString(6, "State");
            stmt.setInt(7, 12345);
            stmt.setString(8, "Country");
            stmt.setInt(9, 1);
            stmt.setString(10, c.getEmail());
            stmt.setString(11, "0000000000");
            stmt.setInt(12, 1);
            stmt.setString(13, "4111111111111111");
            stmt.setString(14, "12/25");
            stmt.setString(15, c.getUsername());
            stmt.setString(16, "password");
            stmt.setInt(17, 30);
            stmt.setInt(18, 50000);
            stmt.setString(19, "M");

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

public int addCustomer(String firstName, String lastName, String phone, String email) {
    // ...existing code...
    if (rowsAffected > 0) {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                System.out.println("Client créé avec l'ID : " + id);
                return id;
            }
        }
    }
    return -1;
}


public class ProductDAO {
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT prod_id, title, actor, price, category FROM products")) {
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("prod_id"));
                p.setTitle(rs.getString("title"));
                p.setActor(rs.getString("actor"));
                p.setPrice(rs.getDouble("price"));
                p.setCategory(rs.getInt("category"));
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

// --- Interface Swing ---

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainFrame extends JFrame {
    private JTable customerTable;
    private JButton loadButton;

    public MainFrame() {
        setTitle("Mini ERP - Gestion Clients");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        loadButton = new JButton("Charger Clients");
        customerTable = new JTable();

        loadButton.addActionListener(e -> {
            CustomerDAO dao = new CustomerDAO();
            List<Customer> customers = dao.getAllCustomers();
            String[][] data = new String[customers.size()][3];
            for (int i = 0; i < customers.size(); i++) {
                Customer c = customers.get(i);
                data[i][0] = String.valueOf(c.getCustomerId());
                data[i][1] = c.getFirstname() + " " + c.getLastname();
                data[i][2] = c.getEmail();
            }
            String[] columns = {"ID", "Nom", "Email"};
            customerTable.setModel(new javax.swing.table.DefaultTableModel(data, columns));
        });

        add(loadButton, BorderLayout.NORTH);
        add(new JScrollPane(customerTable), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
