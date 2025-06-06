public class Customer {
    private int id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;

    public Customer(int id, String firstname, String lastname, String phone, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return firstname + " " + lastname + " (" + email + ")";
    }
}
