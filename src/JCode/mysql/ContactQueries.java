package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import objects.ContactProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public void insertContact(ContactProperty contact) {
        
        String query = "INSERT INTO CONTACT_STORE(CS_ID, CS_FNAME ,CS_LNAME ,CS_DOB ,CS_ADDR ,CS_CITY , " +
                "CS_COUNTRY ,CS_NOTE ,FREZE ,CL_ID, CREATEDON, CREATEDBY) " +
                " SELECT IFNULL(max(CS_ID),0)+1,?,?,?,?,?,?,?,?,?,?,? from CONTACT_STORE";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getDob());
            statement.setString(4, contact.getAddress());
            statement.setString(5, contact.getCity());
            statement.setString(6, contact.getCountry());
            statement.setString(7, contact.getNote());
            statement.setBoolean(8, contact.isIsFreeze());
            statement.setInt(9, contact.getClID());
            statement.setString(10, CommonTasks.getCurrentTimeStamp());
            statement.setInt(11, fHelper.ReadUserDetails().getUCODE());
            
            statement.executeUpdate();
            
            
            EmailsPhoneInsertion(statement, contact);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void updateContact(ContactProperty contact) {
        
        String query = "UPDATE CONTACT_STORE SET CS_FNAME =?, CS_LNAME =?, CS_DOB =?,CS_ADDR =?, CS_CITY =?, " +
                " CS_COUNTRY =?, CS_NOTE =?, FREZE =?, CL_ID =?, CREATEDON  =?, CREATEDBY =? " +
                " WHERE CS_ID =?";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, contact.getFirstName());
            statement.setString(2, contact.getLastName());
            statement.setString(3, contact.getDob());
            statement.setString(4, contact.getAddress());
            statement.setString(5, contact.getCity());
            statement.setString(6, contact.getCountry());
            statement.setString(7, contact.getNote());
            statement.setBoolean(8, contact.isIsFreeze());
            statement.setInt(9, contact.getClID());
            statement.setString(10, CommonTasks.getCurrentTimeStamp());
            statement.setInt(11, fHelper.ReadUserDetails().getUCODE());
            statement.setInt(12, contact.getCode());
            
            statement.executeUpdate();
            
            EmailsPhoneInsertion(statement, contact);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<ContactProperty> getAllContactsProperty(String where) {
        String query = "SELECT CS.CS_ID AS CS_ID,CS_FNAME,CS_LNAME,CS_DOB,CS_ADDR," +
                " CS_CITY,CS_COUNTRY,CS_NOTE,CS.CREATEDON,FREZE,EM_NAME,PH_NUM,CL_NAME,CS.CL_ID AS CL_ID" +
                " FROM CONTACT_STORE AS CS, EMAIL_LIST AS EL, PHONE_LIST AS PL, CLIENT_STORE AS CL " +
                " WHERE EL.CS_ID = CS.CS_ID " +
                " AND PL.CS_ID = CS.CS_ID " +
                " AND CL.CL_ID = CS.CL_ID";
        
        if (where == null) {
            query = query + " AND FREZE = 0 ORDER BY CS.CS_ID";
        } else {
            query = query + " AND " + where;
        }
        
        List<ContactProperty> allContacts = new ArrayList<>();
        
        try {
            System.out.println(query);
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                ContactProperty contact = new ContactProperty();
                contact.setCode(set.getInt("CS_ID"));
                contact.setFirstName(set.getString("CS_FNAME"));
                contact.setLastName(set.getString("CS_LNAME"));
                contact.setAddress(set.getString("CS_ADDR"));
                contact.setCity(set.getString("CS_CITY"));
                contact.setCountry(set.getString("CS_COUNTRY"));
                contact.setEmail(set.getString("EM_NAME"));
                contact.setMobile(set.getString("PH_NUM"));
                contact.setDob(set.getString("CS_DOB"));
                contact.setClID(set.getInt("CL_ID"));
                contact.setClientName(set.getString("CL_NAME"));
                contact.setAge(CommonTasks.getAge(contact.getDob()));
                contact.setNote(set.getString("CS_NOTE"));
                contact.setIsFreeze(set.getBoolean("FREZE"));
                
                allContacts.add(contact);
            }
            
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return allContacts;
    }
    
    public int getNewContactCode() {
        String query = "SELECT IFNULL(max(CS_ID),0)+1 AS CS_ID FROM CONTACT_STORE";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                return set.getInt("CS_ID");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    private void EmailsPhoneInsertion(PreparedStatement statement, ContactProperty contact) {
        
        String deleteEmails = "DELETE FROM EMAIL_LIST WHERE CS_ID = ?";
        
        String deletePhones = "DELETE FROM PHONE_LIST WHERE CS_ID = ?";
        
        String emailList = "INSERT INTO EMAIL_LIST(EM_ID,EM_NAME,CS_ID,UCODE,CL_ID) " +
                "SELECT IFNULL(max(EM_ID),0)+1,?,?,?,? from EMAIL_LIST";
        
        String phoneList = "INSERT INTO PHONE_LIST(PH_ID,PH_NUM,CS_ID,UCODE,CL_ID) " +
                "SELECT IFNULL(max(PH_ID),0)+1,?,?,?,? from PHONE_LIST";
        
        try {
            statement = null;
            statement = static_con.prepareStatement(deleteEmails);
            statement.setInt(1, contact.getCode());
            statement.executeUpdate();
            //Adding Emails
            String[] emails = new String[]{contact.getEmail()};
            
            for (int i = 0; i < emails.length; i++) {   //Inserting Emailss
                statement = null;
                if (emails[i] == null)
                    continue;
                
                statement = static_con.prepareStatement(emailList);
                statement.setString(1, emails[i]);
                statement.setInt(2, contact.getCode());
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            }
            
            statement = null;
            statement = static_con.prepareStatement(deletePhones);
            statement.setInt(1, contact.getCode());
            statement.executeUpdate();
            
            //Adding Phones
            String[] phones = new String[]{contact.getMobile()};
            
            for (int i = 0; i < phones.length; i++) {   //Inserting Emailss
                statement = null;
                if (phones[i] == null)
                    continue;
                
                statement = static_con.prepareStatement(phoneList);
                statement.setString(1, phones[i]);
                statement.setInt(2, contact.getCode());
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            }
            
            
            if (statement != null)
                statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
