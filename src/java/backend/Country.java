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
public class Country {
    private String name;
    private double weight;

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
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(String weight) {
        this.weight = Double.parseDouble(weight.replaceAll("\"", "").replaceAll("%", ""));
    }
    
    public Country (String name, String weight) {
        this.name = name.replaceAll("\"", "");
        this.weight = Double.parseDouble(weight.replaceAll("\"", "").replaceAll("%", ""));
    }
}
