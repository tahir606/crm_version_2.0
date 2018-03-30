package client.contactView;

import JCode.Toast;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Client;
import objects.Contact;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class contactViewController implements Initializable {

    TableView<Contact> contactTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void createTable() {

        contactTable = new TableView<>();

        TableColumn<Contact, JFXCheckBox> checkBoxColumn = new TableColumn<>("");

        TableColumn<Contact, String> nameColumn = new TableColumn<>("Name");

        TableColumn<Contact, String> emailColumn = new TableColumn<>("Email");

        TableColumn<Contact, String> phoneColumn = new TableColumn<>("Phone");

        TableColumn<Contact, String> clientColumn = new TableColumn<>("Client");

        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("check_box"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));

        nameColumn.setSortType(TableColumn.SortType.ASCENDING);

//        ObservableList<Contact> list =

    }

}
