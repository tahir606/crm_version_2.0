package Email.Archive;

import ApiHandler.RequestHandler;
import Email.EmailDashController;
import JCode.Toast;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class archiveController implements Initializable {

    @FXML
    private CheckBox check_all;
    @FXML
    private JFXDatePicker before_date;
    //    @FXML
//    private JFXDatePicker after_date;
    @FXML
    private HBox hbox_ticket;
    @FXML
    private TextField txt_from;
    @FXML
    private TextField txt_to;
    @FXML
    private JFXButton btn_move;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        check_all.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                before_date.setDisable(true);
//                after_date.setDisable(true);
                hbox_ticket.setDisable(true);
            } else {
                before_date.setDisable(false);
//                after_date.setDisable(false);
                hbox_ticket.setDisable(false);
            }
        });
    }

    public void setArchive(ActionEvent actionEvent) throws IOException {


        String where = " 1 ";
        int freeze = 0;
        String beforeDate = null;
        String ticketFrom = null, ticketTo = null;
        if (check_all.isSelected()) {
            freeze = 1;
            where = where + " AND FREZE != 1 ";
        } else {
            String before = String.valueOf(before_date.getValue());
            String tF = txt_from.getText();
            String tT = txt_to.getText();
            if (tF.equals("") ^ tT.equals("")) {   //Exclusive OR || XOR
                Toast.makeText((Stage) btn_move.getScene().getWindow(), "Please Fill Both To and From Tickets");
                return;
            }

            if (!before.equals("null")) {
                beforeDate = before;
                where = where + " AND TSTMP <= '" + before + "' ";
            }

            if (!tF.equals("") && !tT.equals("")) {
                ticketFrom = tF;
                ticketTo = tT;
                where = where + " AND EMNO >= " + tF + " AND EMNO <= " + tT;
            }

        }

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Commit?",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.YES) {
            RequestHandler.run("ticket/archiveAll?freeze=" + freeze + "&beforeDate=" + beforeDate + "&ticketFrom=" + ticketFrom + "&ticketTo=" + ticketTo).close();

//            sql.ArchiveEmail(EmailDashController.Email_Type, where);
            EmailDashController.loadEmailsStatic();
            Stage stage = (Stage) btn_move.getScene().getWindow();
            stage.close();
        } else {
            return;
        }

    }
}
