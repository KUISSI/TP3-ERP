import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
   public boolean addCustomer(String firstName, String lastName, String phone, String email) {
    String sql = "INSERT INTO customers (firstname, lastname, phone, email, address1, city, state, country) " +
                 "VALUES (?, ?, ?, ?, 'Default Address', 'Default City', 'Default State', 'Default Country')";
    
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        
        System.out.println("Préparation de l'insertion pour : " + firstName + " " + lastName);
        
        stmt.setString(1, firstName.trim());
        stmt.setString(2, lastName.trim());
        stmt.setString(3, phone != null ? phone.trim() : null);
        stmt.setString(4, email != null ? email.trim() : null);
        
        int rowsAffected = stmt.executeUpdate();
        System.out.println("Nombre de lignes affectées : " + rowsAffected);
        
        if (rowsAffected > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    System.out.println("Client créé avec l'ID : " + id);
                    return true;
                }
            }
        }
        return false;
    } catch (SQLException e) {
        System.err.println("Erreur SQL détaillée : ");
        System.err.println("État SQL : " + e.getSQLState());
        System.err.println("Code d'erreur : " + e.getErrorCode());
        System.err.println("Message : " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

    public boolean deleteCustomer(int id) {
        if (id <= 0) {
            return false;
        }
        
        String sql = "DELETE FROM customers WHERE customerid = ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }

    private Customer extractCustomerFromResult(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new SQLException("ResultSet cannot be null");
        }
        
        return new Customer(
            rs.getInt("customerid"),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getString("address1"),
            rs.getString("city"),
            rs.getString("state"),
            rs.getString("country"),
            rs.getString("phone"),
            rs.getString("email")
        );
    }
}

public Customer getLastAddedCustomer() {
    String sql = "SELECT * FROM customers ORDER BY customerid DESC LIMIT 1";
    
    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        
        if (rs.next()) {
            return extractCustomerFromResult(rs);
        }
    } catch (SQLException e) {
        System.err.println("Error getting last customer: " + e.getMessage());
    }
    return null;
}