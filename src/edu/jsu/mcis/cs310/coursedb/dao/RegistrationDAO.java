package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class RegistrationDAO {
    
    // INSERT YOUR CODE HERE
    
    private final DAOFactory daoFactory;
    
    RegistrationDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public boolean create(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                String myStatement = "INSERT INTO registration (studentid, termid, crn) VALUES (?, ?, ?)";
                ps = conn.prepareStatement(myStatement);
                
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                
                int updated = ps.executeUpdate();
                if (updated == 1) {
                    result = true;
                }
                
                
//                System.err.println(hasResults);
//                if (hasResults) {
//                
//                    result = true;
//                
//                }
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public boolean delete(int studentid, int termid, int crn) {
        
        boolean result = false;
        
        PreparedStatement ps = null;
        
        String myStatemnt = "DELETE FROM registration WHERE studentid=? AND termid=? AND crn=?";
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(myStatemnt);
                
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                ps.setInt(3, crn);
                
                int updated = ps.executeUpdate();
                if (updated == 1) {
                    result = true;
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
   
    public boolean delete(int studentid, int termid) {
        
        boolean result = false;
        
        
        PreparedStatement ps = null;
        
        String myStatement = "DELETE FROM registration WHERE studentid=? AND termid=?";
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(myStatement);
                
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                int updated = ps.executeUpdate();
                
                //System.err.println(updated); Used to find out updated shows how many rows were affected
                if (updated >= 1 ) {
                    
                    result = true;
                    
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {

            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }

    public String list(int studentid, int termid) {
        
        String result = "[]";
        
        String myStatement = "select * from registration where studentid=? and termid=?";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        JsonArray allResults = new JsonArray();
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(myStatement);
                ps.setInt(1, studentid);
                ps.setInt(2, termid);
                
                boolean hasResults = ps.execute();
                
                if (hasResults) {
                
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                    
                        LinkedHashMap<String, Object> sections = new LinkedHashMap<>();
                        
                        sections.put("studentid", rs.getString("studentid"));
                        sections.put("termid", rs.getString("termid"));
                        sections.put("crn", rs.getString("crn"));
                        allResults.add(sections);
                    
                    }
                    
                    result = Jsoner.serialize(allResults);
                    
                }
                
            }
            
        }
        
        catch (Exception e) { e.printStackTrace(); }
        
        finally {
            
            if (rs != null) { try { rs.close(); } catch (Exception e) { e.printStackTrace(); } }
            if (ps != null) { try { ps.close(); } catch (Exception e) { e.printStackTrace(); } }
            
        }
        
        return result;
        
    }
    
}
