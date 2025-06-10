package com.erp.dao;


import com.erp.model.Order;
import com.erp.model.OrderLine;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.erp.database.DatabaseManager;
import java.sql.ResultSet;
import com.erp.dao.DBConnection;


import java.util.ArrayList;
import java.util.List;

import com.erp.model.OrderLine;

public class OrderDAO {

    public boolean createOrder(Order order) {
        String insertOrderSQL = "INSERT INTO orders (customer_id, order_date, net_amount, tax, total_amount) VALUES (?, ?, ?, ?, ?) RETURNING id";
        String insertLineSQL = "INSERT INTO orderlines (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL)) {
                psOrder.setInt(1, order.getCustomerId());
                psOrder.setObject(2, order.getOrderDate());
                psOrder.setDouble(3, order.getNetAmount());
                psOrder.setDouble(4, order.getTax());
                psOrder.setDouble(5, order.getTotalAmount());

                var rs = psOrder.executeQuery();
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    try (PreparedStatement psLine = conn.prepareStatement(insertLineSQL)) {
                        for (OrderLine line : order.getLines()) {
                            psLine.setInt(1, orderId);
                            psLine.setInt(2, line.getProductId());
                            psLine.setInt(3, line.getQuantity());
                            psLine.setDouble(4, line.getUnitPrice());
                            psLine.addBatch();
                        }
                        psLine.executeBatch();
                    }

                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                }
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

   public List<OrderLine> getOrderLinesByCustomerId(int customerId) {
    List<OrderLine> lines = new ArrayList<>();

    String sql = """
        SELECT o.id AS order_id, o.order_date, p.name AS product_name, 
               ol.quantity, ol.unit_price
        FROM orders o
        JOIN orderlines ol ON o.id = ol.order_id
        JOIN product p ON p.id = ol.product_id
        WHERE o.customer_id = ?
        ORDER BY o.order_date DESC
    """;

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            OrderLine line = new OrderLine();
            line.setOrderId(rs.getInt("order_id"));
            line.setOrderDate(rs.getDate("order_date").toLocalDate());
            line.setProductName(rs.getString("product_name"));
            line.setQuantity(rs.getInt("quantity"));
            line.setUnitPrice(rs.getDouble("unit_price"));
            lines.add(line);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lines;
}

}