package client.dash.contactView;

import JCode.mySqlConn;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import objects.ContactProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class contactViewController implements Initializable {

    mySqlConn sql;

    @FXML
    AnchorPane contactAnchor;
    @FXML
    AnchorPane toolbar_contacts;
    @FXML
    Label txt_no;
    @FXML
    JFXButton btn_email;
    @FXML
    TableView<ContactProperty> table_contact;
    @FXML
    TableColumn<ContactProperty, String> col_name;
    @FXML
    TableColumn<ContactProperty, String> col_city;
    @FXML
    TableColumn<ContactProperty, String> col_country;

    List<ContactProperty> selectedContacts;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        toolbar_contacts.setVisible(false);

        table_contact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("country"));

        table_contact.getItems().setAll(sql.getAllContactsProperty(null));

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

    }

    public void onEmailSending(ActionEvent actionEvent) {
    }
}
