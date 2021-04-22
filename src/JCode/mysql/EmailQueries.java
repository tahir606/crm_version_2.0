package JCode.mysql;

import Email.EResponse.EResponseController;
import JCode.CommonTasks;
import objects.*;

import javax.mail.Address;
import javax.mail.Message;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmailQueries {

    private Connection static_con;
    private ESetting eSetting;
    private Users user;
    private EmailPhoneQueries emailPhoneQueries;
    private NoteQueries noteQueries;

    public EmailQueries(Connection static_con, Users user, ESetting eSetting, EmailPhoneQueries emailPhoneQueries, NoteQueries
            noteQueries) {
        this.static_con = static_con;
        this.user = user;
        this.eSetting = eSetting;
        this.emailPhoneQueries = emailPhoneQueries;
        this.noteQueries = noteQueries;
    }

    public UsersOld getNoOfSolvedEmails(UsersOld user) {
        String query = " SELECT COUNT(EMNO) AS EMNO FROM EMAIL_STORE " +
                " WHERE SOLVBY = ?";

//        // Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = static_con.prepareStatement(query);
//            statement.setInt(1, user.getUCODE());
            statement.setInt(1, user.getUserCode());
            set = statement.executeQuery();

            while (set.next()) {
                user.setSolved(set.getInt("EMNO"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public void createEmailRelations(EmailOld emailOld) {
//        try {
//            for (Address address : email.getFromAddress()) {
//                subCreateEmailRelation(address, email);
//            }
//            if (email.getCcAddress() != null) {
//                for (Address address : email.getCcAddress()) {
//                    subCreateEmailRelation(address, email);
//                }
//            }
//        } catch (SQLException e) {
//            e.getLocalizedMessage();
//        }
    }

    private String mainQuery = "SELECT DISTINCT EM_ID, EM_NAME, CL_ID, CS_ID, UCODE FROM EMAIL_LIST WHERE EM_NAME LIKE ";
    private String relQuery = "INSERT INTO EMAIL_RELATION (EMNO, EM_ID, EMTYPE, CL_ID, UCODE, CS_ID) VALUES (?, ?, ?, ?, ?, ?)";

    private void subCreateEmailRelation(Address address, EmailOld emailOld) throws SQLException {
        if (address != null) {
            String splitted = "";
            try {
                splitted = address.toString().split("\\<")[1];
                splitted = splitted.split("\\>")[0];    //Split email into only the bare minimum to scan
            } catch (Exception e) {
                System.out.println(e);
                splitted = address.toString();
            }
            PreparedStatement statement = static_con.prepareStatement(mainQuery + " '%" + splitted + "%'");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int emno = emailOld.getCode(),
                        emid = set.getInt("EM_ID"),
                        cl = set.getInt("CL_ID"),
                        cs = set.getInt("CS_ID"),
                        ucode = set.getInt("UCODE");

                if (cl == 0 && cs == 0 && ucode == 0)   //Unrelated Email
                    continue;

                PreparedStatement stmnt = static_con.prepareStatement(relQuery);
                stmnt.setInt(1, emno);
                stmnt.setInt(2, emid);
                stmnt.setInt(3, 1);
                stmnt.setInt(4, cl);
                stmnt.setInt(5, ucode);
                stmnt.setInt(6, cs);
                stmnt.executeUpdate();

            }
        }
    }

    public List<ContactProperty> getEmailContactRelations(EmailOld emailOld) {
        String query = "SELECT DISTINCT CS.CS_ID, CS_FNAME, CS_LNAME " +
                "FROM CONTACT_STORE as CS, EMAIL_RELATION as ER " +
                "WHERE CS.CS_ID = ER.CS_ID " +
                "AND ER.EMNO = ?";

        PreparedStatement statement = null;
        List<ContactProperty> contacts = new ArrayList<>();
        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, emailOld.getCode());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ContactProperty c = new ContactProperty();
                c.setCode(set.getInt("CS_ID"));
                c.setFirstName(set.getString("CS_FNAME"));
                c.setLastName(set.getString("CS_LNAME"));
                contacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contacts;
    }

    public List<ClientProperty> getEmailClientRelations(EmailOld emailOld) {
        String query = "SELECT DISTINCT CS.CL_ID, CL_NAME " +
                " FROM CLIENT_STORE AS CS, EMAIL_RELATION as ER " +
                " WHERE CS.CL_ID = ER.CL_ID " +
                " AND ER.CL_ID != 0 " +
                " AND ER.EMNO = ?";

        PreparedStatement statement = null;
        List<ClientProperty> clients = new ArrayList<>();
        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, emailOld.getCode());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ClientProperty c = new ClientProperty();
                c.setCode(set.getInt("CL_ID"));
                c.setName(set.getString("CL_NAME"));
                clients.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    //Locked Emails by you
    public int getNoOfLocked(UsersOld user) {
        String query = "SELECT COUNT(EMNO) AS EMNO FROM EMAIL_STORE " +
                "WHERE LOCKD = ? " +
                "AND ESOLV != 'S' " +
                "AND FREZE = 0";

//        // Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, user.getUserCode());
            set = statement.executeQuery();

            while (set.next()) {
                return set.getInt("EMNO");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getNoOfUnsolved() {
        String query = " SELECT COUNT(EMNO) AS EMNO FROM EMAIL_STORE " +
                " WHERE ESOLV != 'S' AND FREZE = 0";

//        // Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = static_con.prepareStatement(query);
            set = statement.executeQuery();

            while (set.next()) {
                return set.getInt("EMNO");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int getNoOfUnlocked() {
        String query = " SELECT COUNT(EMNO) AS EMNO FROM EMAIL_STORE " +
                " WHERE LOCKD = 0 AND ESOLV != 'S' AND FREZE = 0";

//        // Connection con = getConnection();
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            statement = static_con.prepareStatement(query);
            set = statement.executeQuery();

            while (set.next()) {
                return set.getInt("EMNO");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void insertEmail(EmailOld emailOld, Message message) {

//        if (checkForRelatedEmails(email))
//            return;

        String query = "INSERT INTO email_store(EMNO,SBJCT,TOADD,FRADD,TSTMP,EBODY,ATTCH,CCADD,ESOLV,MSGNO,LOCKD," +
                "FREZE) SELECT IFNULL(max(EMNO),0)+1,?,?,?,?,?,?,?,?,?,?,? from EMAIL_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, emailOld.getSubject());
//            statement.setString(2, email.getToAddressString());
//            statement.setString(3, email.getFromAddressString());
//            statement.setString(4, email.getTimestamp());
            statement.setString(5, emailOld.getBody());
//            statement.setString(6, email.getAttachment());
//            statement.setString(7, email.getCcAddressString());
//            statement.setString(8, String.valueOf(email.getSolved()));
            statement.setInt(9, emailOld.getMessageNo());
            statement.setInt(10, emailOld.getLocked());
            statement.setInt(11, emailOld.getFreeze());
            statement.executeUpdate();

            statement.close();

            int emno = getEmailNo(emailOld);
            emailOld.setCode(emno);

            createEmailRelations(emailOld);

            if (eSetting.isAutot())
                autoReply(emailOld, message);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /// help
    public void insertEmailManual(EmailOld emailOld) {

        String query = "INSERT INTO email_store(EMNO,SBJCT,TOADD,FRADD,TSTMP,EBODY,ATTCH,CCADD,ESOLV,MSGNO,LOCKD," +
                "FREZE,MANUAL) SELECT IFNULL(max(EMNO),0)+1,?,?,?,?,?,?,?,?,?,?,?,? from EMAIL_STORE";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, emailOld.getSubject());
//            statement.setString(2, email.getToAddressString());
//            statement.setString(3, email.getFromAddressString());
//            statement.setString(4, email.getTimestamp());
            statement.setString(5, emailOld.getBody());
//            statement.setString(6, email.getAttachment());
//            statement.setString(7, email.getCcAddressString());
            statement.setString(8, "N");
            statement.setInt(9, emailOld.getMessageNo());
            statement.setInt(10, emailOld.getLocked());
            statement.setInt(11, emailOld.getFreeze());
            statement.setInt(12, emailOld.getManualEmail());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //Store email tickets related
    public boolean checkForRelatedEmails(EmailOld emailOld) {
        List<Integer> ints = checkIfEmailIsRelated(emailOld.getSubject());

        if (ints == null || ints.size() == 0)
            return false;
        else {
            for (int i : ints) {
                emailOld.setCode(i);
                insertEmailRelated(emailOld);
            }
            return true;
        }
    }

    private List<Integer> checkIfEmailIsRelated(String subject) {

        String query = "SELECT EMNO FROM EMAIL_STORE " +
                " WHERE SBJCT LIKE '%" + subject.trim() + "%' AND ESOLV = 'N' AND FREZE = 0 ";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();

            if (!set.isBeforeFirst()) {
                return null;
            }

            List<Integer> list = new ArrayList<>();

            while (set.next()) {
                list.add(set.getInt("EMNO"));
            }

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void insertEmailRelated(EmailOld emailOld) {

        String query = "INSERT INTO email_store_related(ESR_ID,EMNO,SBJCT,TOADD,FRADD,TSTMP,EBODY,ATTCH,CCADD,MSGNO)" +
                " SELECT IFNULL(max(ESR_ID),0)+1,?,?,?,?,?,?,?,?,? from email_store_related";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setInt(1, emailOld.getCode());
            statement.setString(2, emailOld.getSubject());
//            statement.setString(3, email.getToAddressString());
//            statement.setString(4, email.getFromAddressString());
//            statement.setString(5, email.getTimestamp());
            statement.setString(6, emailOld.getBody());
//            statement.setString(7, email.getAttachment());
//            statement.setString(8, email.getCcAddressString());
            statement.setInt(9, emailOld.getMessageNo());
            statement.executeUpdate();

            statement.close();

//            int emno = getEmailNo(email);
//            email.setEmailNo(emno);

//            createEmailRelations(email);

//            if (eSetting.isAuto())
//                autoReply(email, message);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private List<EmailOld> readRelatedEmails(EmailOld where) {     //Emails related to every Emails

        String query = "SELECT EMNO, MSGNO, SBJCT, FRADD, TOADD, CCADD, TSTMP, " +
                " EBODY, ATTCH FROM EMAIL_STORE_RELATED WHERE EMNO = ? ";

        List<EmailOld> allEmailOlds = new ArrayList<>();

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, where.getCode());
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            while (set.next()) {
                EmailOld emailOld = new EmailOld();
                emailOld.setCode(set.getInt("EMNO"));
                emailOld.setMessageNo(set.getInt("MSGNO"));
                emailOld.setSubject(set.getString("SBJCT"));
//                email.setTimestamp(set.getString("TSTMP"));
//                email.setTimeFormatted(CommonTasks.getTimeFormatted(email.getTimestamp()));

                emailOld.setBody(set.getString("EBODY"));
//                email.setAttachment(set.getString("ATTCH"));
                //------From Address
                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setFromAddress(fromAddress);

                //-----To Address
                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setToAddress(toAddress);

                //----- CC Address
//                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setCcAddress(ccAddress);

                allEmailOlds.add(emailOld);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return allEmailOlds;
    }


    public static String autoReplySubject = "Burhani Customer Support - Ticket Number: ";

    private void autoReply(EmailOld emailOld, Message message) {
        String body = "<h3>The Ticket Number Issued to you is: <b>" + emailOld.getCode() + "</b></h3>\n" + eSetting.getAutotextt();

        EmailOld e = new EmailOld();
        e.setSubject(autoReplySubject + emailOld.getCode());
//        e.setToAddress(new Address[]{email.getFromAddress()[0]});
        e.setBody(body + "<br><br><br>" + "--------In Reply To--------" + "<br><br><h4>Subject:   <b>" + emailOld.getSubject() + "</b></h4><br><br>" + emailOld.getBody());
//        emailControl.sendEmail(e, message);

    }

    public int getLatestEmailNo(int email_type) {

        String tablename = null;
        switch (email_type) {
            case 1:
                tablename = "EMAIL_STORE";
                break;
            case 2:
                tablename = "EMAIL_GENERAL";
                break;
        }

        String query = "SELECT MAX(EMNO) FROM " + tablename +
                " WHERE FREZE = 0";

        try {

            PreparedStatement statementEMNO = static_con.prepareStatement(query);
            ResultSet set = statementEMNO.executeQuery();

            int emno = 0;
            while (set.next()) {
                emno = set.getInt(1);
            }

            statementEMNO.close();
            set.close();

            return emno;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // doRelease(con);
        }

        return 0;

    }

    private int getEmailNo(EmailOld emailOld) {
        String queryEMNO = "SELECT emno FROM email_store" +
                " WHERE msgno = ?" +
                " AND sbjct = ? " +
                " AND tstmp = ?";

        try {

            PreparedStatement statementEMNO = static_con.prepareStatement(queryEMNO);
            statementEMNO.setInt(1, emailOld.getMessageNo());
            statementEMNO.setString(2, emailOld.getSubject());
//            statementEMNO.setString(3, email.getTimestamp());
            ResultSet set = statementEMNO.executeQuery();

            int emno = 0;
            //" Mr. " + RecieveName + "\n" +
            while (set.next()) {
                emno = set.getInt(1);
            }

            statementEMNO.close();
            set.close();

            return emno;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // doRelease(con);
        }

        return 0;
    }

    // read solved emails by each user and display with two filters user select and days selection
    public List<EmailProperty> readSolvedEmailsByUsers(Users usersOld, String reportFilter) {

        String query = "SELECT EMNO, SBJCT, FRADD, TSTMP, LOCKTIME, SOLVTIME " +
                " FROM EMAIL_STORE " +
                " WHERE SOLVBY = ? " + reportFilter +
                " AND FREZE = 0 ";

        List<EmailProperty> allEmails = new ArrayList<>();
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, usersOld.getUserCode());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                EmailProperty email = new EmailProperty();
                email.setEmail_No(set.getInt("EMNO"));
                email.setSubject(set.getString("SBJCT"));
                email.setFrom_Address(set.getString("FRADD").replace("^", " "));
//                Document doc = Jsoup.parse(set.getString("EBODY"));
//                String text = doc.body().text();
//                email.setEmail_Body(text);
                email.setTimestamp(CommonTasks.getTimeFormatted(set.getString("TSTMP")));
                email.setLock_time(CommonTasks.getTimeFormatted(set.getString("LOCKTIME")));
                email.setSolve_Time(CommonTasks.getTimeFormatted(set.getString("SOLVTIME")));
                email.setDuration(CommonTasks.getTimeDuration(set.getString("LOCKTIME"), set.getString("SOLVTIME")));
                allEmails.add(email);
            }


        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
        }
        return allEmails;

    }

    /// help
    //Reading tickets
    public List<EmailOld> readAllEmails(Filters filters, UserQueries userQueries) {

        String query = "SELECT EMNO, MSGNO, SBJCT, FRADD, TOADD, CCADD, TSTMP, " +
                " EBODY, ATTCH, ESOLV, LOCKD, LOCKTIME, SOLVBY, SOLVTIME, MANUAL FROM EMAIL_STORE";

        if (filters == null) {
            query = query + " ORDER BY EMNO DESC";
        } else {
            query = query + " WHERE " + filters.toString();
        }

        List<EmailOld> allEmailOlds = new ArrayList<>();

        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            mySqlConn sql = null;
            while (set.next()) {
                EmailOld emailOld = new EmailOld();
                emailOld.setCode(set.getInt("EMNO"));
                emailOld.setMessageNo(set.getInt("MSGNO"));
                emailOld.setSubject(set.getString("SBJCT"));
                emailOld.setTimestamp(set.getString("TSTMP"));
                emailOld.setTimeFormatted(CommonTasks.getTimeFormatted(emailOld.getTimestamp()));

                emailOld.setBody(set.getString("EBODY"));
//                email.setAttachment(set.getString("ATTCH"));
//                email.setSolved(set.getString("ESOLV").charAt(0));
                emailOld.setLocked(set.getInt("LOCKD"));
                emailOld.setLockedTime(set.getString("LOCKTIME"));
                emailOld.setSolvedTime(set.getString("SOLVTIME"));
                emailOld.setManualEmail(set.getInt("MANUAL"));
                if (emailOld.getManualEmail() != '\0') {
                    if (sql == null) sql = new mySqlConn();
                    emailOld.setCreatedBy(sql.getUserName(emailOld.getManualEmail()));
                }
                if (set.getInt("LOCKD") == '\0') {
                    emailOld.setLockedByName("Unlocked");
                } else {
                    emailOld.setLockedByName(userQueries.getUserName(emailOld.getLocked())); //Getting name of username that
                    // locked
                }                                                              // particular email


                //------From Address
                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setFromAddress(fromAddress);

                //-----To Address
                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setToAddress(toAddress);

                //----- CC Address
                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setCcAddress(ccAddress);
////
//                email.setRelatedContacts(getEmailContactRelations(email));
//                email.setRelatedClients(getEmailClientRelations(email));
//
//                email.setRelatedEmails(readRelatedEmails(email));
                allEmailOlds.add(emailOld);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allEmailOlds;
    }


    public void insertEmailGeneral(EmailOld emailOld) {

        String query = "INSERT INTO email_general(EMNO,SBJCT,TOADD,FRADD,TSTMP,EBODY,ATTCH,CCADD,MSGNO," +
                "FREZE) SELECT IFNULL(max(EMNO),0)+1,?,?,?,?,?,?,?,?,? from EMAIL_GENERAL";

        // Connection con = getConnection();
        PreparedStatement statement = null;


        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, emailOld.getSubject());
//            statement.setString(2, email.getToAddressString());
//            statement.setString(3, email.getFromAddressString());
//            statement.setString(4, email.getTimestamp());
            statement.setString(5, emailOld.getBody());
//            statement.setString(6, email.getAttachment());
//            statement.setString(7, email.getCcAddressString());
            statement.setInt(8, emailOld.getMessageNo());
            statement.setInt(9, emailOld.getFreeze());
            statement.executeUpdate();

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<EmailOld> readAllEmailsGeneral(String where) {

        String query = "SELECT EMNO,MSGNO,SBJCT,FRADD,TOADD,CCADD,TSTMP,EBODY,ATTCH FROM EMAIL_GENERAL";

        if (where == null) {
            query = query + " ORDER BY EMNO DESC";
        } else {
            query = query + where + " ORDER BY EMNO DESC";
        }

        List<EmailOld> allEmailOlds = new ArrayList<>();

        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }

            while (set.next()) {
                EmailOld emailOld = new EmailOld();
                emailOld.setCode(set.getInt("EMNO"));
                emailOld.setMessageNo(set.getInt("MSGNO"));
                emailOld.setSubject(set.getString("SBJCT"));
//                email.setTimestamp(set.getString("TSTMP"));

                // Note, MM is months, not mm
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

                Date date = null;
                try {
//                    date = inputFormat.parse(email.getTimestamp());
                    String outputText = outputFormat.format(date);
                    emailOld.setTimeFormatted(outputText);
                } catch (NullPointerException e) {
                    System.out.println(e);
                    emailOld.setTimeFormatted("");
                }

                emailOld.setBody(set.getString("EBODY"));
//                email.setAttachment(set.getString("ATTCH"));
                // particular email


                //------From Address
                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setFromAddress(fromAddress);


                //-----To Address
                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setToAddress(toAddress);
//
//                //----- CC Address
//                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setCcAddress(ccAddress);

                allEmailOlds.add(emailOld);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allEmailOlds;
    }

    public void insertEmailSent(EmailOld emailOld) {
        String query = "INSERT INTO EMAIL_SENT(EMNO,SBJCT,FRADD,TOADD,CCADD,BCCADD,TSTMP,EBODY,ATTCH,UCODE,FREZE,ESNO,UPLD_ATTCH,SENT" +
                ") SELECT IFNULL(max(EMNO),0)+1,?,?,?,?,?,?,?,?,?,?,?,?,? from EMAIL_SENT";

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, emailOld.getSubject());
//            statement.setString(2, email.getFromAddressString());
//            statement.setString(3, email.getToAddressString());
//            statement.setString(4, email.getCcAddressString());
//            statement.setString(5, email.getBccAddressString());
//            statement.setString(6, email.getTimestamp());
            statement.setString(7, emailOld.getBody());
//            statement.setString(8, email.getAttachment());
            statement.setInt(9, user.getUserCode());
            statement.setBoolean(10, false);
            statement.setInt(11, emailOld.getEmailStoreNo());
            statement.setString(12, emailOld.getUploadedDocumentsString());
            statement.setInt(13, emailOld.getSent());
            statement.executeUpdate();

            statement.close();

//            String[] allEmails = (email.getToAddressString() + "^"
//                    + email.getCcAddressString() + "^"
//                    + email.getBccAddressString()).split("\\^");

//            emailPhoneQueries.emailsListInsertion(allEmails);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<EmailOld> readAllEmailsSent(String where) {

        String query = "SELECT EMNO,SBJCT,FRADD,TOADD,CCADD,BCCADD,TSTMP,EBODY,ATTCH,U.FNAME FROM EMAIL_SENT E, users" +
                " U WHERE E.UCODE = U.UCODE ";

        if (where == null) {
            query = query + " ORDER BY EMNO DESC";
        } else {
            query = query + where + " ORDER BY EMNO DESC ";
        }

        List<EmailOld> allEmailOlds = new ArrayList<>();

        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);

            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }

            while (set.next()) {
                EmailOld emailOld = new EmailOld();
                emailOld.setCode(set.getInt("EMNO"));
                emailOld.setSubject(set.getString("SBJCT"));
//                email.setTimestamp(set.getString("TSTMP"));

                // Note, MM is months, not mm
                DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

                Date date = null;
                try {
                    date = inputFormat.parse(emailOld.getTimestamp());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String outputText = outputFormat.format(date);
                emailOld.setTimeFormatted(outputText);

                emailOld.setBody(set.getString("EBODY"));
//                email.setAttachment(set.getString("ATTCH"));

                //------From Address
                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setFromAddress(fromAddress);

//                //-----To Address
//                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setToAddress(toAddress);
//
//                //----- CC Address
//                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setCcAddress(ccAddress);
//
//                //----- CC Address
//                String[] bcc = set.getString("BCCADD").split("\\^");
//                Address[] bccAddress = new Address[bcc.length];
//                for (int i = 1, j = 0; i < bcc.length; i++, j++) {
//                    try {
//                        bccAddress[j] = new InternetAddress(bcc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setBccAddress(bccAddress);
                emailOld.setUserCode(set.getString("FNAME"));

//                email.setEmailTypeSent(true);

                allEmailOlds.add(emailOld);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return allEmailOlds;
    }


    public void lockEmail(EmailOld emailOld, int op) {        //0 Unlock 1 Lock

        String query = " UPDATE EMAIL_STORE " +
                " SET LOCKD = ?, " +
                " LOCKTIME = ? " +
                " WHERE EMNO = ? ";

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);

            if (op == 1) {  //Locking
                statement.setInt(1, user.getUserCode());
                statement.setString(2, CommonTasks.getCurrentTimeStamp());
                statement.setInt(3, emailOld.getCode());
            } else if (op == 0) {   //Unlocking
                statement.setInt(1, 0);
                statement.setString(2, null);
                statement.setInt(3, emailOld.getCode());
            }
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // doRelease(con);
        }

    }

    public void solvEmail(EmailOld emailOld, String flag, UsersOld user, boolean choice, String msg) {

        String query = " UPDATE EMAIL_STORE " +     //Query to Update Email status to solve
                " SET ESOLV = ?, " +
                " SOLVBY = ?, " +
                " SOLVTIME = ? " +
                " WHERE EMNO = ? ";

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Calendar.getInstance().getTime());
//        email.setTimestamp(timeStamp);

        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.setString(1, flag);
            statement.setInt(2, user.getUserCode());
//            statement.setString(3, email.getTimestamp());
            statement.setInt(4, emailOld.getCode());

            statement.executeUpdate();
            statement.close();

            if (eSetting.isSolvt() && choice) {
//                solvResponder(email, msg);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // doRelease(con);
        }

    }

    private void solvResponder(EmailOld emailOld, String msg) {

        //Make it html worthy
        msg = msg.replace("\n", "<br>");

        String sb = "Ticket Number: " + emailOld.getCode() + " Resolved";

        String bd = msg +
                "<br><br><br>------------------- Ticket " + emailOld.getCode() + " -------------------" +
                "<br><br>Timestamp:     <b>" + emailOld.getTimeFormatted() + "</b>" +
                "<br><br>Subject:       <b>" + emailOld.getSubject() + "</b>" +
                "<br><br>" + emailOld.getBody();
        EmailOld send = new EmailOld();
        send.setSubject(sb);
//        send.setToAddresse(email.getFromAddresse());
//        send.setCcAddresse(email.getCcAddresse());
        send.setBody(bd);
        if (emailOld.getAttachment() == null || emailOld.getAttachment().isEmpty()) {
        } else {
            send.setAttachments(EResponseController.addFile(emailOld.getAttachment()));
        }
//        emailControl.sendEmail(send, null);

    }

    public void ArchiveEmail(int type, String where) {    //Verb

        String query = "";

        switch (type) {
            case 1:
                query = "UPDATE EMAIL_STORE SET FREZE = 1 WHERE ";
                break;
            case 2:
                query = "UPDATE EMAIL_GENERAL SET FREZE = 1 WHERE ";
        }

        query = query + where;

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void markAsSent(EmailOld emailOld) {
        String query = "UPDATE EMAIL_SENT SET SENT = 1 WHERE EMNO = " + emailOld.getCode();

        // Connection con = getConnection();
        PreparedStatement statement = null;

        try {
            statement = static_con.prepareStatement(query);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            e.printStackTrace();
        }
    }

    // this method calculate average for reporting which is display onclick Average time of ticket solved by each user
    public List<EmailProperty> average_Calculate() {
        String query = "SELECT FNAME, concat (concat ( concat ( substr(SEC_TO_TIME(AVG(TIME_TO_SEC(SOLVTIME) - TIME_TO_SEC(LOCKTIME))),1,2) , ' Hours ') , " +
                "concat ( substr(SEC_TO_TIME(AVG(TIME_TO_SEC(SOLVTIME) - TIME_TO_SEC(LOCKTIME))),4,2) , ' Minutes ') ) , " +
                "concat ( substr(SEC_TO_TIME(AVG(TIME_TO_SEC(SOLVTIME) - TIME_TO_SEC(LOCKTIME))),7,2) , ' Sec ')) as average " +
                " FROM email_store AS e, users AS u " +
                " where e.SOLVBY = u.ucode" +
                " and e.FREZE = 0" +
                " group by FNAME";

        List<EmailProperty> allEmails = new ArrayList<>();
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                EmailProperty email = new EmailProperty();
                email.setUser_name(set.getString("FNAME"));
                email.setAverage(set.getString("Average"));
                allEmails.add(email);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allEmails;
    }


    // get manual ticket no for new ticket
    public int getManualTicketNo(EmailOld emailOld) {
        String query = "SELECT EMNO FROM email_store Where TSTMP =? AND SBJCT=?";
        int ticketNo = 0;
        try {
            PreparedStatement statement = static_con.prepareStatement(query);
//            statement.setString(1, email.getTimestamp());
            statement.setString(2, emailOld.getSubject());
            ResultSet set = statement.executeQuery();

            while (set.next()) {

                ticketNo = set.getInt("EMNO");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ticketNo;
    }


    // update the email_Sent table
    public void updateResendEmail(EmailOld emailOld) {
        String query = "UPDATE email_sent SET SENT=? where EMNO=?";

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, emailOld.getSent());
            statement.setInt(2, emailOld.getCode());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public EmailOld readSearchEmail(int emailNo, UserQueries userQueries) {
        String query = "SELECT EMNO, MSGNO, SBJCT, FRADD, TOADD, CCADD, TSTMP, " +
                " EBODY, ATTCH, ESOLV, LOCKD, LOCKTIME, SOLVBY, SOLVTIME, MANUAL FROM email_Store WHERE EMNO=? AND FREZE =0 ORDER BY EMNO Asc ";

        EmailOld emailOld = new EmailOld();

        try {
            // Connection con = getConnection();
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, emailNo);
            ResultSet set = statement.executeQuery();
            //-------------Creating Email-------------
            if (!set.isBeforeFirst()) {
                return null;
            }
            mySqlConn sql = null;
            while (set.next()) {
//                Email email = new Email();
                emailOld.setCode(set.getInt("EMNO"));
                emailOld.setMessageNo(set.getInt("MSGNO"));
                emailOld.setSubject(set.getString("SBJCT"));
//                email.setTimestamp(set.getString("TSTMP"));
//                email.setTimeFormatted(CommonTasks.getTimeFormatted(email.getTimestamp()));

                emailOld.setBody(set.getString("EBODY"));
//                email.setAttachment(set.getString("ATTCH"));
//                email.setSolved(set.getString("ESOLV").charAt(0));
                emailOld.setLocked(set.getInt("LOCKD"));
                emailOld.setLockedTime(set.getString("LOCKTIME"));
                emailOld.setSolvedTime(set.getString("SOLVTIME"));
                emailOld.setManualEmail(set.getInt("MANUAL"));
                if (emailOld.getManualEmail() != '\0') {
                    if (sql == null) sql = new mySqlConn();
                    emailOld.setCreatedBy(sql.getUserName(emailOld.getManualEmail()));
                }
                if (set.getInt("LOCKD") == '\0') {
                    emailOld.setLockedByName("Unlocked");
                } else {
                    emailOld.setLockedByName(userQueries.getUserName(emailOld.getLocked())); //Getting name of username that
                    // locked
                }                                                              // particular email

                //------From Address
//                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setFromAddress(fromAddress);
//
//                //-----To Address
//                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setToAddress(toAddress);
//
//                //----- CC Address
//                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email.setCcAddress(ccAddress);
//                allEmails.add(email);
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return emailOld;

    }

    public EmailOld getParticularEmail(EmailOld emailOld) {
        if (emailOld == null) {
            return null;
        }
        String query = "Select  EMNO, MSGNO, SBJCT, FRADD, TOADD, CCADD, TSTMP," +
                "               EBODY, ATTCH, ESOLV, LOCKD, LOCKTIME, SOLVTIME, MANUAL  from email_store AS ES Where EMNO =?";

        try {
            PreparedStatement statement = static_con.prepareStatement(query);
            statement.setInt(1, emailOld.getCode());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                EmailOld emailOld1 = new EmailOld();
                emailOld1.setCode(set.getInt("EMNO"));
                emailOld1.setMessageNo(set.getInt("MSGNO"));
                emailOld1.setSubject(set.getString("SBJCT"));
//                email1.setTimestamp(set.getString("TSTMP"));
//                email1.setTimeFormatted(CommonTasks.getTimeFormatted(email1.getTimestamp()));

                emailOld1.setBody(set.getString("EBODY"));
//                email1.setAttachment(set.getString("ATTCH"));
//                email1.setSolved(set.getString("ESOLV").charAt(0));
                emailOld1.setLocked(set.getInt("LOCKD"));
                emailOld1.setLockedTime(set.getString("LOCKTIME"));
                emailOld1.setSolvedTime(set.getString("SOLVTIME"));
                emailOld1.setManualEmail(set.getInt("MANUAL"));


                //------From Address
//                String[] from = set.getString("FRADD").split("\\^");
//                Address[] fromAddress = new Address[from.length];
//                for (int i = 1, j = 0; i < from.length; i++, j++) {
//                    try {
//                        fromAddress[j] = new InternetAddress(from[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email1.setFromAddress(fromAddress);
//
//                //-----To Address
//                String[] to = set.getString("TOADD").split("\\^");
//                Address[] toAddress = new Address[to.length];
//                for (int i = 1, j = 0; i < to.length; i++, j++) {
//                    try {
//                        toAddress[j] = new InternetAddress(to[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email1.setToAddress(toAddress);
//
//                //----- CC Address
//                String[] cc = set.getString("CCADD").split("\\^");
//                Address[] ccAddress = new Address[cc.length];
//                for (int i = 1, j = 0; i < cc.length; i++, j++) {
//                    try {
//                        ccAddress[j] = new InternetAddress(cc[i]);
//                    } catch (AddressException e) {
//                        e.printStackTrace();
//                    }
//                }
//                email1.setCcAddress(ccAddress);

                emailOld1.setNotes(noteQueries.getNotes(emailOld1));
                return emailOld1;
            }

            // doRelease(con);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
