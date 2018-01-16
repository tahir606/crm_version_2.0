package settings.email;

import JCode.Toast;
import JCode.fileHelper;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.ESetting;
import objects.Users;

import java.net.URL;
import java.util.ResourceBundle;

public class emailSetController implements Initializable {

    @FXML
    private JFXTextField txt_host;

    @FXML
    private JFXTextField txt_email;

    @FXML
    private JFXTextField txt_pass;

    @FXML
    private JFXButton bnt_save;

    private ESetting eSetting;

    private mySqlConn sql;
    private fileHelper fHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        fHelper = new fileHelper();

        eSetting = sql.getEmailSettings();

        txt_host.setText(eSetting.getHost());
        txt_email.setText(eSetting.getEmail());
        txt_pass.setText(eSetting.getPass());

    }

    public void saveChanges(ActionEvent actionEvent) {

        String host, email, pass;

        host = txt_host.getText();
        email = txt_email.getText();
        pass = txt_pass.getText();

        if (host.equals("") || pass.equals("") || email.equals("")) {
            Toast.makeText((Stage) bnt_save.getScene().getWindow(), "Required fields are empty");
            return;
        }

        ESetting es = new ESetting(host, email, pass);
        sql.saveEmailSettings(es);

    }
}
