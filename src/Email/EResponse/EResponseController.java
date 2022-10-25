package Email.EResponse;

import ApiHandler.RequestHandler;
import Email.EmailDashController;
import JCode.FileDev;
import JCode.FileHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Document;
import objects.Email;
import objects.EmailList;
import org.controlsfx.control.textfield.TextFields;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ApiHandler.RequestHandler.postWithFile;
import static ApiHandler.RequestHandler.writeJSON;

public class EResponseController implements Initializable {

    public List<File> fileList;
    @FXML
    private TextField txt_to, txt_cc, txt_bcc, txt_attach, txt_subject;
    @FXML
    private TextArea txt_body;
    @FXML
    private JFXComboBox<Document> combo_uploaded;
    @FXML
    private Text lbl_attach;
    @FXML
    private Label txt_disclaimer;
    @FXML
    private Button btn_Send, btn_attach;
    @FXML
    private HBox hbox_to, hbox_cc, hbox_bcc;
    @FXML
    private JFXCheckBox sendAsEmail;
    public static volatile int choice = 1;      //1- Send Email 2-Create Ticket
    private File file;
    private List<Document> attachedDocuments;

//    private emailControl helper = new emailControl();
    private FileHelper fileHelper;

    String Subject, Email, cc, bcc, Body, Disclaimer;

    public static List<String> stTo, stCc, stBcc, stAttach;
    public static String stSubject, stBody;
    public static char stInstance;

    public String[] EMAILS_LIST;

    public EResponseController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileHelper = new FileHelper();

        if (choice == 1) {
            sendAsEmail.setVisible(false);
            btn_Send.setText("Send");
        } else if (choice == 2) {
            sendAsEmail.setVisible(true);
            btn_Send.setText("Create");

            txt_disclaimer.setVisible(true);
        }

        pullingEmails();

//        pullingUploadedDocuments();

        populateHBox(txt_to, hbox_to);
        populateHBox(txt_cc, hbox_cc);
        populateHBox(txt_bcc, hbox_bcc);

