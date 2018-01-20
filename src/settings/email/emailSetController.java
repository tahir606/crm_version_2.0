package settings.email;

import JCode.Toast;
import JCode.fileHelper;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import objects.ESetting;
import objects.Users;

import java.io.IOException;
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
    private JFXCheckBox check_auto;
    @FXML
    private JFXButton btn_auto;
    @FXML
    private JFXCheckBox check_disclaimer;
    @FXML
    private JFXButton btn_disclaimer;
    @FXML
    private JFXButton bnt_save;
    @FXML
    private JFXButton btn_choose;

    private ESetting eSetting;

    private mySqlConn sql;
    private fileHelper fHelper;

    private static String autoText, discText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        fHelper = new fileHelper();

        eSetting = sql.getEmailSettings();

        txt_host.setText(eSetting.getHost());
        txt_email.setText(eSetting.getEmail());
        txt_pass.setText(eSetting.getPass());
        txt_fspath.setText(eSetting.getFspath());
        check_auto.setSelected(eSetting.isAuto());
        check_disclaimer.setSelected(eSetting.isDisc());
    }


    @FXML
    void setAuto(ActionEvent event) {
        inflateTextArea(1);
    }

    @FXML
    void setDisclaimer(ActionEvent event) {
        inflateTextArea(2);
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

        boolean auto, disc;

        host = txt_host.getText();
        email = txt_email.getText();
        pass = txt_pass.getText();
        fspath = txt_fspath.getText();
        auto = check_auto.isSelected();
        disc = check_disclaimer.isSelected();

        if (host.equals("") || pass.equals("") || email.equals("") || fspath.equals("")) {
            Toast.makeText((Stage) bnt_save.getScene().getWindow(), "Required fields are empty");
            return;
        }

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save changes? " +
                " Your previous settings will be deleted.",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.YES) {
            ESetting es = new ESetting(host, email, pass, fspath);
            es.setAuto(auto);
            es.setDisc(disc);
            es.setAutotext(autoText);
            es.setDisctext(discText);

            sql.saveEmailSettings(es);

            new Thread(() -> {
                Platform.runLater(() -> {
                    Toast.makeText((Stage) btn_auto.getScene().getWindow(), "Restart the application for the changes" +
                            " to be made!");
                });
            }).start();

        } else {
            return;
        }

    }

    private void inflateTextArea(int c) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Auto Reply");
        AnchorPane pane = new AnchorPane();
        TextArea area = new TextArea();
        area.setMinSize(400, 400);
        pane.getChildren().add(area);
        stage.setScene(new Scene(pane, 400, 400));
        trayHelper tray = new trayHelper();
        tray.createIcon(stage);
        Platform.setImplicitExit(true);

        stage.setOnHiding(event -> {
            if (c == 1) {        //Auto Reply
                autoText = area.getText();
            } else if (c == 2) {    //Disclaimer
                discText = area.getText();
            }
        });

        stage.show();
    }
}
