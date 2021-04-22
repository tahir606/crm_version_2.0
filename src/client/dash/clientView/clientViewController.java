package client.dash.clientView;

import ApiHandler.RequestHandler;
import Email.EResponse.EResponseController;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import client.dashBaseController;
import com.jfoenix.controls.*;
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
import objects.Client;
import objects.ClientProperty;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class clientViewController implements Initializable {

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
    TableView<Client> table_contact;
    @FXML
    TableColumn<ClientProperty, String> col_name;
    @FXML
    TableColumn<ClientProperty, String> col_website;
    @FXML
    TableColumn<ClientProperty, String> col_city;
    @FXML
    TableColumn<ClientProperty, String> col_country;

    List<Client> clients;
    List<Client> selectedClient;
    public static Client staticClient;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        try {
            clients = RequestHandler.listRequestHandler(RequestHandler.run("client/clientList"), Client.class);
          } catch (IOException e) {
            e.printStackTrace();
        }

        toolbar_contacts.setVisible(false);


        col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_website.setCellValueFactory(new PropertyValueFactory<>("Website"));
        col_city.setCellValueFactory(new PropertyValueFactory<>("City"));
        col_country.setCellValueFactory(new PropertyValueFactory<>("Country"));

        table_contact.getItems().setAll(clients);

        table_contact.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            selectedClient = table_contact.getSelectionModel().getSelectedItems();

            if (selectedClient == null || selectedClient.size() == 0) {
                toolbar_contacts.setVisible(false);
                return;
            } else {
                toolbar_contacts.setVisible(true);
                txt_no.setText(String.valueOf(selectedClient.size()));
            }

        });

       table_contact.setRowFactory(tv -> {
           TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    staticClient = row.getItem();
                    try {
                        dashBaseController.main_paneF.setCenter(
                                FXMLLoader.load(
                                        getClass().getClassLoader().getResource
                                                ("client/dash/clientView/clientDetails/clientDetails.fxml")));
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

        for (Client contact : selectedClient) {
            if (contact.getEmail() != null || contact.getEmail().equals(""))
                email = email + contact.getEmail() + ",";
        }

        EResponseController.stTo = Collections.singletonList(email);
        EResponseController.stInstance = 'R';

        inflateEResponse(1);

    }

    private void inflateEResponse(int i) {
        try {
            EResponseController.choice = i;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../Email/EResponse/EResponse.fxml"));
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
