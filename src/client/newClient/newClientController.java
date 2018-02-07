package client.newClient;

import JCode.Toast;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import objects.Client;
import objects.ESetting;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        List<String> types = sql.getClientTypes();

        combo_type.getItems().setAll(types);
        combo_type.getSelectionModel().select(0);
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

                sql.insertClient(client);

                txt_name.clear();
                txt_company.clear();
                txt_email.clear();
                txt_phone.clear();
                txt_website.clear();
                txt_addr.clear();
                txt_city.clear();
                txt_country.clear();

                combo_type.getSelectionModel().select(0);
            } else {
                return;
            }
        }

    }
}
