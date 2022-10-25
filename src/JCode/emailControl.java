package JCode;


import Email.EmailDashController;
import JCode.mysql.mySqlConn;
import JSockets.JServer;
import objects.ESetting;
import objects.EmailOld;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class emailControl {


    private static String F_ADD = "";
    private static mySqlConn sqlConn;
    private final static String TRUNCATED = "truncate";
    private static ESetting ESETTING;
    private FileHelper fHelper;
    private static String replacementKeyword;
    private static List<String> white_list, blackListKeyword;
    public static boolean EmailsNotSent = false;


    public emailControl() {
        fHelper = new FileHelper();
//        sqlConn = new mySqlConn();
        ESETTING = sqlConn.getEmailSettings();
        white_list = sqlConn.getWhiteBlackListDomains(1);
        blackListKeyword = sqlConn.getBlackListKeyword();
        replacementKeyword = sqlConn.getReplacementKeyword();

        if (ESETTING != null) {
            F_ADD = ESETTING.getFspatht();
            File f = new FileDev(F_ADD);
            f.mkdirs();
        }
    }


    private static String ATTACH = "";

    private static String storefile(Message message) throws Exception {
        EmailOld emailOld = new EmailOld();

        String result = "";
        String SUBJECT;
        ATTACH = "";

        Address[] fromAddress = message.getFrom();
        Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
        Address[] ccAddress = message.getRecipients(Message.RecipientType.CC);

        SUBJECT = message.getSubject();

        if (SUBJECT.equals(""))
            SUBJECT = "No Subject";

        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            result = parseMultipart((MimeMultipart) message.getContent(), fromAddress);
        }

        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss.S");
        try {
            } catch (Exception e) {
            System.out.println(e); //Because some emails stop when reaching here
        }

        //-------------------------------------Creating Email--------------------------------
        emailOld.setMessageNo(message.getMessageNumber());
        try {
            emailOld.setTimestamp(dt.format(message.getSentDate()).substring(1));
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        emailOld.setSubject(SUBJECT);
        emailOld.setBody(result);
//        email.setAttachment(ATTACH);
//        email.setSolved('N');
        emailOld.setLocked(0);
        emailOld.setFreeze(0);
        int tix = 1;    //2 means ticket 1 means general

        if (tix == 2) {
            sqlConn.insertEmail(emailOld, message);
        } else if (tix == 1)
            sqlConn.insertEmailGeneral(emailOld);

        trayHelper th = new trayHelper();
        th.displayNotification("New Email", "Email Received From: " + fromAddress[0].toString());

        EmailDashController.loadEmailsStatic();

        JServer.broadcastMessages(fromAddress[0].toString());   //Notify all client sockets

        return result;
    }

    private static String parseMultipart(MimeMultipart mimeMultipart, Address[] fromAddress) throws MessagingException, IOException {

        String result = "";

        int numberofparts = mimeMultipart.getCount();

        for (int partcounts = 0; partcounts < numberofparts; partcounts++) {
            MimeBodyPart part = (MimeBodyPart) mimeMultipart.getBodyPart(partcounts);
            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition()) || Part.INLINE.equalsIgnoreCase(part.getDisposition())) {
                //Add Files in a folder created specifically for that email
                String folderName = "";
                try {
                    folderName = fromAddress[0].toString().trim().split("<")[1].replace(">", "");
                } catch (Exception e) {
                    folderName = "Others";
                }
                FileHelper.createDirectoryIfDoesNotExist(ESETTING.getFspatht() + "\\" + folderName + "\\");
                String filename = ESETTING.getFspatht() + "\\" + folderName + "\\" + part.getFileName();
                ATTACH += filename + "^";  //AttachFiles string is to be inserted into Database
                part.saveFile(filename);
            } else if (part.isMimeType("text/plain")) {
                result = result + "\n" + part.getContent();
//                break; // without break same text appears twice in my tests
            } else if (part.isMimeType("text/html")) {
                String html = (String) part.getContent();
                result = html;
            } else if (part.isMimeType("multipart/*")) {
                result = parseMultipart((MimeMultipart) part.getContent(), fromAddress);
            }
        }

        return result;
    }


    //  replacing keyword
    public static void truncateBlacklistedKeywords(EmailOld emailOld) {
        for (int i = 0; i < blackListKeyword.size(); i++) {
            String value = "(?i)" + blackListKeyword.get(i); // (?i) this key is used for ignoring case
            emailOld.setSubject(emailOld.getSubject().replaceAll(value, replacementKeyword)); // replaceAll will replace keyword
            emailOld.setBody(emailOld.getBody().replaceAll(value, replacementKeyword));
        }

    }

    public static boolean checkConnection() {
        HttpURLConnection connection = null;
        try {
            URL u = new URL("http://www.google.com/");
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
            return true;
            // You can determine on HTTP return code received. 200 is success.
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            System.out.println("Unknown Host Exception");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }


}
