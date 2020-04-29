package Email.SolvedDialog;

import JCode.FileHelper;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import Email.EmailDashController;
import javafx.stage.Stage;
import objects.Email;
import objects.Users;

import java.net.URL;
import java.util.ResourceBundle;

import static Email.EmailDashController.*;

public class SolvedDialogController implements Initializable {

    @FXML
    private TextArea txt_body;
    @FXML
    private JFXButton btn_solved;
    @FXML
    private JFXCheckBox check_send;

    private mySqlConn sqlConn;
    private Email selectedEmail;
    private FileHelper fHelper;

    private Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sqlConn = new mySqlConn();
        selectedEmail = EmailDashController.selectedEmail;

        fHelper = new FileHelper();

        user = fHelper.ReadUserDetails();

        String responder = sqlConn.getEmailSettings().getSolvRespText();
        txt_body.setText(responder);

        check_send.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                txt_body.setDisable(true);
            else
                txt_body.setDisable(false);
        });

        btn_solved.setOnAction(event -> {
            sqlConn.solvEmail(selectedEmail, "S", user, !check_send.isSelected(), txt_body.getText().toString()); // S for solved
            loadEmailsStatic();
            reloadInstances();
//            list_emailsF.getSelectionModel().select(EmailDashController.index++);
//            System.out.println("Selecting index: " + EmailDashController.index++);
            Stage stage = (Stage) btn_solved.getScene().getWindow();
            stage.close();
        });

    }


}
