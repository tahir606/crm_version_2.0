package Email.Archive;

import Email.EmailDashController;
import JCode.Toast;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dashboard.dController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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

    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sql = new mySqlConn();

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

    public void setArchive(ActionEvent actionEvent) {


        String where = " 1 ";

        if (check_all.isSelected()) {
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
                where = where + " AND TSTMP <= '" + before + "' ";
            }

            if (!tF.equals("") && !tT.equals("")) {
                where = where + " AND EMNO >= " + tF + " AND EMNO <= " + tT;
            }

        }

        System.out.println(where);

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to Commit?",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.YES) {
            sql.ArchiveEmail(EmailDashController.Email_Type, where);
            EmailDashController.loadEmailsStatic();
            Stage stage = (Stage) btn_move.getScene().getWindow();
            stage.close();
        } else {
            return;
        }

    }
}
