package Email.EResponse;

import ApiHandler.UploadAttachmentHandler;
import Email.EmailDashController;
import JCode.FileDev;
import JCode.FileHelper;
import JCode.emailControl;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Document;
import objects.Email;
import org.controlsfx.control.textfield.TextFields;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static ApiHandler.RequestHandler.post;
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

    private emailControl helper = new emailControl();
    private FileHelper fileHelper;

    String Subject, Email, cc, bcc, Body, Disclaimer;


    public static String stSubject, stTo, stCc, stBcc, stBody, stAttach;
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

        pullingUploadedDocuments();

        populatHbox(txt_to, hbox_to);
        populatHbox(txt_cc, hbox_cc);
        populatHbox(txt_bcc, hbox_bcc);

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
            txt_subject.setDisable(true);
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

    public static List<File> addFile(String attach) {
        List<File> newFileList = new ArrayList<>();
        for (String c : attach.split("\\^")) {
            FileDev file = new FileDev(c);
            newFileList.add(file);
        }
        return newFileList;
    }

    private void populateTo() {
        if (stTo.equals(""))
            return;
        String to[] = stTo.split(",");
        for (String t : to) {
            txt_to.setText(t + ",");
        }
    }

    private void populateCC() {
        if (stCc == null)
            return;
        else if (stCc.equals(""))
            return;
        String cc[] = stCc.split(",");
        for (String c : cc) {
            txt_cc.setText(c + ",");

        }
    }

    private void populatHbox(TextField txt_field, HBox box) {
        txt_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            if (newValue.contains(",")) {
//                if (validateAddress(newValue)) {
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
//                } else {
//                    newValue=newValue.replaceAll(",",""); // replace comma for correcting valid email
//                    Alert alert = new Alert(Alert.AlertType.WARNING, "Email Entered is Invalid",
//                            ButtonType.OK);
//                    alert.showAndWait();
//                    txt_field.setText(newValue);
//                    return;
//                }

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
                at = at +"^" + f.getAbsolutePath();
            }

            txt_attach.setText(at);
        }
    }


    public void btnSendClick(ActionEvent actionEvent) throws IOException {
        Subject = txt_subject.getText();
        Body = txt_body.getText();

        Email em = new Email();

        //--------------To
        List<Address> to_emails = new ArrayList<>();
        for (Node n : hbox_to.getChildren()) {
            HBox b = (HBox) n;
            for (Node node : b.getChildren()) {
                if (node.getAccessibleText() == null)
                    continue;
//                System.out.println(node.getAccessibleText());
                if (node.getAccessibleText().equals("txt")) {
                    Label l = (Label) node;
                    try {
//                        System.out.println(l.getText());
                        Address to = new InternetAddress(l.getText());
                        to_emails.add(to);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!txt_to.getText().equals("")) {
            em.setToAddress(txt_to.getText());
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
                    try {
                        Address to = new InternetAddress(l.getText());
                        cc_emails.add(to);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!txt_cc.getText().equals("")) {
            em.setCcAddress(txt_cc.getText());
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
                    try {
                        Address bcc = new InternetAddress(l.getText());
                        bcc_emails.add(bcc);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (!txt_bcc.getText().equals("")) {
            em.setBccAddress(txt_bcc.getText());
        }

        em.setSubject(Subject);
        //Replace Line Breaks with <br> tags
        Body = Body.replace("\n", "<br>");
        em.setBody(Body);

        em.setAttachment(txt_attach.getText());
        uploadAttachmentHandler(em.getAttachment());
        if (choice == 1) {
            em.setType("sent");
            post("email/send", writeJSON(em));
        } else if (choice == 2) {
            if (!sendAsEmail.isSelected()) {
                em.setType("ticket");
                post("ticket/create", writeJSON(em));
            }
            EmailDashController.loadEmailsStatic();
        }

        Stage stage = (Stage) btn_Send.getScene().getWindow();
        stage.close();

    }

    public void uploadAttachmentHandler(String attachment) {
        String charset = "UTF-8";
        String requestURL = "http://localhost:8080/ticket/uploadMultipleFiles";
        try {
            UploadAttachmentHandler multipart = new UploadAttachmentHandler(requestURL, charset);
            for (File file : fileList) {
                multipart.addFilePart("files", file);
            }

            List<String> response = multipart.finish();

            System.out.println("SERVER REPLIED:");

            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    //Pulling all email IDs from database
    public void pullingEmails() {
        new Thread(() -> {
            mySqlConn sql = new mySqlConn();
            EMAILS_LIST = sql.getAllEmailIDs(null);

            TextFields.bindAutoCompletion(txt_to, EMAILS_LIST);
            TextFields.bindAutoCompletion(txt_cc, EMAILS_LIST);
            TextFields.bindAutoCompletion(txt_bcc, EMAILS_LIST);
        }).start();
    }

    //Placing a list of uploaded documents in the combo box
    public void pullingUploadedDocuments() {
        new Thread(() -> {
            mySqlConn sql = new mySqlConn();
            List<Document> documents = sql.getAllDocuments();
            if (documents != null) {
                ObservableList<Document> list = FXCollections.observableArrayList(documents);
                combo_uploaded.setItems(list);
            }
        }).start();
    }
//  Pattern checked of email if is it true your email pattern is correct else generate error
//    private boolean validateAddress(String email) {
//        Pattern p = Pattern.compile("[a-zA-Z0-9^][a-zA-Z0-9.< ]*@[a-zA-Z0-9]+([.][a-zA-Z> ,]+)+");
//        Matcher m = p.matcher(email);
//        if (m.find() && m.group().equals(email)) {
//            return true;
//        } else {
//            return false;
//        }
//    }


}
