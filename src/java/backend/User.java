/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.ArrayList;

/**
 *
 * @author Jason Park
 */
public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String queries;
    public User(){}
    public User(String u, String f, String l, String q) {
        username = u;
        firstname = f;
        lastname=l;
        queries=q;
    }
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    public ArrayList<String> getHistory() {
        ArrayList<String> toReturn = new ArrayList();
        toReturn.add("");
        
        if (queries != null) {
            String [] spl = queries.split(";");
            for (String s: spl) if (s != null) toReturn.add(s);
        
        }
        return toReturn;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the queries
     */
    public String getQueries() {
        return queries;
    }

    /**
     * @param queries the queries to set
     */
    public void setQueries(String queries) {
        this.queries = queries;
    }
}
