package JCode.mysql;

import JCode.fileHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import objects.*;

import javax.mail.Message;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.*;
import java.util.*;

public class mySqlConn {
    
    private static String USER;
    private static String PASSWORD;
    
    private static String URL;
    
    private fileHelper fHelper;
    private ESetting eSetting;
    
    private Users user;
    
    private static Connection static_con;
    
    private UserQueries userQueries;
    private EmailQueries emailQueries;
    private EmailSettingsQueries emailSettingsQueries;
    private ContactQueries contactQueries;
    private ClientQueries clientQueries;
    private ProductQueries productQueries;
    private DomainQueries domainQueries;
    
    public mySqlConn() {
        fHelper = new fileHelper();
        Network network = fHelper.getNetworkDetails();
        if (network == null)
            return;
        URL = "jdbc:mysql://" + network.getHost() + ":" + network.getPort() + "/" + network.getDbname() + "?allowMultiQueries=true";
        USER = network.getRoot();
        PASSWORD = network.getPass();
        user = fHelper.ReadUserDetails();
        if (static_con == null)
            static_con = getConnection();

        userQueries = new UserQueries(static_con, fHelper);
        emailSettingsQueries = new EmailSettingsQueries(static_con, fHelper);
        eSetting = getEmailSettings();
        emailQueries = new EmailQueries(static_con, user, eSetting);
        contactQueries = new ContactQueries(static_con, fHelper);
        clientQueries = new ClientQueries(static_con, fHelper);
        productQueries = new ProductQueries(static_con, fHelper);
        domainQueries = new DomainQueries(static_con);

        
    }
    
    private Connection getConnection() {
        int times = 1;
        
        while (true) {
            try {
                System.out.println("Trying times: " + times);
                Class.forName("com.mysql.jdbc.Driver");
//                System.out.println(URL + "\n" + USER + "\n" + PASSWORD);
                Connection con = DriverManager.getConnection(
                        URL, USER, PASSWORD);
                return con;
            } catch (SQLException | ClassNotFoundException e) {
                times++;
                System.out.println(e);
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                if (times == 10) {
                    showAlertDialog();
                    break;
                }
            }
        }
        return null;
    }
    
    public boolean authenticateLogin(String username, String password) {
        return userQueries.authenticateLogin(username, password);
    }
    
    public void setLogin(int ucode, boolean log) {
        userQueries.setLogin(ucode, log);
    }
    
    public boolean getRights(Users user) {
        return userQueries.getRights(user);
    }
    
    
    public String getUserName(int ucode) {
        return userQueries.getUserName(ucode);
    }
    
    public Users getUserDetails(Users user) {
        return userQueries.getUserDetails(user);
    }
    
    public List<Users> getAllUsers() {
        return userQueries.getAllUsers();
    }
    
    public List<Users.uRights> getAllUserRights() {
        return userQueries.getAllUserRights();
    }
    
    public void insertUpdateUser(Users user, int choice) {
        userQueries.insertUpdateUser(user, choice);
    }
    
    public void deleteUser(Users u) {
        userQueries.deleteUser(u);
    }
    
    public Users getNoOfSolvedEmails(Users user) {
        return emailQueries.getNoOfSolvedEmails(user);
    }
    
    public void createEmailRelations(Email email) {
        emailQueries.createEmailRelations(email);
    }
    
    public List<ContactProperty> getEmailContactRelations(Email email) {
        return emailQueries.getEmailContactRelations(email);
    }
    
    public List<ClientProperty> getEmailClientRelations(Email email) {
        return emailQueries.getEmailClientRelations(email);
    }
    
    public int getNoOfUnsolved() {
        return emailQueries.getNoOfUnsolved();
    }
    
    public int getNoOfUnlocked() {
        return emailQueries.getNoOfUnlocked();
    }
    
    public void insertEmail(Email email, Message message) {
        emailQueries.insertEmail(email, message);
    }
    
    public void insertEmailManual(Email email) {
        emailQueries.insertEmailManual(email);
    }
    
    public List<Email> readAllEmails(Filters filters) {
        return emailQueries.readAllEmails(filters, userQueries);
    }
    
    
    public void insertEmailGeneral(Email email) {
        emailQueries.insertEmailGeneral(email);
    }
    
    public List<Email> readAllEmailsGeneral(String where) {
        return emailQueries.readAllEmailsGeneral(where);
    }
    
    public void insertEmailSent(Email email) {
        emailQueries.insertEmailSent(email);
    }
    
    public List<Email> readAllEmailsSent(String where) {
        return emailQueries.readAllEmailsSent(where);
    }
    
    
    public void lockEmail(Email email, int op) {
        emailQueries.lockEmail(email, op);
    }
    
    public void solvEmail(Email email, String flag, Users user, boolean choice, String msg) {
        emailQueries.solvEmail(email, flag, user, choice, msg);
    }
    
