public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    // Constructeur vide (utile pour frameworks ou création manuelle)
    public Product() {}

    // Constructeur complet avec ID
    public Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Constructeur sans ID (pour nouvel objet avant insertion en base)
    public Product(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%s (%.2f € - Stock: %d)", name, price, stock);
    }

    
}
