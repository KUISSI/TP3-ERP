package com.erp.model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private int id;
    private int customerId;
    private LocalDate orderDate;
    private double netAmount;
    private double tax;
    private double totalAmount;
    private List<OrderLine> lines;
    private String productName;

    // Getters et Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public void setLines(List<OrderLine> lines) {
        this.lines = lines;
    }
    public String getProductName() {
        return productName;
}

    public void setProductName(String productName) {
        this.productName = productName;
}



}
