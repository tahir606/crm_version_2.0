package client.newClient;

import JCode.Toast;
import JCode.mySqlConn;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import objects.Client;
import objects.ESetting;
import objects.Users;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class newClientController implements Initializable {

    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_owner;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_phone;
    @FXML
    private JFXTextField txt_website;
    @FXML
    private JFXTextField txt_addr;
    @FXML
    private JFXTextField txt_city;
    @FXML
    private JFXTextField txt_country;
    @FXML
    private JFXDatePicker joining_date;
    @FXML
    private JFXComboBox<String> combo_type;
    @FXML
    private JFXComboBox<Client> combo_client;
    @FXML
    private JFXButton btn_save;

    private mySqlConn sql;

    private List<Client> clientList;
    private List<String> types;
    private int nClient;    //CL_ID for new Client

    private Client clientSel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        init();
    }

    private void init() {
        combo_client.getItems().clear();

        clientList = sql.getAllClients(null);
        types = sql.getClientTypes();

        combo_type.getItems().setAll(types);
        try {
            nClient = clientList.get(clientList.size() - 1).getCode() + 1; //Get CL_ID for new client
        } catch (NullPointerException e) {
            nClient = 1;
        }
//        System.out.println(nClient);

        Client c = new Client();
        c.setCode(nClient);
        c.setName(" + Create New");
        c.setOwner("");
        c.setEmail("");
        c.setPhone("");
        c.setWebsite("");
        c.setAddr("");
        c.setCity("");
        c.setCountry("");
        c.setType(1);

        combo_client.getItems().add(c);

        if (nClient != 1)   //If there are no clients previously, else this throws a null pointer exception
            combo_client.getItems().addAll(clientList);
        combo_client.valueProperty().addListener((observable, oldValue, newValue) -> {
            populateDetails(newValue);
            clientSel = newValue;
        });
        combo_type.getSelectionModel().select(0);
        combo_client.getSelectionModel().select(0);
    }

    private void populateDetails(Client newValue) {

        System.out.println("Populating");

        if (newValue == null || newValue.getName().equals(" + Create New"))
            txt_name.setText("");
        else
            txt_name.setText(newValue.getName());

        txt_owner.setText(newValue.getOwner());
        txt_email.setText(newValue.getEmail());
        txt_phone.setText(newValue.getPhone());
        txt_website.setText(newValue.getWebsite());
        txt_addr.setText(newValue.getAddr());
        txt_city.setText(newValue.getCity());
        txt_country.setText(newValue.getCountry());
        if (newValue.getJoinDate() != null)
            joining_date.setValue(LocalDate.parse(newValue.getJoinDate()));
        else
            joining_date.setValue(null);

    }

    public void saveChanges(ActionEvent actionEvent) {
        String name = txt_name.getText(),
                owner = txt_owner.getText(),
                email = txt_email.getText(),
                phone = txt_phone.getText(),
                website = txt_website.getText(),
                addr = txt_addr.getText(),
                city = txt_city.getText(),
                country = txt_country.getText(),
                jdate = String.valueOf(joining_date.getValue());

        int type = combo_type.getSelectionModel().getSelectedIndex() + 1;

        if (name.equals("") || email.equals("") || phone.equals("") || city.equals("") || country.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add Client? ",
                    ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();

            if (alert2.getResult() == ButtonType.YES) {

                clientSel.setName(name);
                clientSel.setOwner(owner);
                clientSel.setEmail(email);
                clientSel.setPhone(phone);
                clientSel.setWebsite(website);
                clientSel.setAddr(addr);
                clientSel.setCity(city);
                clientSel.setCountry(country);
                clientSel.setJoinDate(jdate);
                clientSel.setType(type);

                if (nClient == clientSel.getCode()) {
                    System.out.println("Inserting");
                    sql.insertClient(clientSel);
                } else {
                    System.out.println("Updating");
                    sql.updateClient(clientSel);
                }
                init();

            } else {
                return;
            }
        }

    }
}