package Email.SolvedDialog;

import ApiHandler.RequestHandler;
import Email.EmailDashController;
import JCode.FileHelper;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.ESetting;
import objects.Email;
import objects.History;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
    private ESetting eSetting;
    private Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        sqlConn = new mySqlConn();
        selectedEmail = EmailDashController.selectedEmail;

        fHelper = new FileHelper();

//        user = fHelper.ReadUserDetails();
        user = FileHelper.ReadUserApiDetails();
        try {
            eSetting = (ESetting) RequestHandler.objectRequestHandler(RequestHandler.run("settings/getSettings"), ESetting.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        String responder = sqlConn.getEmailSettings().getSolvRespTextt();
        txt_body.setText(eSetting.getSolveText());

        check_send.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)
                txt_body.setDisable(true);
            else
                txt_body.setDisable(false);
        });

        btn_solved.setOnAction(event -> {
            try {
//                change user Code
                List<History> historyList = getSelectEmailHistory(selectedEmail);
                String statusTime = getStatusTime(historyList);

                History history = getHistory(historyList, statusTime, selectedEmail);

                if (history.getStatus().equals("RESOLVED") && isAdmin.equals("Admin") && selectedEmail.getIsAllocatedBy() == user.getUserCode()) {
                    RequestHandler.run("ticket/solve?code=" + selectedEmail.getCode() + "&userCode=" + history.getUsers().getUserCode() + "&status=" + 2+"&checkSend="+check_send.isSelected()+"&solvedBody="+txt_body.getText().toString()).close();
                } else if (history.getUsers().getUserCode() == user.getUserCode() && history.getStatus().equals("LOCKED")) {
                    RequestHandler.run("ticket/solve?code=" + selectedEmail.getCode() + "&userCode=" + user.getUserCode()+ "&status=" + 2+"&checkSend="+check_send.isSelected()+"&solvedBody="+txt_body.getText().toString()).close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            loadEmailsStatic();
//            reloadInstances();
            Stage stage = (Stage) btn_solved.getScene().getWindow();
            stage.close();
        });

    }


}
