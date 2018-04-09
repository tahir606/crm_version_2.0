package client.dash.clientView.clientDetails;

import Email.EResponse.EResponseController;
import JCode.CommonTasks;
import JCode.trayHelper;
import client.dash.clientView.clientViewController;
import client.dashBaseController;
import client.newContact.newContactController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.ClientProperty;
import objects.ContactProperty;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class clientDetailsController implements Initializable {

    @FXML
    private Label txt_fname;

    @FXML
    private Label txt_website;

    @FXML
    private VBox email_box;

    @FXML
    private VBox phone_box;

    @FXML
    private JFXButton btn_back;

    @FXML
    private JFXButton btn_email;

    @FXML
    private JFXButton btn_edit;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Contacts"));
        btn_back.setOnAction(event -> {
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/dash/clientView/clientView.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ClientProperty contact = clientViewController.staticContact;

        btn_email.setOnAction(event -> {
            EResponseController.stTo = contact.getEmail();
            EResponseController.stInstance = 'N';
            inflateEResponse(1);
        });

        btn_edit.setOnAction(event -> {
            newContactController.stInstance = 'U';
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/newClient/newClientController.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void inflateEResponse(int i) {
        try {
            EResponseController.choice = i;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../../Email/EResponse/EResponse.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("New Email");
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
