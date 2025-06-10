package com.erp.model;

public class Customer {
    private int customerId;
    private String firstname;
    private String lastname;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private Integer zip;
    private String country;
    private Short region;
    private String phone;
    private Integer creditcardtype;
    private String creditcard;
    private String creditcardexpiration;
    private String username;
    private String password;

    // Constructeur principal
public Customer(int customerId, String firstname, String lastname, String email, String phone, String city) {
    this.customerId = customerId;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phone = phone;
    this.city = city;
}

    // Constructeur complet (utile pour tests ou évolutions)
    public Customer(int customerId, String firstname, String lastname, String email,
                    String address1, String address2, String city, String state, Integer zip,
                    String country, Short region, String phone,
                    Integer creditcardtype, String creditcard, String creditcardexpiration,
                    String username, String password) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.region = region;
        this.phone = phone;
        this.creditcardtype = creditcardtype;
        this.creditcard = creditcard;
        this.creditcardexpiration = creditcardexpiration;
        this.username = username;
        this.password = password;
    }

    // Getters et setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Short getRegion() {
        return region;
    }

    public void setRegion(Short region) {
        this.region = region;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCreditcardtype() {
        return creditcardtype;
    }

    public void setCreditcardtype(Integer creditcardtype) {
        this.creditcardtype = creditcardtype;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getCreditcardexpiration() {
        return creditcardexpiration;
    }

    public void setCreditcardexpiration(String creditcardexpiration) {
        this.creditcardexpiration = creditcardexpiration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
public String toString() {
    return firstname + " " + lastname;
}

}
