package activity.event;

import JCode.CommonTasks;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import client.dash.clientView.clientViewController;
import client.dash.contactView.contactViewController;
import com.jfoenix.controls.*;
import gui.EventsConstructor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lead.view.LeadViewController;
import objects.*;
import product.view.ProductViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewEventController implements Initializable {

    @FXML
    private JFXTextField txt_title;
    @FXML
    private JFXTextField txt_location;
    @FXML
    private JFXDatePicker from_date;
    @FXML
    private JFXTimePicker from_time;
    @FXML
    private JFXDatePicker to_date;
    @FXML
    private JFXTimePicker to_time;
    @FXML
    private JFXCheckBox check_allDay;
    @FXML
    private TextArea txt_desc;
    @FXML
    private HBox combo_type;
    @FXML
    private JFXComboBox<String> relation_type;
    @FXML
    private JFXTextField txt_name;
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_cancel;

    private mySqlConn sql;

    private static int choice;
    private static char stInstance;

    private Event currEvent;

    private ClientProperty client;
    private Lead lead;
    private ProductProperty product;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        relation_type.getItems().addAll("Contact", "Client", "Lead", "Product");

        choice = EventsConstructor.choice;

        relation_type.setDisable(true);
        txt_name.setDisable(true);

        switch (stInstance) {
            case 'N': {
                btn_save.setText("Add");

                currEvent = new Event();
                break;
            }
            case 'U': {
                btn_save.setText("Update");

//                event = EventsConstructor.updatingTask;
                populateFields(currEvent);
                break;
            }
            case 'D': { //D for from details
//                btn_save.setText("Update");
//
//                event = ActivityViewController.staticTask;
//                populateFields(event);
                break;
            }
        }

        if (stInstance != 'D') {
            switch (choice) {
                case 1: {       //Contacts
//                    contact = contactViewController.staticContact;
//                    relation_type.getSelectionModel().select("Contact");
//                    txt_name.setText(contact.getFullName());
//                    break;
                }
                case 2: {       //Clients
                    client = clientViewController.staticClient;
                    relation_type.getSelectionModel().select("Client");
                    txt_name.setText(client.getName());
                    break;
                }
                case 3: {
                    lead = LeadViewController.staticLead;
                    relation_type.getSelectionModel().select("Lead");
                    txt_name.setText(lead.getFullNameProperty().toString());
                    break;
                }
                case 4: {
                    product = ProductViewController.staticProduct;
                    relation_type.getSelectionModel().select("Product");
                    txt_name.setText(product.getName().toString());
                    break;
                }
            }
        } else {
            if (currEvent.getClient() != 0) {
                relation_type.getSelectionModel().select("Client");
                txt_name.setText(currEvent.getRelationName());
            } else if (currEvent.getLead() != 0) {
                relation_type.getSelectionModel().select("Lead");
                txt_name.setText(currEvent.getRelationName());
            }
        }

        btn_save.setOnAction(event -> {
            String title = txt_title.getText().toString(),
                    desc = txt_desc.getText().toString(),
                    fromDate = from_date.getValue().toString(),
                    fromTime = from_time.getValue().toString(),
                    toDate = from_date.getValue().toString(),
                    toTime = from_time.getValue().toString(),
                    type = relation_type.getSelectionModel().getSelectedItem(),
                    name = txt_name.getText().toString();
            boolean allDay = check_allDay.isSelected();

            if (title.equals("") || desc.equals("") || fromDate.equals("") || toDate.equals("")) {
                Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
                return;
            } else {
                String msg = "";
                switch (stInstance) {
                    case 'N': {
                        msg = "Are you sure you want to add Event?";
                        break;
                    }
                    case 'U': {
                        msg = "Are you sure you want to update Event?";
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

                    currEvent.setTitle(title);
                    currEvent.setFromDate(fromDate);
                    currEvent.setToDate(toDate);
                    currEvent.setFromTime(fromTime);
                    currEvent.setToTime(toTime);
                    currEvent.setDesc(desc);
                    currEvent.isAllDay();

                    switch (stInstance) {
                        case 'N': {
                            try {
                                if (type.equals("Contact")) {

                                } else if (type.equals("Client")) {
                                    currEvent.setClient(client.getCode());
                                } else if (type.equals("Lead")) {
                                    currEvent.setLead(lead.getCode());
                                }
                            } catch (NullPointerException e) {
                                System.out.println(e);
                            }
                            sql.addEvent(currEvent);
                            break;
                        }
                        case 'U': {
//                            sql.updateEvent(currEvent);
                            break;
                        }
                        case 'D': {
//                            sql.updateEvent(currEvent);
                            break;
                        }
                        default: {
                            break;
                        }
                    }

                } else {
                    return;
                }
                closeStage();
            }
        });

        btn_cancel.setOnAction(event -> closeStage());
    }

    private void closeStage() {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        if (stInstance != 'D')
            try {
                EventsConstructor.generalConstructor(choice);
            } catch (NullPointerException e) {
                try {
                    ActivityDashController.main_paneF.setCenter(
                            FXMLLoader.load(
                                    getClass().getClassLoader().getResource("activity/view/activity_view.fxml")));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        else {
            try {
                ActivityDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("activity/view/activity_view.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateFields(Event event) {
        txt_title.setText(event.getTitle());
        txt_location.setText(event.getLocation());
        if (event.getFromDate() != null)
            from_date.setValue(CommonTasks.createLocalDate(event.getFromDate()));
        else
            from_date.setValue(null);
        check_allDay.setSelected(event.isAllDay());
    }

}