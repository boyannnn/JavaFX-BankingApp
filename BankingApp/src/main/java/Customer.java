package main.java;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private float balance;

    public Customer( int id, String firstName, String lastName, String email, String password, float balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getId(){
        return this.id;
    }

}
