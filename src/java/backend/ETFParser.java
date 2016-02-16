package backend;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Jason Park
 * 
 */
public class ETFParser {
    
    // This URL is the base path of the ETF informations. The ticker symbol will be
    // provided by  user.
    private static final String url = "https://www.spdrs.com/product/fund.seam?ticker=";
    
    // This method will only parse the top holdings table from the given URL.
    private static String getTopHoldings (Document doc) {
        String result = "";
        
        try {
            Element div = doc.getElementById("FUND_TOP_HOLDINGS");
            Element table = div.getElementsByTag("table").get(0);
            Elements rows = table.select("tr");

            for (Element row : rows) {
                Elements cols = row.select("td");
                for (int i = 0; i < cols.size(); i++) {
                    result += "\"" + cols.get(i).text() + "\"";
                    if (i < cols.size() - 1) {
                        result += ",";
                    }
                }
                result += "\n";
            }
        } catch (NullPointerException e) {
            result += "No top holdings info provided.";
        }
        return result;
    }
    
    // This method will return country weights for a given URL.
    private static String getCountryWeights (Document doc) {
        String result = "";
        
        try {
            Element div = doc.getElementById("FUND_COUNTRY_WEIGHTS");
            Element table = div.getElementsByTag("table").get(0);
            Elements rows = table.select("tr");
            
            for (Element row : rows) {
                Elements cols = row.select("td");
                for (int i = 0; i < cols.size(); i++) {
                    result += "\"" + cols.get(i).text() + "\"";
                    if (i < cols.size() - 1) {
                        result += ",";
                    }
                }
                result += "\n";
            }
        } catch (NullPointerException e) {
            result += "No country weight provided.";
        }
        return result;
    }
    
    // Getting sector weights is slightly more tricky. The information
    // contained within the table is dynamically generated, so the resulting
    // section is written in XML and also full of escaped characters.
    // These will need to be changed before parsing into a document for JSoup.
    private static String getSectorWeights (Document doc) {
        String result = "";
        
        try {
            
            // First pull the generated chart from the document.
            Element div = doc.getElementById("SectorsAllocChart");
            Elements chart = doc.select("[style=\"display: none\"]");
            String rewrite = chart.toString();
            
            // Unescape all escaped brackets.
            rewrite = rewrite.replaceAll("&gt;", ">");
            rewrite = rewrite.replaceAll("&lt;", "<");
            
            // Parse the cleaned table as a new Document.
            Document newDoc = Jsoup.parse(rewrite);
            Element table = newDoc.select("component:contains(FUND_SECTOR_ALLOCATION)").get(0);
            Elements rows = table.select("attribute");
            
            for (Element row: rows) {
                result += "\"" + row.select("label").text() + "\"," +
                          "\"" + row.select("value").text() + "\"\n";
            }
           
        } catch (NullPointerException e) {
            result += "No country weight provided.";
        }
        return result;
    }
    
    // Returns name of ETF
    private static String getName (Document doc) {
        String result = "";
        result += doc.select("h1").get(0).text();
        return result;
    }    
    
    // Returns description of ETF
    private static String getDesc (Document doc) {
        String result = "";
        result += doc.getElementsByClass("objective").get(0)
                     .select("p").get(0)
                     .text() + "\n";
        return result;
    }
    
    public static ArrayList<Holding> parseCSVHoldings(String csv) {
        String [] holdings = csv.split("\n");
        ArrayList<Holding> result = new ArrayList<Holding>();
        for (String s: holdings) {
            String [] values = s.split("\",\"");
            Holding temp = new Holding(values[0],values[1],values[2]);
            System.out.println(values [1] + values[2]);
            result.add(temp);
        }
        return result;
    }
    
    public static ArrayList<Country> parseCSVCountries(String csv) {
        ArrayList<Country> result = new ArrayList<Country>();
        
        if (!csv.equals("No country weight provided.")) {
            String [] countries = csv.split("\n");
            for (String s: countries) {
                String [] values = s.split("\",\"");
                
                Country temp = new Country(values[0],values[1]);
                result.add(temp);
            }
        } 
        return result;
        
    }

    public static ArrayList<Sector> parseCSVSectors(String csv) {
        ArrayList<Sector> result = new ArrayList<Sector>();
        String[] sectors = csv.split("\n");
        for (String s : sectors) {
            String[] values = s.split("\",\"");

            Sector temp = new Sector(values[0], values[1]);
            System.out.println(temp.getName() + " " + temp.getWeight());
            result.add(temp);
        }
        return result;
    }
    
    // This method will return all information from a given ETF page provided their symbol.
    public static ArrayList<String> getInfo(String symbol, String user) {
        
        ArrayList<String> newRow = new ArrayList<String>();
        
        // Look first in the database.
        try {
            String DBQuery = "select * from table_csv where etf_symbol=?";
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pst = con.prepareStatement(DBQuery);
            pst.setString(1, symbol);
            ResultSet rs = pst.executeQuery();
            rs.next();
            
            String name = rs.getString("etf_name");
            String desc = rs.getString("etf_desc");
            String holdings = rs.getString("top_holdings");
            String country = rs.getString("country_weights");
            String sector = rs.getString("sector_weights");
            
            newRow.add(symbol);
            newRow.add(name);
            newRow.add(desc);
            newRow.add(holdings);
            newRow.add(country);
            newRow.add(sector);
            
            
            // Clean up
            if (con != null) try {con.close();} catch (Exception e) {}
            if (pst != null) try {pst.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
            
        // If not found in DB, parse web page.
        } catch (SQLException | NullPointerException exception) {
            try {
                Document doc = Jsoup.connect(url + symbol).get();
                
                // Exit if invalid name.
                if (!doc.select(":contains(Fund Page Not found)").isEmpty()) throw new Exception();
                
                
                String name = getName(doc);
                String desc = getDesc(doc);
                String holdings = getTopHoldings(doc);
                String country = getCountryWeights(doc);
                String sector = getSectorWeights(doc);
                
                newRow.add(symbol);
                newRow.add(name);
                newRow.add(desc);
                newRow.add(holdings);
                newRow.add(country);
                newRow.add(sector);
                
                DatabaseHelper addToDB = new DatabaseHelper();
                if (addToDB.addETF(newRow)) System.out.println("Successfully added.");
                

            } catch (Exception ex) {
                ex.printStackTrace();
                return null;    
            }
        }
        try{
            String DBQuery = "select * from user where username=?";
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pst = con.prepareStatement(DBQuery);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            rs.next();
            String searchHistoryOld = "" + rs.getString("searchhistory");
            
            DBQuery = "UPDATE user SET searchhistory=? WHERE username=?";
            pst = con.prepareStatement(DBQuery);
            pst.setString(1, symbol+";"+searchHistoryOld);
            pst.setString(2, user);
            pst.execute();
            
            if (con != null) try {con.close();} catch (Exception e) {}
            if (pst != null) try {pst.close();} catch (Exception e) {}
            if (rs != null) try {rs.close();} catch (Exception e) {}
            
        } catch (Exception e) {e.printStackTrace();}
        finally {}
        
        
        return newRow;
    }

    
    /*
    This is for testing
    public static void main(String[] args) {
        System.out.print("Input stock symbol: ");
        Scanner input = new Scanner(System.in);
        String query = input.next();
        ArrayList<String> res = getInfo(query);
        for(String s: res) System.out.println(s);
    }
    */
    
    
}
