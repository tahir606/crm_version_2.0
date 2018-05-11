package product.newProduct;

import JCode.Toast;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import objects.ClientProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class NewProductController implements Initializable {

    public static char stInstance;

    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_price;
    @FXML
    private JFXTextField combo_status;
    @FXML
    private JFXTextField combo_type;
    @FXML
    private JFXButton txt_desc;
    @FXML
    private JFXButton btn_save;
    @FXML
    private Label txt_heading;
    @FXML
    private JFXDatePicker started_date;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void saveChanges(ActionEvent actionEvent) {
        String name = txt_name.getText(),
                price = txt_price.getText(),
                desc = txt_email.getText(),
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
            String msg = "";
            switch (stInstance) {
                case 'N': {
                    msg = "Are you sure you want to add Contact?";
                    break;
                }
                case 'U': {
                    msg = "Are you sure you want to update Contact?";
                    break;
                }
                default: {
                    break;
                }
            }
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, msg,
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
//                contact.setEmails(new String[]{email});
//                contact.setPhones(new String[]{mobile});
                contact.setEmail(email);
                contact.setMobile(mobile);

                ClientProperty c = (ClientProperty) client_list.getSelectionModel().getSelectedItem();

                if (c != null)
                    contact.setClID(c.getCode());

                System.out.println(contact);

                switch (stInstance) {
                    case 'N': {
                        sql.insertContact(contact);
                        break;
                    }
                    case 'U': {
                        sql.updateContact(contact);
                        break;
                    }
                    default: {
                        break;
                    }
                }

            } else {
                return;
            }
        }
    }
}
