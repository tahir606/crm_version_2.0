package Email.EResponse;

import Email.EmailDashController;
import JCode.emailControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

        txt_subject.setText(EmailDashController.subject);
        if (EmailDashController.ReplyForward == 'R') {
            txt_to.setText(EmailDashController.efrom);
            txt_to.setDisable(true);
            txt_subject.setDisable(true);
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

        if (file == null) {
            helper.sendEmail(Subject, Email, cc, bcc, Body, Disclaimer, "", null);
        } else {
            helper.sendEmail(Subject, Email, cc, bcc, Body, Disclaimer, file.getAbsolutePath(), null);
        }

        Stage stage = (Stage) btn_Send.getScene().getWindow();
        stage.close();

    }

}
