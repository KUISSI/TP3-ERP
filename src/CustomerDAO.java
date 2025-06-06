import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO() throws SQLException {
        conn = DatabaseManager.getInstance().getConnection();
    }

    public boolean addCustomer(String firstname, String lastname, String phone, String email, String address1, String city, String country, String region, String creditcardtype, String creditcard, String creditcardexpiration, String username, String password) throws SQLException {
        String sql = """
            INSERT INTO customers (firstname, lastname, phone, email,address1, city, country, region, creditcardtype, creditcard, creditcardexpiration, username, password) 
            VALUES (?, ?, ?, ?,'1', '1', '1', 1, 1, '1', '1', '1', '1', '1') 
            RETURNING customerid""";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, address1);
            pstmt.setString(6, city);
            pstmt.setString(7, country);
            pstmt.setString(8, region);
            pstmt.setString(9, creditcardtype);
            pstmt.setString(10, creditcard);
            pstmt.setString(11, creditcardexpiration);
            pstmt.setString(12, username);
            pstmt.setString(13, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Client ajouté avec l'ID: " + rs.getInt("customerid"));
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du client: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customerid, firstname, lastname, phone, email FROM customers ORDER BY customerid";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customerid"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des clients: " + e.getMessage());
            throw e;
        }
        return customers;
    }

    public boolean deleteCustomer(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE customerid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client: " + e.getMessage());
            throw e;
        }
    }

    public Customer getCustomerById(int id) throws SQLException {
        String sql = "SELECT customerid, firstname, lastname, phone, email FROM customers WHERE customerid = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getInt("customerid"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phone"),
                        rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du client: " + e.getMessage());
            throw e;
        }
        return null;
    }
}