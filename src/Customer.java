public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private String address1;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;

    // Constructeur complet
    public Customer(int id, String firstname, String lastname, String address1, 
                   String city, String state, String country, String phone, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address1 = address1;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getAddress1() { return address1; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public void setAddress1(String address1) { this.address1 = address1; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setCountry(String country) { this.country = country; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}