        combo_uploaded.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (attachedDocuments == null) {
                attachedDocuments = new ArrayList<>();
            } else {
                attachedDocuments.clear();
            }
            attachedDocuments.add(newValue);
        });

        txt_subject.setText(stSubject);
        if (stInstance == 'R') {
            populateTo();
            populateCC();
//            txt_subject.setDisable(true);
            txt_body.setText(stBody);
            btn_Send.setText("Reply");

        } else if (stInstance == 'F') {
            if (stAttach == null) {
            } else {
                fileList = addFile(stAttach);
                String at = "";
                if (fileList != null) {
                    for (File f : fileList) {
                        at = at + " -- " + f.getName();
                    }
                }
                txt_attach.setText(at);
                txt_attach.setVisible(true);
            }

            txt_body.setText(stBody);
            txt_body.setDisable(true);
            txt_attach.setDisable(true);
            txt_subject.setDisable(true);
            lbl_attach.setVisible(false);
            btn_attach.setVisible(false);
            btn_attach.setDisable(true);
            btn_Send.setText("Forward");
        } else if (stInstance == 'N') {
            populateTo();
        }
    }

    public static List<File> addFile(List<String> attach) {
        List<File> newFileList = new ArrayList<>();
        for (String c : attach) {
            FileDev file = new FileDev(c);
            newFileList.add(file);
        }
        return newFileList;
    }

    private void populateTo() {
        if (stTo.isEmpty()) {
            return;
        }
        for (Iterator<String> i = stTo.iterator(); i.hasNext(); ) {
            String item = i.next();
            if (i.hasNext()) {
                txt_to.setText(item + ",");
            } else {
                txt_to.setText(item);
            }
        }
    }

    private void populateCC() {
        if(stCc==null){
            return;
        }
        if (stCc.isEmpty()) {
            return;
        }
        for (String c : stCc) {
            if (!c.equals("")) {
                txt_cc.setText(c + ",");
            }


        }
    }

    private void populateHBox(TextField txt_field, HBox box) {
        txt_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.equals(""))
                return;

            if (newValue.contains(",")) {
                if (validateAddress(newValue)) {
                    HBox hb = new HBox();
                    Label l = new Label(newValue.split("\\,")[0]);
                    l.setMaxWidth(120);
                    l.setAccessibleText("txt");
                    JFXButton b = new JFXButton("x");
                    b.setStyle("-fx-font-size: 5pt");
                    b.setOnAction(event -> box.getChildren().remove(hb));
                    hb.getChildren().addAll(l, b);
                    box.getChildren().add(hb);
                    txt_field.setText("");


                } else {
                    newValue = newValue.replaceAll(",", ""); // replace comma for correcting valid email
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Email Entered is Invalid",
                            ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

            }

        });
    }

    public void btnAttachClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Attach File");
        fileList = chooser.showOpenMultipleDialog(new Stage());
        String at = "";
        if (fileList != null) {
            for (File f : fileList) {
                at = at + "^" + f.getAbsolutePath();
            }
            txt_attach.setText(at);
        }
    }


    public void btnSendClick(ActionEvent actionEvent) throws IOException, AddressException {
        Subject = txt_subject.getText();
        Body = txt_body.getText();

        objects.Email em = new Email();

        //--------------To
        List<Address> to_emails = new ArrayList<>();
        for (Node n : hbox_to.getChildren()) {
            HBox b = (HBox) n;
            for (Node node : b.getChildren()) {
                if (node.getAccessibleText() == null)
                    continue;

                if (node.getAccessibleText().equals("txt")) {
                    Label l = (Label) node;
                    Address to = new InternetAddress(l.getText());
                    to_emails.add(to);
                }
            }
        }
        if (!txt_to.getText().equals("")) {
            Address toAddress = new InternetAddress(txt_to.getText());
            to_emails.add(toAddress);
        }

        Address ad[] = to_emails.toArray(new Address[to_emails.size()]);
        em.setToAddress(getAddress(getAddressListString(ad)));

        if (validateAddress(txt_to.getText())) { // check email pattern
        } else {
            if (!em.getToAddress().isEmpty()) {
                if (txt_to.getText().equals("")) { // check email pattern
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Required To Address is Invalid",
                            ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Required To Address is Invalid",
                        ButtonType.OK);
                alert.showAndWait();
                return;
            }

        }


        //--------------CC
        List<Address> cc_emails = new ArrayList<>();
        for (Node n : hbox_cc.getChildren()) {
            HBox h = (HBox) n;
            for (Node node : h.getChildren()) {
                if (node.getAccessibleText() == null)
                    continue;
                if (node.getAccessibleText().equals("txt")) {
                    Label l = (Label) node;
                    Address cc = new InternetAddress(l.getText());
                    cc_emails.add(cc);
                }
            }
        }
        if (!txt_cc.getText().equals("")) {
            Address ccAddress = new InternetAddress(txt_cc.getText());
            cc_emails.add(ccAddress);
        }

        Address cc[] = cc_emails.toArray(new Address[cc_emails.size()]);
        em.setCcAddress(getAddress(getAddressListString(cc)));

        if (validateAddress(txt_cc.getText()) || txt_cc.getText().equals("")) { // check email pattern
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required cc Address is Invalid",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }
        //--------------BCC
        List<Address> bcc_emails = new ArrayList<>();
        for (Node n : hbox_bcc.getChildren()) {
            HBox h = (HBox) n;
            for (Node node : h.getChildren()) {
                if (node.getAccessibleText() == null)
                    continue;
                if (node.getAccessibleText().equals("txt")) {
                    Label l = (Label) node;
                    Address bcc = new InternetAddress(l.getText());
                    bcc_emails.add(bcc);
                }
            }
        }
        if (!txt_bcc.getText().equals("")) {
            Address bccAddress = new InternetAddress(txt_bcc.getText());
            bcc_emails.add(bccAddress);
        }

        Address bcc[] = bcc_emails.toArray(new Address[bcc_emails.size()]);
        em.setBccAddress(getAddress(getAddressListString(bcc)));
        if (validateAddress(txt_bcc.getText()) || txt_bcc.getText().equals("")) { // check email pattern
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required bcc Address is Invalid",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }

        if (Subject ==null || Subject.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required Subject",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }
        em.setSubject(Subject);
        //Replace Line Breaks with <br> tags
        if (Body==null){
          Body ="";
        }else{
            Body = Body.replace("\n", "<br>");
        }

        em.setBody(Body);
        List<String> attachmentList = new ArrayList<>();
        if (!txt_attach.getText().equals("")) {
            for (String attach : txt_attach.getText().split("\\^")) {
                attachmentList.add(attach);
            }
        }
        em.setAttachment(attachmentList);
        combinedEmailList(ad, cc, bcc);
        int userCode = FileHelper.ReadUserApiDetails().getUserCode(); //get user Code From File
        if (to_emails.isEmpty()) {
            return;
        }
        if (choice == 1) {
            em.setType("sent");
            postWithFile("email/send/" + userCode, writeJSON(em), fileList);
        } else if (choice == 2) {

            em.setType("ticket");
            em.setSendAsEmail(sendAsEmail.isSelected());
            postWithFile("ticket/create/" + userCode, writeJSON(em), fileList);

            EmailDashController.loadEmailsStatic();
        }

        Stage stage = (Stage) btn_Send.getScene().getWindow();
        stage.close();

    }

    public void combinedEmailList(Address[] fromAddress, Address[] ccAddress, Address[] bccAddress) {
        Set<EmailList> set = new LinkedHashSet<>(getAddressListString(fromAddress));
        set.addAll(getAddressListString(ccAddress));
        List<EmailList> combinedList = new ArrayList<>(set);
        Set<EmailList> set2 = new LinkedHashSet<>(combinedList);
        set2.addAll(getAddressListString(bccAddress));
        List<EmailList> combinedList3 = new ArrayList<>(set2);

        try {
            RequestHandler.post("emailList/addToEmailList", RequestHandler.writeJSONEmailList(combinedList3));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<EmailList> list;

    //Pulling all email IDs from database
    public void pullingEmails() {

        new Thread(() -> {
            try {
                list = RequestHandler.listRequestHandler(RequestHandler.run("emailList/getEmailAddressList"), EmailList.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            TextFields.bindAutoCompletion(txt_to, list);
            TextFields.bindAutoCompletion(txt_cc, list);
            TextFields.bindAutoCompletion(txt_bcc, list);
        }).start();
    }

    private static List<EmailList> getAddressListString(Address[] addresses) {
        List<EmailList> s = new ArrayList<>();
        if (addresses == null)
            return s;
        for (Address ad : addresses) {
            if (ad != null) {
                s.add(new EmailList(((InternetAddress) ad).getAddress(), ((InternetAddress) ad).getPersonal()));
            }

        }
        return s;
    }

    private List<String> getAddress(List<EmailList> addressListString) {
        List<String> s = new ArrayList<>();
        for (EmailList emailAddress : addressListString) {
            if (emailAddress != null) {
                s.add(emailAddress.getAddress());
            }
        }
        return s;
    }

    //Placing a list of uploaded documents in the combo box
//    public void pullingUploadedDocuments() {
//        new Thread(() -> {
//            mySqlConn sql = new mySqlConn();
//            List<Document> documents = sql.getAllDocuments();
//
//            if (documents != null) {
//                documents.add(0,new Document(new File("Empty")));
//                ObservableList<Document> list = FXCollections.observableArrayList(documents);
//                combo_uploaded.setItems(list);
//            }
//        }).start();
//    }

    //  Pattern checked of email if is it true your email pattern is correct else generate error
    public static boolean validateAddress(String email) {
        if (email != null) {
            if (email.contains(",")) {
                int pos = email.lastIndexOf(',');//returns last index of 's' char value
                String ch = "";
                email = email.substring(0, pos) + ch + email.substring(pos + 1);
            }
        }
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }


}
