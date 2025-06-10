package com.erp.model;

public class Product {
    private int id;
    private int stock;
    private String name;
    private double unitPrice;
    private String category;

    public Product() {}

    public Product(int id, String name, double unitPrice, String category) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.category = category;
    }

    // Getters et setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public String getCategory() { return category; }
    public int getStock() {return stock; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
    public void setCategory(String category) { this.category = category; }
    public void setStock(int stock) {this.stock = stock; }
}
