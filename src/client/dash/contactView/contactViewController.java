package client.dash.contactView;

import ApiHandler.RequestHandler;
import Email.EResponse.EResponseController;
import JCode.trayHelper;
import client.dashBaseController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.Contact;
import objects.ContactProperty;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class contactViewController implements Initializable {

    @FXML
    AnchorPane contactAnchor;
    @FXML
    AnchorPane toolbar_contacts;
    @FXML
    Label txt_no;
    @FXML
    JFXButton btn_email;
    @FXML
    TableView<Contact> table_contact;
    @FXML
    TableColumn<ContactProperty, String> col_name;
    @FXML
    TableColumn<ContactProperty, String> col_age;
    @FXML
    TableColumn<ContactProperty, String> col_email;
    @FXML
    TableColumn<ContactProperty, String> col_mobile;
    @FXML
    TableColumn<ContactProperty, String> col_city;
    @FXML
    TableColumn<ContactProperty, String> col_country;

    List<Contact> selectedContacts;
    public static Contact staticContact;
    List<Contact> contacts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            contacts = RequestHandler.listRequestHandler(RequestHandler.run("contact/contactList"), Contact.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        toolbar_contacts.setVisible(false);
        table_contact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_age.setCellValueFactory(new PropertyValueFactory<>("Age"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("country"));

        table_contact.getItems().setAll(contacts);

        table_contact.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            selectedContacts = table_contact.getSelectionModel().getSelectedItems();
            if (selectedContacts == null || selectedContacts.size() == 0) {
                toolbar_contacts.setVisible(false);
                return;
            } else {
                toolbar_contacts.setVisible(true);
                txt_no.setText(String.valueOf(selectedContacts.size()));
            }

        });

        table_contact.setRowFactory(tv -> {
            TableRow<Contact> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    staticContact = row.getItem();
                    try {
                        dashBaseController.main_paneF.setCenter(
                                FXMLLoader.load(
                                        getClass().getClassLoader().getResource
                                                ("client/dash/contactView/contactDetails/contactDetails.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }

    public void onEmailSending(ActionEvent actionEvent) {

        String email = "";
        for (Contact contact : selectedContacts) {
            if (!contact.getCoEmailLists().isEmpty()) {
                if (contact.getCoEmailLists().get(0).getAddress() != null || contact.getCoEmailLists().get(0).getAddress().equals(""))
                    email = email + contact.getCoEmailLists().get(0).getAddress() + ",";
            }

        }


        EResponseController.stTo = Collections.singletonList(email);
        EResponseController.stInstance = 'R';

        inflateEResponse(1);

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
}
