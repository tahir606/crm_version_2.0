package client.contactView;

import JCode.Toast;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Client;
import objects.Contact;
import objects.ContactProperty;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class contactViewController implements Initializable {

    JFXTreeTableView contactTable;

    mySqlConn sql;

    @FXML
    AnchorPane contactAnchor;
    @FXML
    TableView<ContactProperty> table_contact;
    @FXML
    TableColumn<ContactProperty, String> col_name;
    @FXML
    TableColumn<ContactProperty, String> col_city;
    @FXML
    TableColumn<ContactProperty, String> col_country;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        table_contact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("city"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("country"));

        table_contact.getItems().setAll(sql.getAllContactsProperty(null));

        table_contact.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            List<ContactProperty> contacts = table_contact.getSelectionModel().getSelectedItems();

            if (contacts == null) {
                System.out.println("Nothing Selected");
                return;
            }

            System.out.println("Selected Items: " + contacts.size());

            for (ContactProperty c : contacts) {
                System.out.println(c.getFirstName() + " " + c.getLastName());
            }

        });

//        createTable();
    }

//    private void createTable() {
//
//        contactTable = new TableView<>();
//
//        TableColumn<Contact, JFXCheckBox> checkBoxColumn = new TableColumn<>("");
//        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");
//        TableColumn<Contact, String> emailColumn = new TableColumn<>("Email");
//        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Phone");
//        TableColumn<Contact, String> clientColumn = new TableColumn<>("Client");
//
//        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("check_box"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
//        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
//        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
//
//        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
//
//        List<Contact> contacts = sql.getAllContacts(null);
//        ObservableList<Contact> list = FXCollections.observableArrayList(contacts);
//        contactTable.setItems(list);
//
//        contactTable.getColumns().addAll(checkBoxColumn, nameColumn, emailColumn, phoneColumn, clientColumn);
//
//        contactAnchor.getChildren().add(contactTable);
//
//    }

}
