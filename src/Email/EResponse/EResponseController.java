package Email.EResponse;

import Email.EmailDashController;
import JCode.FileHelper;
import JCode.emailControl;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.controlsfx.control.textfield.TextFields;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class EResponseController implements Initializable {

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

    public static volatile int choice = 1;      //1- Send Email 2-Create Ticket

    private List<File> file;
    private List<Document> attachedDocuments;

    private emailControl helper = new emailControl();
    private FileHelper fileHelper;

    String Subject, Email, cc, bcc, Body, Disclaimer, Attachment;

    public static String stSubject, stTo, stCc, stBcc, stBody;
    public static char stInstance;

    public String[] EMAILS_LIST;

    public EResponseController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileHelper = new FileHelper();

        if (choice == 1)
            btn_Send.setText("Send");
        else if (choice == 2) {
            btn_Send.setText("Create");

            txt_disclaimer.setVisible(false);
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
            txt_body.setText(stBody);
            txt_body.setDisable(true);
            txt_attach.setVisible(false);
            txt_attach.setDisable(true);

            txt_subject.setDisable(true);

            lbl_attach.setVisible(false);
            btn_attach.setVisible(false);
            btn_attach.setDisable(true);

            btn_Send.setText("Forward");
        } else if (stInstance == 'N') {
            populateTo();
        } else {

        }
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
            }
        });
    }

    public void btnAttachClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Attach File");
        file = chooser.showOpenMultipleDialog(new Stage());
        String at = "";
        if (file != null) {
            for (File f : file) {
                at = at + " -- " + f.getName();
            }
            txt_attach.setText(at);
        }
    }

    public void btnSendClick(ActionEvent actionEvent) {
        Subject = txt_subject.getText();
        Body = txt_body.getText();
        Email = txt_to.getText();
        cc = "";
        bcc = "";
        Disclaimer = "";

        System.out.println(hbox_to.getChildren().size());
        System.out.println(Email);
        System.out.println(Body);
//        System.out.println((hbox_to.getChildren().size() == 0 ^ Email.equals("")) || Body.equals(""));

        objects.Email em = new Email();

        //--------------To
        List<Address> to_emails = new ArrayList<>();
        for (Node n : hbox_to.getChildren()) {
            HBox b = (HBox) n;
            for (Node node : b.getChildren()) {
                if (node.getAccessibleText() == null)
                    continue;
                System.out.println(node.getAccessibleText());
                if (node.getAccessibleText().equals("txt")) {
                    Label l = (Label) node;
                    try {
                        System.out.println(l.getText());
                        Address to = new InternetAddress(l.getText());
                        to_emails.add(to);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            if (!txt_to.getText().equals("")) {
                Address to = new InternetAddress(txt_to.getText());
                to_emails.add(to);
            }
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address ad[] = to_emails.toArray(new Address[to_emails.size()]);
        em.setToAddress(ad);

        if (em.getToAddress().length < 0 || Body.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required Fields are Empty",
                    ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                return;
            } else {
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
                    try {
                        Address to = new InternetAddress(l.getText());
                        cc_emails.add(to);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            if (!txt_cc.getText().equals("")) {
                Address cc = new InternetAddress(txt_cc.getText());
                cc_emails.add(cc);
            }
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address cc[] = cc_emails.toArray(new Address[cc_emails.size()]);
        em.setCcAddress(cc);

        System.out.println(Arrays.toString(em.getCcAddress()));

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
        try {
            if (!txt_bcc.getText().equals("")) {
                Address bcc = new InternetAddress(txt_bcc.getText());
                bcc_emails.add(bcc);
            }
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address bcc[] = bcc_emails.toArray(new Address[bcc_emails.size()]);
        em.setBccAddress(bcc);

        em.setSubject(Subject);
        em.setBody(Body);
        em.setDisclaimer(Disclaimer);
        if (file == null) {
        } else if (file.size() > -1)
            em.setAttachments(file);
        else
            em.setAttch("");

        if (attachedDocuments != null) {
            em.setDocuments(attachedDocuments);
        }

        if (choice == 1)
            helper.sendEmail(em, null);
        else if (choice == 2) {
            mySqlConn sql = new mySqlConn();
            em.setFromAddress(em.getToAddress());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Calendar.getInstance().getTime());
            em.setTimestamp(timeStamp);
            em.setManual(fileHelper.ReadUserDetails().getUCODE());

            //Pretty Garbage Code. Try to Clean it up a bit later
            //This copies all attachments to File System to be viewed later
            //Still have to check Ticket Generation from General for attachment Carry Forward. Peace.
            String path = sql.getEmailSettings().getFspath() + "\\manual\\";
            fileHelper.createDirectoryIfDoesNotExist(path);
            String attch = "";
            if (file == null) {
            } else if (file.size() > -1) {
                for (File aFile : file) {
                    try {
                        Files.copy(aFile.toPath(),
                                (new File(path + aFile.getName())).toPath(),
                                StandardCopyOption.REPLACE_EXISTING);
                        attch += path + aFile.getName();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!attch.equals(""))
                em.setAttch(attch);

            sql.insertEmailManual(em);
            EmailDashController.loadEmailsStatic();
        }

        Stage stage = (Stage) btn_Send.getScene().getWindow();
        stage.close();

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

}
