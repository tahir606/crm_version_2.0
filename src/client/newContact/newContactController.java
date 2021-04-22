package client.newContact;

import ApiHandler.RequestHandler;
import Email.EResponse.EResponseController;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.Toast;
import client.dash.contactView.contactViewController;
import client.dashBaseController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import objects.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class newContactController implements Initializable {


    @FXML
    private Label txt_heading;
    @FXML
    private JFXTextField txt_fname;
    @FXML
    private JFXTextField txt_lname;
    @FXML
    private JFXButton btn_back;
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


    //    static ContactProperty contact;
    Contact contact;
    private List<ClientProperty> allClients;

    public static char stInstance;
    List<Client> clients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        contact = new ContactProperty();
        contact = new Contact();
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Contacts"));
        btn_back.setOnAction(event -> {
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/dash/contactView/contactView.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        try {
            clients = RequestHandler.listRequestHandler(RequestHandler.run("client/clientList"), Client.class);


        } catch (IOException e) {
            e.printStackTrace();
        }
        client_list.getItems().addAll(clients);

        txt_fname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                //prevents from the new space char
                txt_fname.setText(oldValue);
            }
        });

        txt_lname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                //prevents from the new space char
                txt_lname.setText(oldValue);
            }
        });

        switch (stInstance) {
            case 'N': {      //New
                txt_heading.setText("New Contact");
                btn_save.setText("Create");
                break;
            }
            case 'U': {      //Update
                txt_heading.setText("Update Contact");
                contact.setClientID(contactViewController.staticContact.getClientID());
                btn_save.setText("Update");
                populateContact();
                break;
            }
            default:
                break;
        }

    }

    public void populateContact() {
        Contact contact = contactViewController.staticContact;
        txt_fname.setText(contact.getFirstName());
        txt_lname.setText(contact.getLastName());
        txt_email.setText(contact.getCoEmailLists().get(0).getAddress());
        txt_mobile.setText(contact.getCoPhoneLists().get(0).getNumber());
        txt_addr.setText(contact.getAddress());
        txt_city.setText(contact.getCity());
        txt_country.setText(contact.getCountry());
        txt_note.setText(contact.getNote());

        date_of_birth.setValue(CommonTasks.createLocalDate(contact.getDateOfBirth()));

        for (Client c : clients) {
            if (c.getClientID() == contact.getClientID()) {
                client_list.getSelectionModel().select(c);
                break;
            }
        }
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException {
        String fname = txt_fname.getText(),
                lname = txt_lname.getText(),
                email = txt_email.getText(),
                mobile = txt_mobile.getText(),
                addr = txt_addr.getText(),
                city = txt_city.getText(),
                country = txt_country.getText(),
                note = txt_note.getText(),
                jdate = String.valueOf(date_of_birth.getValue());

        Client c = (Client) client_list.getSelectionModel().getSelectedItem();

        if (fname.equals("") || lname.equals("") || city.equals("") || country.equals("") || c == null || email.equals("") || note.equals("") || jdate.equals("") || addr.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        }  else {
            if (!email.equals("")) {
               if (!EResponseController.validateAddress(email)){
                    Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Email is Not Correct");
                    return;
                }
            }
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
                int userId = FileHelper.ReadUserApiDetails().getUserCode();
                contact.setCreatedBy(userId);
                if (jdate.equals("null")) {
                    contact.setDateOfBirth(null);
                    contact.setCreatedOn(null);
                } else {
                    contact.setCreatedOn(jdate);
                    contact.setDateOfBirth(jdate);
                }


                int clientId = 0;
                if (c != null)
                    clientId = c.getClientID();
                contact.setClientID(clientId);
                switch (stInstance) {
                    case 'N': {
                        try {
                            Contact contact1 = (Contact) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("contact/addContact", RequestHandler.writeJSON(contact)), Contact.class);
                           if (contact1 != null) {
                                RequestHandler.post("emailList/addEmail", RequestHandler.writeJSON(new EmailList(email, userId, clientId, contact1.getContactID())));
                                RequestHandler.post("phoneList/addPhone", RequestHandler.writeJSON(new PhoneList(mobile, userId, clientId, contact1.getContactID())));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 'U': {
                        contact.setContactID(contactViewController.staticContact.getContactID());
                        Contact contact1 = (Contact) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("contact/addContact", RequestHandler.writeJSON(contact)), Contact.class);
                        if (contact1 != null) {
                            for (EmailList emailList : contactViewController.staticContact.getCoEmailLists()) {
                                RequestHandler.post("emailList/addEmail", RequestHandler.writeJSON(new EmailList(emailList.getEmailID(), email, userId, clientId, contact1.getContactID())));
                            }
                            for (PhoneList phoneList : contactViewController.staticContact.getCoPhoneLists()) {
                                RequestHandler.post("phoneList/addPhone", RequestHandler.writeJSON(new PhoneList(phoneList.getPhoneID(), mobile, userId, clientId, contact1.getContactID())));

                            }
                        }
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
