import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT customerid, firstname, lastname FROM customers LIMIT 20")) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("customerid"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
