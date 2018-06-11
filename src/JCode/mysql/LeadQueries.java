package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.Lead;
import objects.ProductModule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeadQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    private EmailPhoneQueries emailPhoneQueries;
    private NoteQueries noteQueries;
    
    public LeadQueries(Connection static_con, fileHelper fHelper, EmailPhoneQueries emailPhoneQueries, NoteQueries noteQueries) {
        this.static_con = static_con;
        this.fHelper = fHelper;
        this.emailPhoneQueries = emailPhoneQueries;
        this.noteQueries = noteQueries;
    }
    
    public void insertLead(Lead lead) {
        
        String query = "INSERT INTO LEAD_STORE(LS_ID, LS_FNAME ,LS_LNAME ,LS_CNAME ,LS_WEBSITE ,LS_CITY , " +
                " LS_COUNTRY, LS_NOTE, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(LS_ID),0)+1,?,?,?,?,?,?,?,? from LEAD_STORE";
        
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, lead.getFirstName());
            statement.setString(2, lead.getLastName());
            statement.setString(3, lead.getCompany());
            statement.setString(4, lead.getWebsite());
            statement.setString(5, lead.getCity());
            statement.setString(6, lead.getCountry());
            statement.setString(7, lead.getNote());
            statement.setString(8, CommonTasks.getCurrentTimeStamp());
            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());
            
            statement.executeUpdate();
            
            emailPhoneQueries.emailsPhoneInsertion(statement, lead);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void updateLead(Lead lead) {
        
        String query = "UPDATE LEAD_STORE SET LS_FNAME=?,LS_LNAME=?,LS_WEBSITE=?,LS_CNAME=?,LS_CITY=?, " +
                " LS_COUNTRY=?,LS_NOTE=?,CREATEDON=?,CREATEDBY=?" +
                " WHERE LS_ID=?";
        
        System.out.println(query);
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, lead.getFirstName());
            statement.setString(2, lead.getLastName());
            statement.setString(3, lead.getWebsite());
            statement.setString(4, lead.getCompany());
            statement.setString(5, lead.getCity());
            statement.setString(6, lead.getCountry());
            statement.setString(7, lead.getNote());
            statement.setString(8, CommonTasks.getCurrentTimeStamp());
            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(10, lead.getCode());
            
            statement.executeUpdate();
            
            emailPhoneQueries.emailsPhoneInsertion(statement, lead);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<Lead> getAllLeads(String where) {
        
        String query = " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME,LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,EM_NAME,PH_NUM " +
                " FROM LEAD_STORE AS LS, EMAIL_LIST AS EL,  PHONE_LIST AS PL  " +
                " WHERE EL.LS_ID = LS.LS_ID   " +
                " AND PL.LS_ID = LS.LS_ID " +
                " UNION ALL " +
                " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME, LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,null EM_NAME,null PH_NUM FROM LEAD_STORE AS LS  " +
                " WHERE LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM EMAIL_LIST) " +
                " AND LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM phone_list) ";
        
        if (where == null) {
            query = query + " ";
        } else {
            query = query + " AND " + where;
        }
        System.out.println(query);
        List<Lead> allLeads = new ArrayList<>();
        try {
            
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                Lead lead = new Lead();
                lead.setCode(set.getInt("LS_ID"));
                lead.setFirstName(set.getString("LS_FNAME"));
                lead.setLastName(set.getString("LS_LNAME"));
                lead.setCity(set.getString("LS_CITY"));
                lead.setCountry(set.getString("LS_COUNTRY"));
                lead.setWebsite(set.getString("LS_WEBSITE"));
                lead.setCompany(set.getString("LS_CNAME"));
                lead.setNote(set.getString("LS_NOTE"));
                lead.setEmail(set.getString("EM_NAME"));
                lead.setPhone(set.getString("PH_NUM"));
                
                allLeads.add(lead);
            }
            
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return allLeads;
    }
    
    public Lead getParticularLead(Lead where) {
        
        String query = " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME,LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,EM_NAME,PH_NUM " +
                " FROM LEAD_STORE AS LS, EMAIL_LIST AS EL,  PHONE_LIST AS PL  " +
                " WHERE EL.LS_ID = LS.LS_ID   " +
                " AND PL.LS_ID = LS.LS_ID " +
                " AND LS.LS_ID = ? " +
                " UNION ALL " +
                " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME, LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,null EM_NAME,null PH_NUM FROM LEAD_STORE AS LS  " +
                " WHERE LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM EMAIL_LIST) " +
                " AND LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM phone_list) " +
                " AND LS.LS_ID = ? ";
        try {
            
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, where.getCode());
            statement.setInt(2, where.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                Lead lead = new Lead();
                lead.setCode(set.getInt("LS_ID"));
                lead.setFirstName(set.getString("LS_FNAME"));
                lead.setLastName(set.getString("LS_LNAME"));
                lead.setCity(set.getString("LS_CITY"));
                lead.setCountry(set.getString("LS_COUNTRY"));
                lead.setWebsite(set.getString("LS_WEBSITE"));
                lead.setCompany(set.getString("LS_CNAME"));
                lead.setNote(set.getString("LS_NOTE"));
                lead.setEmail(set.getString("EM_NAME"));
                lead.setPhone(set.getString("PH_NUM"));
                lead.setNotes(noteQueries.getNotes(lead));
//                System.out.println(lead);
                return lead;
            }
            
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public int getNewLeadCode() {
        String query = "SELECT IFNULL(max(LS_ID),0)+1 AS LS_ID FROM LEAD_STORE";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                return set.getInt("LS_ID");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
