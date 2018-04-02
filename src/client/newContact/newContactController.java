package client.newContact;

import JCode.Toast;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
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

public class newContactController implements Initializable {

    @FXML
    private JFXTextField txt_fname;
    @FXML
    private JFXTextField txt_lname;
    @FXML
    private JFXButton btn_email;
    @FXML
    private JFXButton btn_phone;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_mobile;
    @FXML
    private JFXTextField txt_addr;
    @FXML
    private JFXTextField txt_city;
    @FXML
    private JFXTextField txt_country;
    @FXML
    private TextArea txt_note;
    @FXML
    private JFXDatePicker date_of_birth;
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXComboBox client_list;

    mySqlConn sql;

    static Contact contact;

    private List<Client> allClients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        contact = new Contact();

        contact.setCode(sql.getNewContactCode());

        allClients = sql.getAllClients("CL_TYPE = 1");
        client_list.getItems().addAll(allClients);
    }

    public void saveChanges(ActionEvent actionEvent) {
        String fname = txt_fname.getText(),
                lname = txt_lname.getText(),
                email = txt_email.getText(),
                mobile = txt_mobile.getText(),
                addr = txt_addr.getText(),
                city = txt_city.getText(),
                country = txt_country.getText(),
                note = txt_note.getText(),
                jdate = String.valueOf(date_of_birth.getValue());


        if (fname.equals("") || lname.equals("") || city.equals("") || country.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        } else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to add Contact? ",
                    ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();

            if (alert2.getResult() == ButtonType.YES) {

                fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);   //Make First Letter to Uppercase
                lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);

                contact.setFirstName(fname);
                contact.setLastName(lname);
                contact.setAddress(addr);
                contact.setCity(city);
                contact.setCountry(country);
                contact.setNote(note);
                if (jdate.equals("null"))
                    contact.setDob(null);
                else
                    contact.setDob(jdate);

                Client c = (Client) client_list.getSelectionModel().getSelectedItem();

                if (c != null)
                    contact.setClientCode(c.getCode());

                System.out.println(contact);

                sql.insertContact(contact);

            } else {
                return;
            }
        }

    }

    public void inflateEmail(ActionEvent actionEvent) {
        inflateBOX(1);
    }

    public void inflatePhone(ActionEvent actionEvent) {
        inflateBOX(2);
    }

    public static int noOfFields = 1;

    private void inflateBOX(int c) {

        String[] Emails = contact.getEmails();
        String[] Phones = contact.getPhones();

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        String title = (c == 1) ? "Email" : "Phone";
        stage.setTitle(title);
        VBox pane = new VBox();
        pane.setMinWidth(200);
        pane.setMinHeight(130);
        for (int i = 0; i < noOfFields; i++) {
            JFXTextField txt_data = new JFXTextField();
            txt_data.setMinWidth(pane.getWidth());
            txt_data.setPadding(new Insets(2, 2, 2, 2));
            txt_data.setFocusColor(Paint.valueOf("#006e0e"));
            pane.getChildren().add(txt_data);
        }

        //On inflating
        for (int i = 0; i < noOfFields; i++) {
            if (c == 1)        //Email
                ((JFXTextField) pane.getChildren().get(i)).setText(Emails[i]);
            else if (c == 2)    //Phone
                ((JFXTextField) pane.getChildren().get(i)).setText(Phones[i]);
        }

        stage.setScene(new Scene(pane));
        trayHelper tray = new trayHelper();
        tray.createIcon(stage);
        Platform.setImplicitExit(true);

        stage.setOnHiding(event -> {

            String array[] = new String[noOfFields];

            for (int i = 0; i < noOfFields; i++) {
                String t = ((JFXTextField) pane.getChildren().get(i)).getText();
                if (t != null) {
                    if (!t.equals("")) {
                        if (c == 1)        //Email
                            array[i] = t;
                        else if (c == 2)    //Phone
                            array[i] = t;
                    }
                }
            }
            if (c == 1)
                contact.setEmails(array);
            else if (c == 2)
                contact.setPhones(array);
        });

        stage.show();
    }
}
