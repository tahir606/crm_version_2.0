package JCode.mysql;

import JCode.CommonTasks;
import JCode.fileHelper;
import client.newClient.newClientController;
import objects.ClientProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientQueries {
    
    private Connection static_con;
    private fileHelper fHelper;
    
    public ClientQueries(Connection static_con, fileHelper fHelper) {
        this.static_con = static_con;
        this.fHelper = fHelper;
    }
    
    public void insertClient(ClientProperty client) {
        
        String query = "INSERT INTO CLIENT_STORE(CL_ID,CL_NAME,CL_OWNER,CL_ADDR,CL_CITY" +
                ",CL_COUNTRY,CL_WEBSITE,CL_TYPE,CL_JOINDATE,CREATEDBY,CREATEDON) " +
                " SELECT IFNULL(max(CL_ID),0)+1,?,?,?,?,?,?,?,?,?,? from CLIENT_STORE";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getOwner());
            statement.setString(3, client.getAddr());
            statement.setString(4, client.getCity());
            statement.setString(5, client.getCountry());
            statement.setString(6, client.getWebsite());
            statement.setInt(7, client.getType());
            if (!client.getJoinDate().equals("null"))
                statement.setString(8, client.getJoinDate());
            else
                statement.setString(8, null);
            statement.setInt(9, fHelper.ReadUserDetails().getUCODE());
            statement.setString(10, CommonTasks.getCurrentTimeStamp());
            
            statement.executeUpdate();
            
            EmailsPhoneInsertion(statement, client);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
    public void updateClient(ClientProperty client) {
        
        String query = "UPDATE  client_store  SET  CL_NAME = ?, CL_OWNER = ?," +
                " CL_ADDR = ?, CL_CITY = ?, CL_COUNTRY = ?," +
                " CL_WEBSITE = ?, CL_TYPE = ?, CL_JOINDATE = ? WHERE CL_ID = ?";
        
        // Connection con = getConnection();
        PreparedStatement statement = null;
        
        System.out.println("Client Owner: " + client.getOwner());
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getOwner());
            statement.setString(3, client.getAddr());
            statement.setString(4, client.getCity());
            statement.setString(5, client.getCountry());
            statement.setString(6, client.getWebsite());
            statement.setInt(7, client.getType());
            if (!client.getJoinDate().equals("null"))
                statement.setString(8, client.getJoinDate());
            else
                statement.setString(8, null);
            statement.setInt(9, client.getCode());
            
            statement.executeUpdate();
            
            EmailsPhoneInsertion(statement, client);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // doRelease(con);
        }
        
    }
    
    public int getNoClients() {
        String query = "SELECT COUNT(CL_ID) FROM CLIENT_STORE WHERE CL_TYPE = 1";
        
        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            
            while (set.next())
                return set.getInt(1);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public List<ClientProperty> getAllClients(String where) {
        String query = "SELECT CL_ID,CL_NAME,CL_OWNER,CL_ADDR," +
                "CL_CITY,CL_COUNTRY,CL_WEBSITE,CL_TYPE,CL_JOINDATE FROM CLIENT_STORE";
        
        
        if (where == null) {
            query = query + " WHERE CL_ID != 0 ORDER BY CL_ID";
        } else {
            query = query + " WHERE " + where;
        }
        
        String emails = "SELECT EM_NAME FROM EMAIL_LIST WHERE CL_ID = ?";
        String phones = "SELECT PH_NUM FROM PHONE_LIST WHERE CL_ID = ?";
        
        List<ClientProperty> allClients = new ArrayList<>();
        
        try {
            // Connection con = getConnection();
            System.out.println(query);
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            
            while (set.next()) {
                ClientProperty client = new ClientProperty();
                client.setCode(set.getInt("CL_ID"));
                client.setName(set.getString("CL_NAME"));
                client.setOwner(set.getString("CL_OWNER"));
                client.setAddr(set.getString("CL_ADDR"));
                client.setCity(set.getString("CL_CITY"));
                client.setCountry(set.getString("CL_COUNTRY"));
                client.setWebsite(set.getString("CL_WEBSITE"));
                client.setType(set.getInt("CL_TYPE"));
                client.setJoinDate(set.getString("CL_JOINDATE"));
                
                //Get all Emails
                PreparedStatement st = static_con.prepareStatement(emails);
                st.setInt(1, client.getCode());
                ResultSet setArray = st.executeQuery();
                
                String[] dataArr = new String[newClientController.noOfFields];
                int c = 0;
                while (setArray.next()) {
                    try {
                        dataArr[c] = setArray.getString("EM_NAME");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("ArrayIndexOutOfBoundsException");
                    }
                    c++;
                }
                client.setEmails(dataArr);
                
                //Get all Phone Numbers
                st = null;
                st = static_con.prepareStatement(phones);
                st.setInt(1, client.getCode());
                setArray = null;
                setArray = st.executeQuery();
                
                dataArr = new String[newClientController.noOfFields];
                c = 0;
                while (setArray.next()) {
                    dataArr[c] = setArray.getString("PH_NUM");
                    c++;
                }
                client.setPhones(dataArr);
                
                allClients.add(client);
            }
            
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return allClients;
    }
    
    public List<ClientProperty> getAllClientsProperty(String where) {
        String query = "SELECT CL_ID,CL_NAME,CL_OWNER,CL_ADDR," +
                "CL_CITY,CL_COUNTRY,CL_WEBSITE,CL_TYPE,CL_JOINDATE FROM CLIENT_STORE";
        
        
        if (where == null) {
            query = query + " WHERE CL_ID != 0 ORDER BY CL_ID";
        } else {
            query = query + " WHERE " + where;
        }
        
        String emails = "SELECT EM_NAME FROM EMAIL_LIST WHERE CL_ID = ?";
        String phones = "SELECT PH_NUM FROM PHONE_LIST WHERE CL_ID = ?";
        
        List<ClientProperty> allClients = new ArrayList<>();
        
        try {
            // Connection con = getConnection();
            System.out.println(query);
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            
            while (set.next()) {
                ClientProperty client = new ClientProperty();
                client.setCode(set.getInt("CL_ID"));
                client.setName(set.getString("CL_NAME"));
                client.setOwner(set.getString("CL_OWNER"));
                client.setAddr(set.getString("CL_ADDR"));
                client.setCity(set.getString("CL_CITY"));
                client.setCountry(set.getString("CL_COUNTRY"));
                client.setWebsite(set.getString("CL_WEBSITE"));
                client.setType(set.getInt("CL_TYPE"));
                client.setJoinDate(set.getString("CL_JOINDATE"));
                
                //Get all Emails
                PreparedStatement st = static_con.prepareStatement(emails);
                st.setInt(1, client.getCode());
                ResultSet setArray = st.executeQuery();
                
                String[] dataArr = new String[newClientController.noOfFields];
                int c = 0;
                while (setArray.next()) {
                    try {
                        dataArr[c] = setArray.getString("EM_NAME");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("ArrayIndexOutOfBoundsException");
                    }
                    c++;
                }
                client.setEmails(dataArr);
                
                //Get all Phone Numbers
                st = null;
                st = static_con.prepareStatement(phones);
                st.setInt(1, client.getCode());
                setArray = null;
                setArray = st.executeQuery();
                
                dataArr = new String[newClientController.noOfFields];
                c = 0;
                while (setArray.next()) {
                    dataArr[c] = setArray.getString("PH_NUM");
                    c++;
                }
                client.setPhones(dataArr);
                
                allClients.add(client);
            }
            
            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return allClients;
    }


//    public String[] getClientEmails() {
//
//    }
    
    public List<String> getClientTypes() {
        
        String query = "SELECT CT_NAME FROM CLIENT_TYPE";
        
        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            
            List<String> types = new ArrayList<>();
            
            while (set.next()) {
                types.add(set.getString("CT_NAME"));
            }
            
            // doRelease(con);
            
            return types;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    private void EmailsPhoneInsertion(PreparedStatement statement, ClientProperty client) {
        
        String deleteEmails = "DELETE FROM EMAIL_LIST WHERE CL_ID = ?";
        
        String deletePhones = "DELETE FROM PHONE_LIST WHERE CL_ID = ?";
        
        String emailList = "INSERT INTO EMAIL_LIST(EM_ID,EM_NAME,CL_ID,UCODE,CS_ID) " +
                "SELECT IFNULL(max(EM_ID),0)+1,?,?,?,? from EMAIL_LIST";
        
        String phoneList = "INSERT INTO PHONE_LIST(PH_ID,PH_NUM,CL_ID,UCODE,CS_ID) " +
                "SELECT IFNULL(max(PH_ID),0)+1,?,?,?,? from PHONE_LIST";
        
        try {
            statement = null;
            statement = static_con.prepareStatement(deleteEmails);
            statement.setInt(1, client.getCode());
            statement.executeUpdate();
            //Adding Emails
            String[] emails = client.getEmails();
            
            for (int i = 0; i < emails.length; i++) {   //Inserting Emailss
                statement = null;
                if (emails[i] == null)
                    continue;
                
                statement = static_con.prepareStatement(emailList);
                statement.setString(1, emails[i]);
                statement.setInt(2, client.getCode());
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            }
            
            statement = null;
            statement = static_con.prepareStatement(deletePhones);
            statement.setInt(1, client.getCode());
            statement.executeUpdate();
            
            //Adding Phones
            String[] phones = client.getPhones();
            
            for (int i = 0; i < phones.length; i++) {   //Inserting Emailss
                statement = null;
                if (phones[i] == null)
                    continue;
                
                statement = static_con.prepareStatement(phoneList);
                statement.setString(1, phones[i]);
                statement.setInt(2, client.getCode());
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
            }
            
            for (int i = 0; i < emails.length; i++) {
                try {
                    if (emails[i] == null)
                        continue;
                    String[] t = emails[i].split("\\@");
                    System.out.println(t);
                    insertDomainsWhitelist(t[1]);
                } catch (Exception e) {
                    System.out.println(e);
                    continue;
                }
            }
            
            if (statement != null)
                statement.close();
            // doRelease(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void insertDomainsWhitelist(String domain) {
        String query = "INSERT INTO DOMAIN_LIST(DCODE,DNAME,DWB) " +
                " SELECT IFNULL(max(DCODE),0)+1,?,? from DOMAIN_LIST";
        
        PreparedStatement statement = null;

//        if (con == null)
//            con = getConnection();
        
        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, domain);
            statement.setInt(2, 1); //WhiteList
            statement.executeUpdate();
            if (statement != null)
                statement.close();
//            // doRelease(con);
        } catch (SQLException e) {
            System.out.println(e);
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
