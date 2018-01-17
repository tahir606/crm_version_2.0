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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
    private JFXTextField txt_fspath;

    @FXML
    private JFXButton bnt_save;

    @FXML
    private JFXButton btn_choose;

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

    @FXML
    void onChoose(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder");
        String path = chooser.showDialog(new Stage()).getAbsolutePath();
        txt_fspath.setText(path);
    }

    public void saveChanges(ActionEvent actionEvent) {

        String host, email, pass, fspath;

        host = txt_host.getText();
        email = txt_email.getText();
        pass = txt_pass.getText();
        fspath = txt_fspath.getText();

        if (host.equals("") || pass.equals("") || email.equals("") || fspath.equals("")) {
            Toast.makeText((Stage) bnt_save.getScene().getWindow(), "Required fields are empty");
            return;
        }

        ESetting es = new ESetting(host, email, pass, fspath + "\\Bits\\CRM\\Files\\");
        sql.saveEmailSettings(es);

    }
}
