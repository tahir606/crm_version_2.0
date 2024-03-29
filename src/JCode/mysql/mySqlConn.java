package JCode.mysql;

import JCode.FileHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import objects.*;

import javax.mail.Message;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mySqlConn {

    private final static String USER = "crm",
            PASSWORD = "crm123!@#",
            DBNAME = "bits_crm";
    private String URL;

    private FileHelper fHelper;
    private ESetting eSetting;

    private Users user;

    private static Connection static_con;

    private UserQueries userQueries;
    private StartupQueries startupQueries;
    private EmailQueries emailQueries;
    private SettingsQueries settingsQueries;
    private EmailPhoneQueries emailPhoneQueries;
    private ContactQueries contactQueries;
    private ClientQueries clientQueries;
    private ProductQueries productQueries;
    private LeadQueries leadQueries;
    private DomainQueries domainQueries;
    private NoteQueries noteQueries;
    private TaskQueries taskQueries;
    private EventQueries eventQueries;
    private ReportQueries reportQueries;
    private DocumentQueries documentQueries;

    public mySqlConn() {
        fHelper = new FileHelper();
        Network network = fHelper.getNetworkDetails();
        if (network == null)
            return;
        URL = "jdbc:mysql://" + network.getHost() + ":" + network.getPort() + "/" + DBNAME + "?allowMultiQueries=true&autoReconnect=true";

        user =FileHelper.ReadUserApiDetails();
        if (static_con == null)
            static_con = getConnection();

        userQueries = new UserQueries(static_con, fHelper);
        startupQueries = new StartupQueries(static_con, userQueries);
        settingsQueries = new SettingsQueries(static_con, fHelper);
        eSetting = getEmailSettings();
        emailPhoneQueries = new EmailPhoneQueries(static_con);
        noteQueries = new NoteQueries(static_con, fHelper);
        emailQueries = new EmailQueries(static_con, user, eSetting, emailPhoneQueries,noteQueries);
        contactQueries = new ContactQueries(static_con, fHelper, emailPhoneQueries, noteQueries);
        clientQueries = new ClientQueries(static_con, fHelper, emailPhoneQueries, noteQueries);
        leadQueries = new LeadQueries(static_con, fHelper, emailPhoneQueries, noteQueries);
        productQueries = new ProductQueries(static_con, fHelper, noteQueries);
        domainQueries = new DomainQueries(static_con);
        taskQueries = new TaskQueries(static_con, fHelper);
        eventQueries = new EventQueries(static_con, fHelper);
        reportQueries = new ReportQueries(static_con);
        documentQueries = new DocumentQueries(static_con);
    }

    private Connection getConnection() {
        int times = 1;

        if (static_con != null) {
            try {
                static_con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                System.out.println("Trying: " + times);
                Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection(
                        URL, USER, PASSWORD);
                return con;
            } catch (SQLException | ClassNotFoundException e) {
                times++;
                e.printStackTrace();
                System.out.println("Sleeping for 10 Seconds");
                try {
                    Thread.sleep(10000);
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

    public boolean getRights(UsersOld user) {
        return userQueries.getRights(user);
    }

    public String getUserName(int ucode) {
        return userQueries.getUserName(ucode);
    }

    public UsersOld getUserDetails(UsersOld user) {
        return userQueries.getUserDetails(user);
    }

    public UsersOld getUserDetails(int ucode) {
        UsersOld user = new UsersOld();
        user.setUserCode(ucode);
        return userQueries.getUserDetails(user);
    }

    public List<Users> getAllUsers() {
        return userQueries.getAllUsers();
    }

    public List<UsersOld.uRights> getAllUserRights() {
        return userQueries.getAllUserRights();
    }

    public void insertUpdateUser(UsersOld user, int choice) {
        userQueries.insertUpdateUser(user, choice);
    }

    public void deleteUser(UsersOld u) {
        userQueries.deleteUser(u);
    }

//    public void archiveUser(Users u) {
//        userQueries.archiveUser(u);
//    }

    public boolean checkAndCreateUser() {
        return startupQueries.checkAndCreateUser();
    }

    public boolean checkAndPopulateRights() {
        return startupQueries.checkAndPopulateRights();
    }

    public int getLatestEmailNo(int email_type) {
        return emailQueries.getLatestEmailNo(email_type);
    }

    public UsersOld getNoOfSolvedEmails(UsersOld user) {
        return emailQueries.getNoOfSolvedEmails(user);
    }

    public void createEmailRelations(EmailOld emailOld) {
        emailQueries.createEmailRelations(emailOld);
    }

    public List<ContactProperty> getEmailContactRelations(EmailOld emailOld) {
        return emailQueries.getEmailContactRelations(emailOld);
    }

    public List<ClientProperty> getEmailClientRelations(EmailOld emailOld) {
        return emailQueries.getEmailClientRelations(emailOld);
    }

    public int getNoOfLocked(UsersOld user) {
        return emailQueries.getNoOfLocked(user);
    }

    public int getNoOfUnsolved() {
        return emailQueries.getNoOfUnsolved();
    }

    public int getNoOfUnlocked() {
        return emailQueries.getNoOfUnlocked();
    }

    public void insertEmail(EmailOld emailOld, Message message) {
        emailQueries.insertEmail(emailOld, message);
    }

    public void insertEmailManual(EmailOld emailOld) {
        emailQueries.insertEmailManual(emailOld);
    }
//help
    public List<EmailOld> readAllEmails(Filters filters) {
        return emailQueries.readAllEmails(filters, userQueries);
    }

    public void insertEmailGeneral(EmailOld emailOld) {
        emailQueries.insertEmailGeneral(emailOld);
    }

    public List<EmailOld> readAllEmailsGeneral(String where) {
        return emailQueries.readAllEmailsGeneral(where);
    }

    public void insertEmailSent(EmailOld emailOld) {
        emailQueries.insertEmailSent(emailOld);
    }

    public List<EmailOld> readAllEmailsSent(String where) {
        return emailQueries.readAllEmailsSent(where);
    }

    public void lockEmail(EmailOld emailOld, int op) {
        emailQueries.lockEmail(emailOld, op);
    }

    public void solvEmail(EmailOld emailOld, String flag, UsersOld user, boolean choice, String msg) {
        emailQueries.solvEmail(emailOld, flag, user, choice, msg);
    }

    public void ArchiveEmail(int type, String where) {    //Verb
        emailQueries.ArchiveEmail(type, where);
    }

    public void markAsSent(EmailOld emailOld) {
        emailQueries.markAsSent(emailOld);
    }

    public boolean checkIfUserIsLoggedIn(Users user) {
        return settingsQueries.checkIfUserIsLoggedIn(user);
    }

    public void logUserOut(Users user) {
        settingsQueries.logUserOut(user);
    }

    public ESetting getEmailSettings() {
        return settingsQueries.getEmailSettings();
    }

    public void saveEmailSettings(ESetting eSetting) {
        settingsQueries.saveEmailSettings(eSetting);
    }

    public void checkOnNotificationSettings() {
        settingsQueries.checkOnNotificationSettings();
    }

    public void insertNotificationSettings(NotificationSettings nSettings) {
        settingsQueries.insertNotificationSettings(nSettings);
    }

    public NotificationSettings getNotificationSettings() {
        return settingsQueries.getNotificationSettings();
    }


    public boolean insertClient(ClientProperty client) {
        return clientQueries.insertClient(client);
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

    public ClientProperty getParticularClient(Client client) {
        return clientQueries.getParticularClient(client);
    }

    public List<ClientProperty> getAllClientsProperty(String where) {
        return clientQueries.getAllClientsProperty(where);
    }

    public List<String> getClientTypes() {
        return clientQueries.getClientTypes();
    }

    public boolean insertFromLead(ClientProperty client) {
        return clientQueries.insertFromLead(client);
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

    public ContactProperty getParticularContact(Contact contact) {
        return contactQueries.getParticularContact(contact);
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

    public ProductProperty getParticularProduct(ProductProperty product) {
        return productQueries.getParticularProduct(product);
    }

    public void insertProductModule(ProductModuleOld productModuleOld) {
        productQueries.insertProductModule(productModuleOld);
    }

    public void updateProductModule(ProductModuleOld productModuleOld) {
        productQueries.updateProductModule(productModuleOld);
    }

    public List<ProductModuleOld> getAllProductModules(int productCode) {
        return productQueries.getAllProductModules(productCode);
    }

    public ProductProperty getProductModuleStates(ProductProperty product) {
        return productQueries.getProductModuleStates(product);
    }

    public ArrayList<ProductModuleOld> getLockedModules() {
        return productQueries.getLockedModules();
    }

    public void deleteAllProductModules(int product) {
        productQueries.deleteAllProductModules(product);
    }

    public int getNewProductCode() {
        return productQueries.getNewProductCode();
    }

    public boolean lockModule(ProductModuleOld module) {
        return productQueries.lockModule(module);
    }

    public void unlockModule(ProductModuleOld module, String desc) {
        productQueries.unlockModule(module, desc);
    }

    public int getNewLeadCode() {
        return leadQueries.getNewLeadCode();
    }

    public void insertLead(LeadOld leadOld) {
        leadQueries.insertLead(leadOld);
    }

    public void updateLead(LeadOld leadOld) {
        leadQueries.updateLead(leadOld);
    }

    public List<LeadOld> getAllLeads(String where) {
        return leadQueries.getAllLeads(where);
    }

    public LeadOld getParticularLead(LeadOld leadOld) {
        return leadQueries.getParticularLead(leadOld);
    }

    public void checkAndPopulateSourcesOnCreation() {
        leadQueries.checkAndPopulateSourcesOnCreation();
    }

    public List<String> getAllSources() {
        return leadQueries.getAllSources(null);
    }

    public void markLeadAsClient(LeadOld leadOld) {
        leadQueries.markLeadAsClient(leadOld);
    }

    public void archiveLead(LeadOld leadOld) {
        leadQueries.archiveLead(leadOld);
    }

    public void addNote(String text, Contact contact) {
        noteQueries.addNewNote(text, contact);
    }

    public void addNote(String text, Client client) {
        noteQueries.addNewNote(text, client);
    }

    public void addNote(String text, LeadOld leadOld) {
        noteQueries.addNewNote(text, leadOld);
    }

    public void addNote(String text, ProductProperty product) {
        noteQueries.addNewNote(text, product);
    }

    public void updateNote(NoteOld noteOld, Contact contact) {
        noteQueries.updateNote(noteOld, contact);
    }

    public void updateNote(NoteOld noteOld, Client client) {
        noteQueries.updateNote(noteOld, client);
    }

    public void updateNote(NoteOld noteOld, LeadOld leadOld) {
        noteQueries.updateNote(noteOld, leadOld);
    }

    public void updateNote(NoteOld noteOld, ProductProperty product) {
        noteQueries.updateNote(noteOld, product);
    }

    public List<NoteOld> getContactNotes(ContactProperty contact) {
        return noteQueries.getNotes(contact);
    }

    public void deleteNote(NoteOld noteOld, Contact contact) {
        noteQueries.deleteNote(noteOld, contact);
    }

    public void deleteNote(NoteOld noteOld, Client client) {
        noteQueries.deleteNote(noteOld, client);
    }

    public void deleteNote(NoteOld noteOld, LeadOld leadOld) {
        noteQueries.deleteNote(noteOld, leadOld);
    }

    public void deleteNote(NoteOld noteOld, ProductProperty product) {
        noteQueries.deleteNote(noteOld, product);
    }

    public void addTask(TaskOld taskOld) {
        taskQueries.addTask(taskOld);
    }

    public void updateTask(TaskOld taskOld) {
        taskQueries.updateTask(taskOld);
    }

    public void closeTask(TaskOld taskOld) {
        taskQueries.closeTask(taskOld);
    }

    public void archiveTask(TaskOld taskOld) {
        taskQueries.archiveTask(taskOld);
    }

    public List<TaskOld> getAllTasks(String where) {
        return taskQueries.getAlLTasks(where);
    }

    public List<TaskOld> getTasks(Client obj) {
        return taskQueries.getTasks(obj);
    }

    public List<TaskOld> getTasks(ContactProperty obj) {
        return taskQueries.getTasks(obj);
    }

    public List<TaskOld> getTasks(LeadOld obj) {
        return taskQueries.getTasks(obj);
    }

    public List<TaskOld> getTasks(ProductProperty obj) {
        return taskQueries.getTasks(obj);
    }

    public void markNotified(TaskOld obj) {
        taskQueries.markNotified(obj);
    }

    public void addEvent(EventOld eventOld) {
        eventQueries.addEvent(eventOld);
    }

    public List<EventOld> getEvents(Client obj) {
        return eventQueries.getEvents(obj);
    }

    public List<EventOld> getEvents(LeadOld obj) {
        return eventQueries.getEvents(obj);
    }

    public List<EventOld> getAllEvents(String where) {
        return eventQueries.getAlLEvents(where);
    }

    public void updateEvent(EventOld eventOld) {
        eventQueries.updateEvent(eventOld);
    }

    public void closeEvent(EventOld eventOld) {
        eventQueries.closeEvent(eventOld);
    }

    public void archiveEvent(EventOld eventOld) {
        eventQueries.archiveEvent(eventOld);
    }

    public void markNotified(EventOld obj) {
        eventQueries.markNotified(obj);
    }

    public List<Users> ticketsSolvedByUser(String filter) {
        return reportQueries.ticketsSolvedByUser(filter);
    }

    public List<ClientProperty> emailsPerClient(String filter) {
        return reportQueries.emailsPerClient(filter);
    }

    public void insertDocument(Document document) throws Exception {
        documentQueries.insertDocument(document);
    }

    public List<Document> getAllDocuments() {
        return documentQueries.getAllDocuments();
    }

    public boolean deleteDocument(Document document) {
        return documentQueries.deleteDocument(document);
    }

    public String[] getAllEmailIDs(String where) {
        String query = "SELECT DISTINCT EM_NAME FROM EMAIL_LIST ";

        if (where == null)
            query = query + " ORDER BY EM_NAME";
        else
            query = query + where;

        try {
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


//    public static boolean pingHost(String host, int port, int timeout) {
//        try (Socket socket = new Socket()) {
//            socket.connect(new InetSocketAddress(host, port), timeout);
//            return true;
//        } catch (IOException e) {
//            return false; // Either timeout or unreachable or failed DNS lookup.
//        }
//    }

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

    public List<EmailProperty> readSolvedEmailsByUsers(Users newValue, String reportFilter) {
        return emailQueries.readSolvedEmailsByUsers(newValue,reportFilter);
    }

    public List<EmailProperty> average_Calculate() {
        return emailQueries.average_Calculate();
    }

    public int getManualTicketNo(EmailOld em) {
        return emailQueries.getManualTicketNo(em);
    }


    public void updateResendEmail(EmailOld emailOld) {
         emailQueries.updateResendEmail(emailOld);
    }

    public EmailOld readSearchEmail(int emailNo) {
        return emailQueries.readSearchEmail(emailNo, userQueries);
    }

    public void insertBlackListKeywords(String[] array) {
        domainQueries.insertBlackListKeywords(array);
    }

    public List<String> getBlackListKeyword() {
        return domainQueries.getBlackListKeyword();
    }

    public void updateReplacementKeyword(String saveKeyword) {
        settingsQueries.updateReplacementKeyword(saveKeyword);
    }

    public String getReplacementKeyword() {
        return settingsQueries.getReplacementKeyword();
    }

    public void removeKeyword(String selectedItem) {
        settingsQueries.removeKeyword(selectedItem);
    }

    public EmailOld getParticularEmail(EmailOld emailOld) {
        return emailQueries.getParticularEmail(emailOld);
    }

    public void addNote(String note, EmailOld emailOld) {
        noteQueries.addNewNote(note, emailOld);
    }

    public void deleteNote(NoteOld noteOld, EmailOld emailOld) {
        noteQueries.deleteNote(noteOld, emailOld);
    }

    public void updateNote(NoteOld noteOld, EmailOld emailOld) {
        noteQueries.updateNote(noteOld, emailOld);
    }

    public List<EmailProperty> clientReportWithDomain(ClientProperty clientProperty, String reportFilter) {
        return  reportQueries.clientReportWithDomain(clientProperty,reportFilter);
    }

    public List<ClientProperty> clientName() {
        return clientQueries.clientName();
    }

    public String getUserRight(int code) {
        return userQueries.getUserRight(code);

    }
}
