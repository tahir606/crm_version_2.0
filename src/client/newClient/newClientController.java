package client.newClient;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.Toast;
import JCode.trayHelper;
import client.dash.clientView.clientViewController;
import client.dashBaseController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Client;
import objects.ClientType;
import objects.EmailList;
import objects.PhoneList;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class newClientController implements Initializable {

    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_owner;
    @FXML
    private JFXButton btn_email;
    @FXML
    private JFXButton btn_phone;
    @FXML
    private JFXButton btn_back;
    @FXML
    private JFXTextField txt_website;
    @FXML
    private JFXTextField txt_addr;
    @FXML
    private JFXTextField txt_city;
    @FXML
    private JFXTextField txt_country;
    @FXML
    private Label txt_heading;
    @FXML
    private JFXDatePicker joining_date;
    @FXML
    private JFXComboBox<ClientType> combo_type;
    @FXML
    private JFXButton btn_save;


    private List<ClientType> types;

    private Client clientSel;
    public static char stInstance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientSel = new Client();
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Contacts"));
        btn_back.setOnAction(event -> {
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/dash/clientView/clientView.fxml")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        try {
            types = RequestHandler.listRequestHandler(RequestHandler.run("clientType/getClientType"), ClientType.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        combo_type.getItems().setAll(types);

        switch (stInstance) {
            case 'N': {      //New
                txt_heading.setText("New Client");
                btn_save.setText("Create");
                break;
            }
            case 'U': {      //Update
                txt_heading.setText("Update Client");
                btn_save.setText("Update");
                populateDetails(clientViewController.staticClient);
                break;
            }
            default:
                break;
        }
    }

    private void init() {

        clientSel.setName("");
        clientSel.setOwner("");
        clientSel.setWebsite("");
        clientSel.setAddress("");
        clientSel.setCity("");
        clientSel.setCountry("");
        clientSel.setType(1);
        clientSel.setClEmailLists(new ArrayList<>());
        clientSel.setClPhoneLists(new ArrayList<>());

        combo_type.getSelectionModel().select(0);
        populateDetails(clientSel);
    }

    private void populateDetails(Client client) {
        if (client == null)
            return;
        else if (client.getName().equals(" + Create New"))
            txt_name.setText("");
        else
            txt_name.setText(client.getName());

        txt_owner.setText(client.getOwner());
        txt_website.setText(client.getWebsite());
        txt_addr.setText(client.getAddress());
        txt_city.setText(client.getCity());
        txt_country.setText(client.getCountry());
//        joining_date.setValue(CommonTasks.createLocalDate(client.getJoinDate()));
        if (client.getJoinDate() != null)
            joining_date.setValue(CommonTasks.createLocalDateForFilter(client.getJoinDate()));
        else
            joining_date.setValue(null);

        combo_type.getSelectionModel().select(client.getType() - 1);  //Types in database start from 1

    }


    public void saveChanges(ActionEvent actionEvent) throws IOException {
        String name = txt_name.getText(),
                owner = txt_owner.getText(),
                website = txt_website.getText(),
                addr = txt_addr.getText(),
                city = txt_city.getText(),
                country = txt_country.getText(),
                jdate = String.valueOf(joining_date.getValue());


        int type = combo_type.getSelectionModel().getSelectedIndex() + 1;

        if (name.equals("") || city.equals("") || country.equals("") || owner.equals("") || website.equals("") || addr.equals("") || jdate.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        } else {
            String msg = "";
            switch (stInstance) {
                case 'N': {
                    msg = "Are you sure you want to add Client?";
                    break;
                }
                case 'U': {
                    msg = "Are you sure you want to update Client?";
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

                clientSel.setName(name);
                clientSel.setOwner(owner);
                clientSel.setWebsite(website);
                clientSel.setAddress(addr);
                clientSel.setCity(city);
                clientSel.setCountry(country);
                clientSel.setJoinDate(jdate);
                clientSel.setType(type);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                clientSel.setCreatedOn(String.valueOf(formatter.format(date)));
                int userId = FileHelper.ReadUserApiDetails().getUserCode();
                clientSel.setCreatedBy(userId);

                if (!emailLists.isEmpty()) {
                    clientSel.setEmail(emailLists.get(0).getAddress());
                }
                if (!phoneLists.isEmpty()) {
                    clientSel.setPhoneNo(phoneLists.get(0).getNumber());
                }

                switch (stInstance) {
                    case 'N': {
                        System.out.println("new");
                        try {
                            Client client = (Client) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("client/addClient", RequestHandler.writeJSON(clientSel)), Client.class);
                            if (client != null) {
                                for (EmailList emailList : emailLists) {
                                    RequestHandler.post("emailList/addEmail", RequestHandler.writeJSON((new EmailList(emailList.getAddress(), userId, client.getClientID()))));
                                }
                                for (PhoneList phoneList : phoneLists) {
                                    RequestHandler.post("phoneList/addPhone", RequestHandler.writeJSON(new PhoneList(phoneList.getNumber(), userId, client.getClientID())));
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case 'U': {
                        System.out.println("Update");
                        clientSel.setClientID(clientViewController.staticClient.getClientID());
                        Client client = (Client) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("client/addClient", RequestHandler.writeJSON(clientSel)), Client.class);
                        if (client != null) {
                            try {
                                System.out.println(RequestHandler.run("emailList/deleteEmailList/" + client.getClientID()));
                                RequestHandler.run("emailList/deleteEmailList/" + client.getClientID());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                RequestHandler.run("phoneList/deletePhoneList/" + client.getClientID());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            for (EmailList emailList : emailLists) {
                                RequestHandler.post("emailList/addEmail", RequestHandler.writeJSON((new EmailList(emailList.getEmailID(), emailList.getAddress(), userId, client.getClientID()))));
                            }
                            for (PhoneList phoneList : phoneLists) {
                                RequestHandler.post("phoneList/addPhone", RequestHandler.writeJSON(new PhoneList(phoneList.getPhoneID(), phoneList.getNumber(), userId, client.getClientID())));
                            }
                        }
                        break;
                    }
                }
                init();

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

    public static int noOfFields = 30;
    List<EmailList> emailLists = new ArrayList<>();
    List<PhoneList> phoneLists = new ArrayList<>();

    private void inflateBOX(int c) {

        List<EmailList> Emails = new ArrayList<>();
        List<PhoneList> Phones = new ArrayList<>();

        switch (stInstance) {
            case 'N': {
                Emails = new ArrayList<>();
                Phones = new ArrayList<>();
                break;
            }
            case 'U': {
                Emails = clientViewController.staticClient.getClEmailLists();
                Phones = clientViewController.staticClient.getClPhoneLists();
                break;
            }
            default: {
                break;
            }
        }


        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        String title = (c == 1) ? "Email" : "Phone";
        stage.setTitle(title);
        VBox pane = new VBox();
        pane.setMinWidth(200);
        pane.setMinHeight(230);
        for (int i = 0; i < noOfFields; i++) {
            JFXTextField txt_data = new JFXTextField();
            txt_data.setMinWidth(pane.getWidth());
            txt_data.setPadding(new Insets(2, 2, 2, 2));
            txt_data.setFocusColor(Paint.valueOf("#006e0e"));
            pane.getChildren().add(txt_data);
        }

        if (c == 1) {        //Email
            int j = 0;
            for (EmailList emailList : Emails) {
                if (Emails.isEmpty()) {
                    ((JFXTextField) pane.getChildren().get(j)).setText("");
                } else {
                    ((JFXTextField) pane.getChildren().get(j)).setText(emailList.getAddress());
                }
                j++;
            }
        } else if (c == 2) {
            //Phone
            int k = 0;
            for (PhoneList phoneList : Phones) {
                if (Phones.isEmpty()) {
                    ((JFXTextField) pane.getChildren().get(k)).setText("");
                } else {
                    ((JFXTextField) pane.getChildren().get(k)).setText(phoneList.getNumber());
                }
                k++;
            }
        }

        stage.setScene(new Scene(pane));
        trayHelper tray = new trayHelper();
        tray.createIcon(stage);
        Platform.setImplicitExit(true);

        stage.setOnHiding(event -> {
            for (int i = 0; i < noOfFields; i++) {
                String t = ((JFXTextField) pane.getChildren().get(i)).getText();
                if (t != null) {
                    if (!t.equals("")) {
                        if (c == 1) {     //Email
                            emailLists.add(new EmailList(t));
                        } else if (c == 2) {    //Phone
                            phoneLists.add(new PhoneList(t));
                        }
                    }
                }
            }
        });

        stage.show();
    }
}
