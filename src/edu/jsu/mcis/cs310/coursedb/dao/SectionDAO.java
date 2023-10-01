package edu.jsu.mcis.cs310.coursedb.dao;

import com.github.cliftonlabs.json_simple.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.LinkedHashMap;

public class SectionDAO {
    
    // INSERT YOUR CODE HERE
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        
        
        String result = "[]";
        
        String myStatement = "select * from section where termid=? and subjectid=? and num=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        
        
        JsonArray allResults = new JsonArray();
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                
                ps = conn.prepareStatement(myStatement);
                ps.setInt(1, termid);
                ps.setString(2, subjectid);
                ps.setString(3, num);
                //System.err.println(ps.toString()); Ex.) "select * from section where termid=1 and subjectid='CS' and num='201'"
                boolean hasResults = ps.execute();
                
                if (hasResults) {
                    
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        
                        LinkedHashMap<String, Object> sections = new LinkedHashMap<>();
                        
                        sections.put("termid", rs.getString("termid"));
                        sections.put("crn", rs.getString("crn"));
                        sections.put("subjectid", rs.getString("subjectid"));
                        sections.put("num", rs.getString("num"));
                        sections.put("section", rs.getString("section"));
                        sections.put("scheduletypeid", rs.getString("scheduletypeid"));
                        sections.put("instructor", rs.getString("instructor"));
                        sections.put("start", rs.getString("start"));
                        sections.put("end", rs.getString("end"));
                        sections.put("days", rs.getString("days"));
                        sections.put("where", rs.getString("where"));
                        allResults.add(sections);
                    
                    }
                    
                    
                    
                    //System.err.println(Jsoner.serialize(allResults));
                    
                    result = Jsoner.serialize(allResults);
                    
                    //String id = rs.getString("termid");
                    //String instructor = rs.getString("Instructor");
                    
                    
                    //System.err.println(instructor); //prints result set on the current row, of the column "instructor"
                    
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