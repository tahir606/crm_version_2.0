package JCode;

import objects.ESetting;
import objects.Email;
import objects.Users;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class mySqlConn {

    private static String USER;
    private static String PASSWORD;

    private static String URL;

    private fileHelper fHelper;

    private emailControl eControl;

    private Users user;

    public mySqlConn() {
        fHelper = new fileHelper();
        eControl = new emailControl();
        String[] network = fHelper.getNetworkDetails();
        URL = "jdbc:mysql://" + network[0] + ":" + network[1] + "/" + network[2];
        USER = network[3];
        PASSWORD = network[4];

        user = fHelper.ReadUserDetails();
    }

    private Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean authenticateLogin(String username, String password) {

        String query = "SELECT UCODE, FNAME, URIGHT, ISEMAIL FROM USERS " +
                "WHERE UNAME = ? " +
                "AND UPASS = ? " +
                "AND FREZE = ?";

        Connection con = getConnection();

        Users user = new Users();

        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, "N");
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                user.setUCODE(set.getInt(1));
                user.setFNAME(set.getString(2));
                user.setUright(set.getString(3));
                if (set.getString(4).equals("Y")) {
                    user.setEmailBool(true);
                } else {
                    user.setEmailBool(false);
                }
            }

            if (user.getUCODE() == '\0') {
                return false;
            }

            user.setUNAME(username);

            return getRights(user, con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean getRights(Users user, Connection con) {

        String query1 = "SELECT  RL.RCODE, RL.RNAME FROM RIGHTS_CHART RC, RIGHTS_LIST RL" +
                " WHERE RC.UCODE = ?" +
                " AND RC.RCODE = RL.RCODE" +
                " AND FREZE = 'N'";

        String query2 = "SELECT RCODE, RNAME FROM RIGHTS_LIST" +
                " WHERE FREZE = 'N'";

        ArrayList<Users.uRights> userRights = new ArrayList<>();

        PreparedStatement statement;
        ResultSet set = null;

        try {
            if (user.getUright().equals("Admin")) {
                statement = con.prepareStatement(query2);
                set = statement.executeQuery();
            } else {
                statement = con.prepareStatement(query1);
                statement.setInt(1, user.getUCODE());
                set = statement.executeQuery();
            }


            while (set.next()) {
                userRights.add(new Users.uRights(set.getString(1), set.getString(2)));
            }

            return fHelper.WriteUserDetails(user, userRights);


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }

        return false;

    }


    public String getUserName(Connection con, int ucode) { //For Locking Names

        String query = " SELECT FNAME FROM USERS " +
                " WHERE UCODE = ?";

        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, ucode);
            set = statement.executeQuery();

            while (set.next()) {
                return set.getString("FNAME");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "";

    }

    public Users getUserDetails(Users user) {
        String query = " SELECT FNAME, SOLV, LOCKD FROM USERS " +
                " WHERE UCODE = ?";

        Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, user.getUCODE());
            set = statement.executeQuery();

            while (set.next()) {
                user.setLocked(set.getInt("LOCKD"));
                user.setSolved(set.getInt("SOLV"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public List<Users> getAllUsers() {

        List<Users> userList = new ArrayList<>();

        String query = "SELECT UCODE, UNAME, FNAME, EMAIL, UPASS, URIGHT, FREZE, ISEMAIL FROM USERS";

        String query2 = "SELECT RCODE FROM RIGHTS_CHART WHERE UCODE = ?";

        Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = con.prepareStatement(query);
            set = statement.executeQuery();

            while (set.next()) {
                Users user = new Users();
                user.setUCODE(set.getInt("UCODE"));
                user.setUNAME(set.getString("UNAME"));
                user.setFNAME(set.getString("FNAME"));
                user.setEmail(set.getString("EMAIL"));
                user.setPassword(set.getString("UPASS"));
                if (set.getString("UNAME").equals("Y")) {
                    user.setFreeze(true);
                } else {
                    user.setFreeze(false);
                }
                if (set.getString("ISEMAIL").equals("Y")) {
                    user.setEmailBool(true);
                } else {
                    user.setEmailBool(false);
                }
                user.setUright(set.getString("URIGHT"));

                if (!user.getUright().equalsIgnoreCase("Admin")) {
                    ArrayList<Users.uRights> rights = new ArrayList<>();
                    PreparedStatement statement1 = con.prepareStatement(query2);
                    statement1.setInt(1, user.getUCODE());
                    ResultSet set1 = statement1.executeQuery();
                    while (set1.next()) {
                        Users.uRights r = new Users.uRights(set1.getInt("RCODE"), "");
                        rights.add(r);
                    }
                    user.setuRightsList(rights);
                }

                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }

        return userList;
    }

    public List<Users.uRights> getAllUserRights() {

        List<Users.uRights> rightsList = new ArrayList<>();

        String query = "SELECT RCODE, RNAME FROM RIGHTS_LIST WHERE FREZE = 'N'";

        Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {

            statement = con.prepareStatement(query);
            set = statement.executeQuery();

            while (set.next()) {
                Users.uRights r = new Users.uRights();
                r.setRCODE(set.getInt("RCODE"));
                r.setRNAME(set.getString("RNAME"));
                rightsList.add(r);
            }

            return rightsList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }

        return rightsList;
    }

    public void insertUpdateUser(Users user, int choice) {
        String query = "";

        if (choice == 0) {          //New
            query = " INSERT INTO USERS( UCODE ,  FNAME ,  UNAME ,  Email ,  UPASS ,  URIGHT ,  FREZE , " +
                    "  ISEMAIL ) SELECT IFNULL(max(UCODE),0)+1,?,?,?,?,?,?,? from USERS";
        } else if (choice == 1) {   //Update
            query = "UPDATE USERS SET  FNAME =?, UNAME =?, Email =?, " +
                    " UPASS =?, URIGHT =?, FREZE =?, ISEMAIL =? WHERE UCODE = ? ";
        }

        String delete = "DELETE FROM RIGHTS_CHART WHERE UCODE = ?";
        String insert = "INSERT INTO RIGHTS_CHART (RCODE, UCODE) VALUES (?,?)";


        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFNAME());
            statement.setString(2, user.getUNAME());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUright());
            if (user.isFreeze()) {
                statement.setString(6, "Y");
            } else {
                statement.setString(6, "N");
            }
            if (user.isEmail()) {
                statement.setString(7, "Y");
            } else {
                statement.setString(7, "N");
            }
            if (choice == 1)
                statement.setInt(8, user.getUCODE());

            statement.executeUpdate();
            statement = null;

            if (!user.getUright().equals("Admin")) {
                statement = con.prepareStatement(delete);
                statement.setInt(1, user.getUCODE());
                statement.executeUpdate();

                for (Users.uRights u : user.getRights()) {
                    statement = null;
                    statement = con.prepareStatement(insert);
                    statement.setInt(1, u.getRCODE());
                    statement.setInt(2, user.getUCODE());
                    statement.executeUpdate();
                }
            }

            doRelease(con);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(Users u) {

        String query = "DELETE FROM USERS WHERE UCODE = ?";

        Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement(query);
            statement.setInt(1, u.getUCODE());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }

    }


    public void insertEmail(Email email, Message message) {

        String query = "INSERT INTO email_store(EMNO,SBJCT,TOADD,FRADD,TSTMP,EBODY,ATTCH,CCADD,ESOLV,MSGNO,LOCKD) " +
                " SELECT IFNULL(max(EMNO),0)+1,?,?,?,?,?,?,?,?,?,? from EMAIL_STORE";

        Connection con = getConnection();
        PreparedStatement statement = null;

        System.out.println(email.getTimestamp());

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, email.getSubject());
            statement.setString(2, email.getToAddressString());
            statement.setString(3, email.getFromAddressString());
            statement.setString(4, email.getTimestamp());
            statement.setString(5, email.getBody());
            statement.setString(6, email.getAttch());
            statement.setString(7, email.getCcAddressString());
            statement.setString(8, String.valueOf(email.getSolvFlag()));
            statement.setInt(9, email.getMsgNo());
            statement.setInt(10, email.getLockd());
            statement.executeUpdate();

            statement.close();

            autoReply(con, email, message);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void autoReply(Connection con, Email email, Message message) {
        String queryEMNO = "SELECT emno FROM email_store" +
                " WHERE msgno = ?" +
                " AND sbjct = ? " +
                " AND tstmp = ?";

        try {

            PreparedStatement statementEMNO = con.prepareStatement(queryEMNO);
            statementEMNO.setInt(1, email.getMsgNo());
            statementEMNO.setString(2, email.getSubject());
            statementEMNO.setString(3, email.getTimestamp());
            ResultSet set = statementEMNO.executeQuery();

            int emno = 0;
            //" Mr. " + RecieveName + "\n" +
            while (set.next()) {
                emno = set.getInt(1);
            }
            String body = "Thank you for contacting Burhani Customer Service \n" +
                    " Your complaint has been successfully registered. \n \n " +
                    " The Ticket Number that has been issued to you is:    " + emno + "\n \n" +
                    " Our IT department has started working to resolve your issue. " +
                    " We will notify you of any further development.";

            eControl.sendEmail("Burhani Customer Relationship Manager", email.getFromAddress()[0].toString(), "", "",
                    body, "", "", message);
            statementEMNO.close();
            set.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }
    }

    public List<Email> readAllEmails(String where) {

        String query = "SELECT EMNO,MSGNO,SBJCT,FRADD,TOADD,CCADD,TSTMP,EBODY,ATTCH,ESOLV,LOCKD FROM EMAIL_STORE";

        if (where == null) {
            query = query + " ORDER BY EMNO DESC";
        } else {
            query = query + where;
        }

        List<Email> allEmails = new ArrayList<>();

        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            System.out.println(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }

            while (set.next()) {
                Email email = new Email();
                email.setEmailNo(set.getInt("EMNO"));
                email.setMsgNo(set.getInt("MSGNO"));
                email.setSubject(set.getString("SBJCT"));
                email.setTimestamp(set.getString("TSTMP"));
                email.setBody(set.getString("EBODY"));
                email.setAttch(set.getString("ATTCH"));
                email.setSolvFlag(set.getString("ESOLV").charAt(0));
                email.setLockd(set.getInt("LOCKD"));
                if (set.getInt("LOCKD") == '\0') {
                    email.setLockedByName("Unlocked");
                } else {
                    email.setLockedByName(getUserName(con, email.getLockd())); //Getting name of username that locked
                }                                                              // particular email

                //------From Address
                String[] from = set.getString("FRADD").split("\\^");
                Address[] fromAddress = new Address[from.length];
                for (int i = 1, j = 0; i < from.length; i++, j++) {
                    try {
                        fromAddress[j] = new InternetAddress(from[i]);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
                email.setFromAddress(fromAddress);

                //-----To Address
                String[] to = set.getString("TOADD").split("\\^");
                Address[] toAddress = new Address[to.length];
                for (int i = 1, j = 0; i < to.length; i++, j++) {
                    try {
                        toAddress[j] = new InternetAddress(to[i]);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
                email.setToAddress(toAddress);

                //----- CC Address
                String[] cc = set.getString("CCADD").split("\\^");
                Address[] ccAddress = new Address[cc.length];
                for (int i = 1, j = 0; i < cc.length; i++, j++) {
                    try {
                        ccAddress[j] = new InternetAddress(cc[i]);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
                email.setCcAddress(ccAddress);

                allEmails.add(email);
            }

            doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allEmails;
    }

    public void lockEmail(Email email, int op) {        //0 Unlock 1 Lock

        String query = " UPDATE EMAIL_STORE " +
                " SET LOCKD = ? " +
                " WHERE EMNO = ? ";

        Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement(query);

            if (op == 1) {  //Locking
                statement.setInt(1, user.getUCODE());
                statement.setInt(2, email.getEmailNo());
            } else if (op == 0) {   //Unlocking
                statement.setInt(1, 0);
                statement.setInt(2, email.getEmailNo());
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }


    }

    public void solvEmail(Email email, String flag, Users user) {

        String query = " UPDATE EMAIL_STORE " +
                " SET ESOLV = ?, LOCKD = ? " +
                " WHERE EMNO = ? ";

        String query2 = "UPDATE USERS SET SOLV = " +
                " ((select IFNULL(SOLV,0) + 1 AS SOLV from ( select SOLV from users where UCODE = ?) as x))" +
                " WHERE UCODE = ?";

        Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, flag);
            statement.setInt(2, 0);
            statement.setInt(3, email.getEmailNo());
            statement.executeUpdate();
            statement.close();

            statement = con.prepareStatement(query2);
            statement.setInt(1, user.getUCODE());
            statement.setInt(2, user.getUCODE());
            statement.executeUpdate();
            statement.close();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            doRelease(con);
        }


    }

    public ESetting getEmailSettings() {

        String query = "SELECT HOST, EMAIL, PASS, FSPATH FROM EMAIL_SETTINGS WHERE 1";

        Connection con = getConnection();
        try {
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            ESetting eSetting;
            while (set.next()) {
                eSetting = new ESetting(set.getString("HOST"), set.getString("EMAIL"), set.getString("PASS"), set.getString("FSPATH"));
                return eSetting;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public void saveEmailSettings(ESetting eSetting) {

//        String query = "INSERT INTO EMAIL_SETTINGS(ECODE,HOST,EMAIL,PASS) " +
//                " SELECT IFNULL(max(ECODE),0)+1,?,?,? from EMAIL_SETTINGS";

        String query = "UPDATE EMAIL_SETTINGS SET HOST = ?,EMAIL = ?, PASS = ?, FSPATH = ? WHERE ECODE = 1";

        Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement(query);
            statement.setString(1, eSetting.getHost());
            statement.setString(2, eSetting.getEmail());
            statement.setString(3, eSetting.getPass());
            statement.setString(4, eSetting.getFspath());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean pingHost(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    private void doRelease(Connection con) {

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
