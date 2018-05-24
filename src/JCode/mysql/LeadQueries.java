package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.Lead;
import objects.ProductModule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeadQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    private EmailPhoneQueries emailPhoneQueries;
    
    public LeadQueries(Connection static_con, fileHelper fHelper, EmailPhoneQueries emailPhoneQueries) {
        this.static_con = static_con;
        this.fHelper = fHelper;
        this.emailPhoneQueries = emailPhoneQueries;
    }
    
    public void insertLead(Lead lead) {
        
        String query = "INSERT INTO LEAD_STORE(LS_ID, LS_FNAME ,LS_LNAME ,LS_CNAME ,LS_WEBSITE ,LS_CITY , " +
                " LS_COUNTRY, CREATEDON, CREATEDBY) " +
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
            statement.setString(7, CommonTasks.getCurrentTimeStamp());
            statement.setInt(8, fHelper.ReadUserDetails().getUCODE());
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void updateLead(Lead lead) {
        
        String query = "UPDATE LEAD_STORE SET LS_FNAME=?,LS_LNAME=?,LS_WEBSITE=,LS_CNAME=?,LS_CITY=?," +
                "LS_COUNTRY=?,LS_DESC,CREATEDON=?,CREATEDBY=?,FREZE=?" +
                "WHERE CS_ID=?";
        
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
            statement.setString(7, lead.getDesc());
            statement.setString(8, CommonTasks.getCurrentTimeStamp());
            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());
            statement.setBoolean(10, false);
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}