    public void ArchiveEmail(int type, String where) {    //Verb
        emailQueries.ArchiveEmail(type, where);
    }
    
    public ESetting getEmailSettings() {
        return emailSettingsQueries.getEmailSettings();
    }
    
    public void saveEmailSettings(ESetting eSetting) {
        emailSettingsQueries.saveEmailSettings(eSetting);
    }
    
    public void insertClient(ClientProperty client) {
        clientQueries.insertClient(client);
    }
    
    public void updateClient(ClientProperty client) {
        clientQueries.updateClient(client);
    }
    
    public void insertDomainsWhitelist(String domain) {
        domainQueries.insertDomainsWhitelist(domain);
    }
    
    public void insertDomainsWhitelist(String[] list) {
        domainQueries.insertDomainsWhitelist(list);
    }
    
    public void updateDomainType(int type, String domain) {
        domainQueries.updateDomainType(type, domain);
    }
    
    public List<String> getWhiteBlackListDomains(int type) {
        return domainQueries.getWhiteBlackListDomains(type);
    }
    
    public int getNoClients() {
        return clientQueries.getNoClients();
    }
    
    public List<ClientProperty> getAllClients(String where) {
        return clientQueries.getAllClients(where);
    }
    
    public List<ClientProperty> getAllClientsProperty(String where) {
        return clientQueries.getAllClientsProperty(where);
    }
    
    public List<String> getClientTypes() {
        return clientQueries.getClientTypes();
    }
    
    public void insertContact(ContactProperty contact) {
        contactQueries.insertContact(contact);
    }
    
    public void updateContact(ContactProperty contact) {
        contactQueries.updateContact(contact);
    }
    
    public List<ContactProperty> getAllContactsProperty(String where) {
        return contactQueries.getAllContactsProperty(where);
    }
    
    public int getNewContactCode() {
        return contactQueries.getNewContactCode();
    }
    
    public void insertProduct(ProductProperty product) {
        productQueries.insertProduct(product);
    }
    
    public void updateProduct(ProductProperty product) {
        productQueries.updateProduct(product);
    }
    
    public List<ProductProperty> getAllProducts(String where) {
        return productQueries.getAllProducts(where);
    }
    
    public void insertProductModule(ProductModule productModule) {
        productQueries.insertProductModule(productModule);
    }
    
    public void updateProductModule(ProductModule productModule) {
        productQueries.updateProductModule(productModule);
    }
    
    public List<ProductModule> getAllProductModules(int productCode) {
        return productQueries.getAllProductModules(productCode);
    }
    
    public ProductProperty getProductModuleStates(ProductProperty product) {
        return productQueries.getProductModuleStates(product);
    }
    
    public ArrayList<ProductModule> getLockedModules() {
        return productQueries.getLockedModules();
    }
    
    public void deleteAllProductModules(int product) {
        productQueries.deleteAllProductModules(product);
    }
    
    public int getNewProductCode() {
        return productQueries.getNewProductCode();
    }
    
    public boolean lockModule(ProductModule module) {
        return productQueries.lockModule(module);
    }
    
    public void unlockModule(ProductModule module, String desc) {
        productQueries.unlockModule(module, desc);
    }
    
    
    private void EmailsListInsertion(String[] emails) {
        
        String emailList = "INSERT INTO EMAIL_LIST(EM_ID,EM_NAME,CL_ID,UCODE,CS_ID) " +
                "SELECT IFNULL(max(EM_ID),0)+1,?,?,?,? from EMAIL_LIST";
        
        try {
            PreparedStatement statement = null;
            
            for (int i = 0; i < emails.length; i++) {   //Inserting Emailss
                statement = null;
                if (emails[i] == null || emails[i].equals(""))
                    continue;
                
                statement = static_con.prepareStatement(emailList);
                statement.setString(1, emails[i]);
                statement.setInt(2, 0);
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
    
    public String[] getAllEmailIDs(String where) {
        String query = "SELECT DISTINCT EM_NAME FROM EMAIL_LIST ";
        
        if (where == null)
            query = query + " ORDER BY EM_NAME";
        else
            query = query + where;
        
        Connection con = null;
        try {
            con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            
            set.last();
            int size = set.getRow();
            set.beforeFirst();
            
            String[] ems = new String[size];
            
            int c = 0;
            while (set.next()) {
                ems[c] = set.getString("EM_NAME");
                c++;
            }
            
            return ems;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // doRelease(con);
        }
        
        return null;
    }
    
    
    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
    
    private void showAlertDialog() {
        
        Alert alert2 = new Alert(Alert.AlertType.ERROR, "Cannot Connect to the Database!",
                ButtonType.OK);
        alert2.showAndWait();
        
        if (alert2.getResult() == ButtonType.OK) {
            System.exit(0);
        }
        
    }
    
    private void doRelease(Connection con) {
        
        try {
            if (con != null)
                static_con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    
}
