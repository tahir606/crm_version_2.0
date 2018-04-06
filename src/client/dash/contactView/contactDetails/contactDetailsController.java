package client.dash.contactView.contactDetails;

import JCode.CommonTasks;
import client.dash.contactView.contactViewController;
import client.dash.dashBaseController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.ContactProperty;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class contactDetailsController implements Initializable {

    @FXML
    private Label txt_fname;
    @FXML
    private Label txt_email;
    @FXML
    private Label txt_mobile;
    @FXML
    private Label txt_client;
    @FXML
    private Label txt_dob;
    @FXML
    private Label txt_age;
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
                                getClass().getClassLoader().getResource("client/dash/contactView/contactView.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ContactProperty contact = contactViewController.staticContact;

        txt_fname.setText(contact.getFullName());
        txt_email.setText(contact.getEmail());
        txt_mobile.setText(contact.getMobile());
        txt_client.setText(contact.getClientName());
        txt_dob.setText(CommonTasks.getDateFormatted(contact.getDob()));
        txt_age.setText(String.valueOf(contact.getAge()));

    }
}
