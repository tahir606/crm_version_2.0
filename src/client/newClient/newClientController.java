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
    private JFXTextField txt_company;
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

        nClient = clientList.get(clientList.size() - 1).getCode() + 1; //Get CL_ID for new client
        System.out.println(nClient);

        Client c = new Client();
        c.setCode(nClient);
        c.setName(" + Create New");
        c.setCompany("");
        c.setEmail("");
        c.setPhone("");
        c.setWebsite("");
        c.setAddr("");
        c.setCity("");
        c.setCountry("");
        c.setType(1);

        combo_client.getItems().add(c);

        combo_client.getItems().addAll(clientList);
        combo_client.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getCode() != nClient)
                populateDetails(newValue);
            clientSel = newValue;
        });
        combo_type.getSelectionModel().select(0);
        combo_client.getSelectionModel().select(0);
    }

    private void populateDetails(Client newValue) {

        if (newValue == null || newValue.getName().equals(" + Create New"))
            txt_name.setText("");
        else
            txt_name.setText(newValue.getName());
        txt_company.setText(newValue.getCompany());
        txt_email.setText(newValue.getEmail());
        txt_phone.setText(newValue.getPhone());
        txt_website.setText(newValue.getWebsite());
        txt_addr.setText(newValue.getAddr());
        txt_city.setText(newValue.getCity());
        txt_country.setText(newValue.getCountry());
        if (newValue.getJoinDate() != null)
            joining_date.setValue(LocalDate.parse(newValue.getJoinDate()));

    }

    public void saveChanges(ActionEvent actionEvent) {
        String name = txt_name.getText(),
                company = txt_company.getText(),
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
                Client client = new Client();
                client.setCode(nClient);
                client.setName(name);
                client.setCompany(company);
                client.setEmail(email);
                client.setPhone(phone);
                client.setWebsite(website);
                client.setAddr(addr);
                client.setCity(city);
                client.setCountry(country);
                client.setJoinDate(jdate);
                client.setType(type);

                if (nClient == client.getCode())
                    sql.updateClient(client);
                else
                    sql.insertClient(client);

//                txt_name.clear();
//                txt_company.clear();
//                txt_email.clear();
//                txt_phone.clear();
//                txt_website.clear();
//                txt_addr.clear();
//                txt_city.clear();
//                txt_country.clear();
//                joining_date.setValue(null);

                init();

//                combo_type.getSelectionModel().select(0);
            } else {
                return;
            }
        }

    }
}
