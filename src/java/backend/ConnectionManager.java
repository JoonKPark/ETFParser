package backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Jason Park
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;

public class ConnectionManager {

    static Connection con;
    static String url;

    public static Connection getConnection() {
        
        try {
            // Make sure there is a file containing database credentials
            String url = "jdbc:mysql://104.196.61.78:3306/etfparser?user=root";
            String username = "root";
            String password = "admin";
            /*
            try {
                Scanner scanner = new Scanner(new File("database.txt"));
                
                url = scanner.nextLine();
                username = scanner.nextLine();
                password = scanner.nextLine();
            } catch (FileNotFoundException e) {
            */    
            //}
            Class.forName("com.mysql.jdbc.Driver");

            try {
                con = DriverManager.getConnection(url, username, password);
                System.out.println(con.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        return con;
    }
}
