package JCode.mysql;

import JCode.CommonTasks;
import JCode.FileHelper;
import objects.LeadOld;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeadQueries {

    private Connection static_con;
    private FileHelper fHelper;
    private EmailPhoneQueries emailPhoneQueries;
    private NoteQueries noteQueries;

    public LeadQueries(Connection static_con, FileHelper fHelper, EmailPhoneQueries emailPhoneQueries, NoteQueries noteQueries) {
        this.static_con = static_con;
        this.fHelper = fHelper;
        this.emailPhoneQueries = emailPhoneQueries;
        this.noteQueries = noteQueries;
    }

    public void insertLead(LeadOld leadOld) {

        String query = "INSERT INTO LEAD_STORE(LS_ID, LS_FNAME ,LS_LNAME ,LS_CNAME ,LS_WEBSITE ,LS_CITY , " +
                " LS_COUNTRY, LS_NOTE, S_ID, S_OTHER, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(LS_ID),0)+1,?,?,?,?,?,?,?,?,?,?,? from LEAD_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, leadOld.getFirstName());
            statement.setString(2, leadOld.getLastName());
            statement.setString(3, leadOld.getCompany());
            statement.setString(4, leadOld.getWebsite());
            statement.setString(5, leadOld.getCity());
            statement.setString(6, leadOld.getCountry());
            statement.setString(7, leadOld.getNote());
            if (leadOld.getOtherText() == null)
                statement.setInt(8, '\0');
            else
                statement.setInt(8, leadOld.getSource());

            statement.setString(9, leadOld.getOtherText());
            statement.setString(10, CommonTasks.getCurrentTimeStamp());
//            statement.setInt(11, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(11, FileHelper.ReadUserApiDetails().getUserCode());
            statement.executeUpdate();

            emailPhoneQueries.emailsPhoneInsertion(statement, leadOld);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateLead(LeadOld leadOld) {

        String query = "UPDATE LEAD_STORE SET LS_FNAME=?,LS_LNAME=?,LS_WEBSITE=?,LS_CNAME=?,LS_CITY=?, " +
                " LS_COUNTRY=?,LS_NOTE=?,S_ID=?,S_OTHER=?,CREATEDON=?,CREATEDBY=?" +
                " WHERE LS_ID=?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, leadOld.getFirstName());
            statement.setString(2, leadOld.getLastName());
            statement.setString(3, leadOld.getWebsite());
            statement.setString(4, leadOld.getCompany());
            statement.setString(5, leadOld.getCity());
            statement.setString(6, leadOld.getCountry());
            statement.setString(7, leadOld.getNote());
            if (leadOld.getOtherText() != null) {
                statement.setInt(8, '\0');
            } else {
                statement.setInt(8, leadOld.getSource());
            }
            statement.setString(9, leadOld.getOtherText());
            statement.setString(10, CommonTasks.getCurrentTimeStamp());
//            statement.setInt(11, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(11, FileHelper.ReadUserApiDetails().getUserCode());
            statement.setInt(12, leadOld.getCode());

            statement.executeUpdate();

            emailPhoneQueries.emailsPhoneInsertion(statement, leadOld);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<LeadOld> getAllLeads(String where) {

        String query = " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME,LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,EM_NAME,PH_NUM,S_ID,S_OTHER " +
                " FROM LEAD_STORE AS LS, EMAIL_LIST AS EL,  PHONE_LIST AS PL " +
                " WHERE EL.LS_ID = LS.LS_ID   " +
                " AND PL.LS_ID = LS.LS_ID " +
                " AND LS.FREZE = 0" +
                " UNION ALL " +
                " SELECT LS.LS_ID AS LS_ID,LS_FNAME,LS_LNAME,LS_CNAME, LS_CITY,LS_COUNTRY,LS_NOTE,LS_WEBSITE,null EM_NAME,null PH_NUM,S_ID,S_OTHER " +
                " FROM LEAD_STORE AS LS  " +
                " WHERE LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM EMAIL_LIST) " +
                " AND LS.LS_ID NOT IN (SELECT IFNULL(LS_ID,0) FROM phone_list) " +
                " AND LS.FREZE = 0 " +
                " AND LS.CONVERTED = 0";

        if (where == null) {
            query = query + " ";
        } else {
            query = query + " AND " + where;
        }
        List<LeadOld> allLeadOlds = new ArrayList<>();
        try {

            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                LeadOld leadOld = new LeadOld();
                leadOld.setCode(set.getInt("LS_ID"));
                leadOld.setFirstName(set.getString("LS_FNAME"));
                leadOld.setLastName(set.getString("LS_LNAME"));
                leadOld.setCity(set.getString("LS_CITY"));
                leadOld.setCountry(set.getString("LS_COUNTRY"));
                leadOld.setWebsite(set.getString("LS_WEBSITE"));
                leadOld.setCompany(set.getString("LS_CNAME"));
                leadOld.setNote(set.getString("LS_NOTE"));
                leadOld.setEmail(set.getString("EM_NAME"));
                leadOld.setPhone(set.getString("PH_NUM"));
                leadOld.setSource(set.getInt("S_ID"));
                leadOld.setOtherText(set.getString("S_OTHER"));
                allLeadOlds.add(leadOld);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allLeadOlds;
    }

    public LeadOld getParticularLead(LeadOld where) {

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
                LeadOld leadOld = new LeadOld();
                leadOld.setCode(set.getInt("LS_ID"));
                leadOld.setFirstName(set.getString("LS_FNAME"));
                leadOld.setLastName(set.getString("LS_LNAME"));
                leadOld.setCity(set.getString("LS_CITY"));
                leadOld.setCountry(set.getString("LS_COUNTRY"));
                leadOld.setWebsite(set.getString("LS_WEBSITE"));
                leadOld.setCompany(set.getString("LS_CNAME"));
                leadOld.setNote(set.getString("LS_NOTE"));
                leadOld.setEmail(set.getString("EM_NAME"));
                leadOld.setPhone(set.getString("PH_NUM"));
                leadOld.setNotes(noteQueries.getNotes(leadOld));

                return leadOld;
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

    public List<String> getAllSources(String where) {

        String query = " SELECT S_NAME FROM SOURCE_LIST ";
        List<String> sources = new ArrayList<>();
        try {

            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                sources.add(set.getString("S_NAME"));
            }
            set.close();
            statement.close();
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return sources;
    }


    public void insertSourceCreation(String name, String desc) {    //Set sources on  first startup
        String query = "INSERT INTO SOURCE_LIST (S_ID, S_NAME, S_DESC) " +
                " SELECT IFNULL(MAX(S_ID),0) + 1,?,? FROM SOURCE_LIST ";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, desc);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean checkIfSources() {
        String query = "SELECT S_ID FROM SOURCE_LIST";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);

            ResultSet set = statement.executeQuery();
            if (!set.next()) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void checkAndPopulateSourcesOnCreation() {
        if (!checkIfSources()) {
            insertSourceCreation("Referall", "");
            insertSourceCreation("Website", "");
        }
    }

    public void markLeadAsClient(LeadOld leadOld) {
        String query = "UPDATE LEAD_STORE SET CONVERTED = 1 WHERE LS_ID = ?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, leadOld.getCode());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // // doRelease(con);
        }
    }

    public void archiveLead(LeadOld leadOld) {
        String query = "UPDATE LEAD_STORE SET FREZE = 1 WHERE LS_ID = ?";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, leadOld.getCode());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // // doRelease(con);
        }
    }
}
