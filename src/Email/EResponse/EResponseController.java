package Email.EResponse;

import Email.EmailDashController;
import JCode.emailControl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    File file;

    emailControl helper = new emailControl();

    String Subject, Email, cc, bcc, Body, Disclaimer, Attachment;

    public EResponseController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String[] emails = EmailDashController.EMAILS_LIST;

        TextFields.bindAutoCompletion(txt_to, emails);
        TextFields.bindAutoCompletion(txt_cc, emails);
        TextFields.bindAutoCompletion(txt_bcc, emails);

        populatHbox(txt_to, hbox_to);
        populatHbox(txt_cc, hbox_cc);
        populatHbox(txt_bcc, hbox_bcc);

        txt_subject.setText(EmailDashController.subject);
        if (EmailDashController.ReplyForward == 'R') {
            txt_to.setText(EmailDashController.efrom);
            txt_to.setDisable(true);
            txt_subject.setDisable(true);
            txt_body.setText(EmailDashController.body);
            btn_Send.setText("Reply");

        } else if (EmailDashController.ReplyForward == 'F') {
            txt_body.setText(EmailDashController.body);
            txt_body.setDisable(true);
            txt_attach.setVisible(false);
            txt_attach.setDisable(true);

            txt_subject.setDisable(true);

            lbl_attach.setVisible(false);
            btn_attach.setVisible(false);
            btn_attach.setDisable(true);

            btn_Send.setText("Forward");
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
        file = chooser.showOpenDialog(new Stage());
        if (file != null)
            txt_attach.setText(file.getName());
    }

    public void btnSendClick(ActionEvent actionEvent) {
        Subject = txt_subject.getText();
        Body = txt_body.getText();
        Email = txt_to.getText();
        cc = "";
        bcc = "";
//        Disclaimer = "\n\n\nRegards,\nBITS IT Department";
        Disclaimer = "";

        System.out.println(hbox_to.getChildren().size());
        System.out.println(Email);
        System.out.println(Body);
//        System.out.println((hbox_to.getChildren().size() == 0 ^ Email.equals("")) || Body.equals(""));

        if (!(hbox_to.getChildren().size() == 0 || Email.equals("")) || Body.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required Fields are Empty",
                    ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                return;
            } else {
                return;
            }
        }

        objects.Email em = new Email();

        //--------------To
        List<Address> to_emails = new ArrayList<>();
        for (Node node : hbox_to.getChildren()) {
            if (node.getAccessibleText() == null)
                continue;
            if (node.getAccessibleText().equals("txt")) {
                Label l = (Label) node;
                try {
                    Address to = new InternetAddress(l.getText());
                    to_emails.add(to);
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Address to = new InternetAddress(txt_to.getText());
            to_emails.add(to);
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address ad[] = (Address[]) to_emails.toArray();
        em.setToAddress(ad);


        //--------------CC
        List<Address> cc_emails = new ArrayList<>();
        for (Node node : hbox_cc.getChildren()) {
            if (node.getAccessibleText().equals("txt")) {
                Label l = (Label) node;
                try {
                    Address to = new InternetAddress(l.getText());
                    to_emails.add(to);
                } catch (AddressException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Address cc = new InternetAddress(txt_cc.getText());
            cc_emails.add(cc);
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address cc[] = (Address[]) cc_emails.toArray();
        em.setCcAddress(cc);

        //--------------BCC
        List<Address> bcc_emails = new ArrayList<>();
        for (Node node : hbox_bcc.getChildren()) {
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
        try {
            Address bcc = new InternetAddress(txt_bcc.getText());
            bcc_emails.add(bcc);
        } catch (AddressException e) {
            e.printStackTrace();
        }
        Address bcc[] = (Address[]) bcc_emails.toArray();
        em.setCcAddress(bcc);

        em.setSubject(Subject);
        em.setBody(Body);
        em.setDisclaimer(Disclaimer);
        if (file != null)
            em.setAttch(file.getAbsolutePath());
        else
            em.setAttch("");

        helper.sendEmail(em, null);

        Stage stage = (Stage) btn_Send.getScene().getWindow();
        stage.close();

    }

}
