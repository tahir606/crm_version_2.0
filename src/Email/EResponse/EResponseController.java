package Email.EResponse;

import Email.EmailDashController;
import JCode.emailControl;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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

        txt_to.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(",")) {
                TextFields.bindAutoCompletion(txt_to, emails);
                System.out.println("Binding");
            }
        });

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

    public void btnAttachClick(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Attach File");
        file = chooser.showOpenDialog(new Stage());
        if (file != null)
            txt_attach.setText(file.getName());
    }

    public void btnSendClick(ActionEvent actionEvent) {
        Subject = txt_subject.getText();
        Email = txt_to.getText();
        Body = txt_body.getText();
        cc = txt_cc.getText();
        bcc = txt_bcc.getText();
//        Disclaimer = "\n\n\nRegards,\nBITS IT Department";
        Disclaimer = "";

        if (Email.equals("") || Body.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Required Fields are Empty",
                    ButtonType.OK);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                return;
            }
        }


        objects.Email em = new Email();

        try {
            Address to = new InternetAddress(Email);
            em.setToAddress(new Address[]{to});
        } catch (AddressException e) {
            e.printStackTrace();
        }

        if (!cc.equals("")) {
            try {
                Address ccAdd = new InternetAddress(cc);
                em.setCcAddress(new Address[]{ccAdd});
            } catch (AddressException e) {
                e.printStackTrace();
            }
        } else if (!bcc.equals("")) {
            try {
                Address bccAdd = new InternetAddress(bcc);
                em.setBccAddress(new Address[]{bccAdd});
            } catch (AddressException e) {
                e.printStackTrace();
            }
        }

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
