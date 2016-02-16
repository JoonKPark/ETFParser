package backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author Jason Park
 */
public class DatabaseHelper {
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;
    // Takes the arrayList representing a new row and inserts it into database
    // of ETF for future lookup without parsing.
    public boolean addETF (ArrayList<String> newRow) {
        try {
            con = ConnectionManager.getConnection();
            String query = "INSERT INTO `etfparser`.`table_csv` (etf_symbol, etf_name, etf_desc, top_holdings, country_weights, sector_weights) " +
                       "VALUES(?,?,?,?,?,?)";
            stmt = con.prepareStatement(query);
            for (int i = 0; i < newRow.size(); i++) {
                stmt.setString(i+1, newRow.get(i));
            }
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) try {con.close();} catch (Exception e){}
            if (stmt != null) try {stmt.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
        }
    }
    
    // This method encrypts password. For use when registering and logging in.
    private String hashPassword(String pw) {
        String passwordToHash = pw;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    // Attempt to log in user given an id and password supplied by user.
    public boolean login (String id, String password) {
        String query = "SELECT * FROM `etfparser`.`user` WHERE username=? AND password=?";
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, hashPassword(password));
            ResultSet rs = stmt.executeQuery();
            return rs.next();
            
        } catch (Exception e) { e.printStackTrace(); return false; }
        finally {
            if (con != null) try {con.close();} catch (Exception e){}
            if (stmt != null) try {stmt.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
        }
    }
    
    public User lookup (String username) {
        String query = "FROM user SELECT * WHERE username=?";
        User result = new User();
        
        try {
            con = ConnectionManager.getConnection();
            stmt = con.prepareStatement(query);
            stmt.setString(1,username);
            stmt.executeQuery();
            if (rs.next()) {
                result = new User(rs.getString("username"),rs.getString("firstname"),rs.getString("lastname"),rs.getString("searchhistory"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if (con != null) try {con.close();} catch (Exception e){}
            if (stmt != null) try {stmt.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
        }
        return result;
    }
    
    // Create a new entry in the database for User with given credentials.
    public boolean register (String id, String password, String email, String firstName, String lastName) {
        String query = "INSERT INTO `etfparser`.`user` (username,email,password,first_name,last_name) VALUES (?,?,?,?,?)";
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, email);
            stmt.setString(3, hashPassword(password));
            stmt.setString(4, firstName);
            stmt.setString(5, lastName);
            stmt.execute();
            return true;
        } catch (Exception e) { e.printStackTrace(); }
        finally {
            if (con != null) try {con.close();} catch (Exception e){}
            if (stmt != null) try {stmt.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
        }
        return false;
    }
    
}
