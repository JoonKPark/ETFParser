/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

/**
 *
 * @author Jason Park
 */
public class Holding {
    private String name;
    private double percent;
    private int shares;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name.replaceAll("\"", "");
    }

    /**
     * @return the percent
     */
    public double getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(String percent) {
        this.percent = Double.parseDouble(percent.replaceAll("\"", "").replaceAll("%", ""));
    }

    /**
     * @return the shares
     */
    public int getShares() {
        return shares;
    }

    /**
     * @param shares the shares to set
     */
    public void setShares(String shares) {
        this.shares = Integer.parseInt(shares.replaceAll("\"", ""));
    }
    
    public Holding(String n, String p, String s) {
        name = n.replaceAll("\"", "");
        percent = Double.parseDouble(p.replaceAll("\"", "").replaceAll("%", ""));
        shares = Integer.parseInt(s.replaceAll("\"", "").replaceAll(",", ""));
    }
    
}
