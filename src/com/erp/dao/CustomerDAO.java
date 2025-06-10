package com.erp.dao;

import com.erp.database.DatabaseManager;
import com.erp.model.Customer;
import com.erp.dao.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class CustomerDAO {

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT customerid, firstname, lastname, email, phone, city FROM customers")) {

            while (rs.next()) {
                int id = rs.getInt("customerid");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String email = rs.getString("email");
                String phone = rs.getString("phone"); 
                String city = rs.getString("city"); 

                customers.add(new Customer(id, firstname, lastname, email, phone, city));
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des clients : " + e.getMessage());
        }

        return customers;
    }

    public boolean addCustomer(String firstname, String lastname, String email, String phone, String city) {
    String sql = "INSERT INTO customers (firstname, lastname, email, phone, city) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, firstname);
        pstmt.setString(2, lastname);
        pstmt.setString(3, email);
        pstmt.setString(4, phone);
        pstmt.setString(5, city);

        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}}