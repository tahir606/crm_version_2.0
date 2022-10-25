package client.dash.contactView.contactDetails;

import Email.EResponse.EResponseController;
import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import client.dash.contactView.contactViewController;
import client.dashBaseController;
import client.newContact.newContactController;
import com.jfoenix.controls.JFXButton;
import gui.NotesConstructor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static JCode.CommonTasks.getSimpleDate;

public class
contactDetailsController implements Initializable {

    @FXML
    private AnchorPane tab_anchor;
    @FXML
    private AnchorPane notes_anchor;
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
    @FXML
    private VBox notes_list;
    @FXML
    private VBox vbox_main;

    private mySqlConn sql;

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
        Contact contact = contactViewController.staticContact;
        populateDetails(contact);

        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(600);
        new NotesConstructor(tabPane,  contact).generalConstructor(1);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);

        vbox_main.getChildren().add(tabPane);

    }

    private void inflateEResponse(int i) {
        try {
            EResponseController.choice = i;
            FXMLLoader fxmlLoader;
            if (getClass().getResource("../../../Email/EResponse/EResponse.fxml") == null) {
                fxmlLoader = new FXMLLoader(getClass().getResource("/Email/EResponse/EResponse.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(getClass().getResource("../../../Email/EResponse/EResponse.fxml"));
            }

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

    private void populateDetails(Contact contact) {
        txt_fname.setText(contact.getFirstName());
        if (!contact.getCoEmailLists().isEmpty()){
            txt_email.setText(contact.getCoEmailLists().get(0).getAddress());

        }
        if (!contact.getCoPhoneLists().isEmpty()){
            txt_mobile.setText(contact.getCoPhoneLists().get(0).getNumber());
        }
        txt_client.setText(contact.getClient12().getName());
        txt_dob.setText(getSimpleDate(contact.getDateOfBirth()));
        txt_age.setText(CommonTasks.getAge(contact.getDateOfBirth()));

        btn_email.setOnAction(event -> {
            if (contact.getCoEmailLists().isEmpty()){
                EResponseController.stTo = Collections.singletonList("");
            }else{
                EResponseController.stTo = Collections.singletonList(contact.getCoEmailLists().get(0).getAddress());

            }
            EResponseController.stInstance = 'N';
            inflateEResponse(1);
        });

        btn_edit.setOnAction(event -> {
            newContactController.stInstance = 'U';
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/newContact/newContact.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
