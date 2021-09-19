package main.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDbUtil {

    // this method definitely works
    public Connection connect(){
        Connection con = null;

        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src/main/resources/database/CustomersDB.db");


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    // this method definitely works
    public Customer logIn(String email, String password) {

        Connection con=connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // create sql query - ERROR WHEN USING '?'
            String sql = "SELECT * FROM customer WHERE email = ? AND password = ?";

            // prep statement
            stmt = con.prepareStatement(sql);

            stmt.setString(1,email);
            stmt.setString(2,password);

            rs = stmt.executeQuery();

            if (rs.next()){
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                float balance = rs.getFloat("balance");

                return new Customer(id,firstName,lastName,email,password,balance); //WORKS!
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(con,stmt,rs);
    }
        return null;
}

    public void register(String firstName, String lastName, String email, String password) {
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "INSERT INTO customer(first_name, last_name, email, password) " +
                    "VALUES (?, ?, ?, ?)";

            stmt = con.prepareStatement(sql);

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, password);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    // update balance - used for both deposit and withdrawal
    public void updateBalance(float newBalance, int id){
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE customer SET balance = ? WHERE id = ?";
            stmt = con.prepareStatement(sql);

            stmt.setFloat(1, newBalance);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    public void receiveTransfer(float moneyToAdd, int id){
        Connection con = connect();
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE customer SET balance = balance + ? WHERE id = ?";
            stmt = con.prepareStatement(sql);

            stmt.setFloat(1, moneyToAdd);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close(con,stmt,null);
        }
    }

    // query that checks if a user exists
    public boolean customerExists(int id) {

        Connection con=connect();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // create sql query - ERROR WHEN USING '?'
            String sql = "SELECT * FROM customer WHERE id = ?";

            // prep statement
            stmt = con.prepareStatement(sql);

            stmt.setInt(1,id);


            rs = stmt.executeQuery();

            if (rs.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            close(con,stmt,rs);
        }
        return false;
    }


    private void close(Connection con, Statement stmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
