package Email.SolvedDialog;

import JCode.fileHelper;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import Email.EmailDashController;
import javafx.stage.Stage;
import objects.Email;
import objects.Users;

import java.net.URL;
import java.util.ResourceBundle;

import static Email.EmailDashController.loadEmailsStatic;
import static Email.EmailDashController.reloadInstances;

public class SolvedDialogController implements Initializable {

    @FXML
    private TextArea txt_body;
    @FXML
    private JFXButton btn_solved;
    @FXML
    private JFXCheckBox check_send;

    private mySqlConn sqlConn;
    private Email selectedEmail;
    private fileHelper fHelper;

    private Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sqlConn = new mySqlConn();
        selectedEmail = EmailDashController.selectedEmail;

        fHelper = new fileHelper();

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

            Stage stage = (Stage) btn_solved.getScene().getWindow();
            stage.close();
        });

    }


}
