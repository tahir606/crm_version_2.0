package Email.EResponse;

import Email.EmailDashController;
import JCode.emailControl;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Email;
import org.controlsfx.control.textfield.TextFields;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class EResponseController implements Initializable {

    @FXML
    private TextField txt_to;
    @FXML
    private TextField txt_cc;
    @FXML
    private TextField txt_bcc;
    @FXML
    private TextField txt_attach;
    @FXML
    private TextField txt_subject;
    @FXML
    private TextArea txt_body;
    @FXML
    private Text lbl_attach;
    @FXML
    private Button btn_Send;
    @FXML
    private Button btn_attach;
    @FXML
    private HBox hbox_to, hbox_cc, hbox_bcc;

    public static volatile int choice = 1;      //1- Send Email 2-Create Ticket

    List<File> file;

    emailControl helper = new emailControl();

    String Subject, Email, cc, bcc, Body, Disclaimer, Attachment;

    public static String stSubject, stTo, stCc, stBcc, stBody;
    public static char stInstance;

    public String[] EMAILS_LIST;

    public EResponseController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (choice == 1)
            btn_Send.setText("Send");
        else if (choice == 2)
            btn_Send.setText("Create");

        pullingEmails();

        populatHbox(txt_to, hbox_to);
        populatHbox(txt_cc, hbox_cc);
        populatHbox(txt_bcc, hbox_bcc);

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

    private void populateBCC() {

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

        System.out.println(Arrays.toString(em.getBccAddress()));

        em.setSubject(Subject);
        em.setBody(Body);
        em.setDisclaimer(Disclaimer);
        if (file == null) {
        } else if (file.size() > -1)
            em.setAttachments(file);
        else
            em.setAttch("");

        if (choice == 1)
            helper.sendEmail(em, null);
        else if (choice == 2) {
            mySqlConn sql = new mySqlConn();
            em.setFromAddress(em.getToAddress());
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(Calendar.getInstance().getTime());
            em.setTimestamp(timeStamp);
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

}
