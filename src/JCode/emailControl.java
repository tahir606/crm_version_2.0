package JCode;


import com.sun.mail.util.MailConnectException;
import objects.ESetting;
import objects.Email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.awt.*;
import java.net.*;
import java.util.List;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class emailControl {

//    private static String HOST = "burhanisolutions.com.pk";
//    private static String EMAIL_ID = "sales@burhanisolutions.com.pk";
//    private static String EMAIL_PASS = "burhanisales";

//    private static String F_ADD = "C:/Users/" + System.getProperty("user.name") + "/Bits/CRM/Files/";
//    private static String FADD_FILE = "\\\\192.168.100.110\\g$\\Bits\\CRM\\";

    private static String F_ADD = "";

    private static mySqlConn sqlConn;

    private static ESetting ESETTING;
    private fileHelper fHelper;

    public emailControl() {
        fHelper = new fileHelper();
        sqlConn = new mySqlConn();
        ESETTING = sqlConn.getEmailSettings();
        if (ESETTING != null) {
            F_ADD = ESETTING.getFspath();
//            System.out.println(F_ADD);
            File f = new FileDev(F_ADD);
            f.mkdirs();
        }
    }

    public void RecieveEmail() {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        trayHelper help = new trayHelper();
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            //store.connect("imap.gmail.com", "tahirshakir606@gmail.com", "king786786");
            store.connect(ESETTING.getHost(), ESETTING.getEmail(), ESETTING.getPass());

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);      //Read_Write Is Necessary For Marking Emails as Read.
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = inbox.search(ft);

            String result;
            int i = 0;

            StoreData(messages);

            inbox.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            help.displayNotification("Error", "Mail Connect Exception");
        } catch (MessagingException e) {
            e.printStackTrace();
            help.displayNotification("Error", "Mail Connect Exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void StoreData(Message messages[]) {
        try {
            int i = -1;
            for (Message message : messages) {
                i++;
                String result = "";
                result = storefile(message);
                message.setFlag(Flags.Flag.SEEN, true);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String storefile(Message message) throws Exception {
        Email email = new Email();

        String result = "";
        String SUBJECT, ATTACH = "";

        Address[] fromAddress = message.getFrom();
        Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
        Address[] ccAddress = message.getRecipients(Message.RecipientType.CC);

        trayHelper th = new trayHelper();
        th.displayNotification("New Email", "Email Received From: " + fromAddress[0].toString());

        SUBJECT = message.getSubject();
        if (SUBJECT.equals(""))
            SUBJECT = "No Subject";

        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            int numberofparts = mimeMultipart.getCount();

            String attachFiles = "";    //Names of the attached Files Concatenated

            for (int partcounts = 0; partcounts < numberofparts; partcounts++) {
                MimeBodyPart part = (MimeBodyPart) mimeMultipart.getBodyPart(partcounts);
                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                    String filename = F_ADD + part.getFileName();
                    System.out.println(part.getFileName());
                    ATTACH += filename + "^";  //AttachFiles string is to be inserted into Database
                    part.saveFile(filename);
                } else {
                    result = getTextFromMimeMultipart(mimeMultipart);
                }
            }
        }

        if (ATTACH.equals(""))
            email.setAttch("No Attachments");

        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-MM-dd hh:mm:ss");

        //-------------------------------------Creating Email--------------------------------
        email.setMsgNo(message.getMessageNumber());
        email.setTimestamp(dt.format(message.getSentDate()).substring(1));
        email.setFromAddress(fromAddress);
        email.setToAddress(toAddress);
        email.setCcAddress(ccAddress);
        email.setSubject(SUBJECT);
        email.setBody(result);
        email.setAttch(ATTACH);
        email.setSolvFlag('N');
        email.setLockd(0);
        System.out.println(email);

        sqlConn.insertEmail(email, message);

        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mime) throws Exception {
        String result = "";
        int count = mime.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mime.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }

    //Reading file
    public String readMessageFile(String fileAdd) {
        try {
            InputStream mailFile = new FileInputStream(new File(fileAdd));
            Properties properties = new Properties();
            Session session = Session.getDefaultInstance(properties, null);
            MimeMessage message = new MimeMessage(session, mailFile);
            if (message.isMimeType("multipart/*")) {
                MimeMultipart mime = (MimeMultipart) message.getContent();
                String result = getTextFromMimeMultipart(mime);
                return result;
            }

        } catch (FileNotFoundException | MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //-----------------Email Send

    public static void sendEmail(String Subject, String Email, String cc, String bcc, String Body, String Disclaimer,
                                 String Attachment, Message messageReply) {

        String perDisc = null;

        if (Disclaimer == null) {
            Disclaimer = "";
        }

        if (ESETTING.isDisc())
            perDisc = Disclaimer + "\n" + ESETTING.getDisctext();
        else
            perDisc = Disclaimer;

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", ESETTING.getHost());
        props.put("mail.smtp.port", "26");

        InternetAddress emailAddr;
        try {
            emailAddr = new InternetAddress(Email);
            emailAddr.validate();
        } catch (AddressException ex) {
            System.out.println("Invalid Email ID");
        }

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ESETTING.getEmail(), ESETTING.getPass());
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(ESETTING.getEmail()));


            // Set Subject: header field
            message.setSubject(Subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setText(Body + "\n\n" + perDisc);

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            if (!Attachment.equals("")) {
                // Part two is attachment
                messageBodyPart = new MimeBodyPart();

                BodyPart attachment = new MimeBodyPart();

                File s = new File(Attachment);

                if (s.exists()) {
                    DataSource source = new FileDataSource(Attachment);
                    attachment.setDataHandler(new DataHandler(source));

                    attachment.setFileName(Attachment);
                    //System.out.println(Attachment);

                } else {
                    trayHelper.trayIcon.displayMessage("IOException", "File Not Found", TrayIcon.MessageType.ERROR);

                }

                multipart.addBodyPart(attachment);
            }

            // Send the complete message parts
            message.setContent(multipart);

            message.saveChanges();

            //message.setText(multipart);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Email));
            if (!cc.equals(""))
                message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
            if (!bcc.equals(""))
                message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));

            //Put Message Reply
            if (messageReply != null) {
                message.setReplyTo(messageReply.getReplyTo());
            }

            new Thread(() -> {
                try {
                    Transport.send(message);
                    //System.out.println("Sent E-Mail to: " + Email);
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                    trayHelper tray = new trayHelper();
                    tray.displayNotification("Error", "Messaging Exception: Email Not Sent");
                }
            }).start();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public static boolean checkConnection() {
        HttpURLConnection connection = null;
        try {
            URL u = new URL("http://www.google.com/");
            connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("HEAD");
            int code = connection.getResponseCode();
//            System.out.println("" + code);
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
