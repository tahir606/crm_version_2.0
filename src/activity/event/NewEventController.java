package activity.event;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.view.ActivityViewController;
import client.dash.clientView.clientViewController;
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
    public static char stInstance;

    private Event currEvent;

    //    private ClientProperty client;
    private Client client;
    private Lead lead;
    private ProductProperty product;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();

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

                currEvent = EventsConstructor.updatingEvent;
                populateFields(currEvent);
                break;
            }
            case 'D': { //D for from details
                btn_save.setText("Update");

                currEvent = ActivityViewController.staticEvent;

                populateFields(currEvent);
                break;
            }
        }

        if (stInstance != 'D') {
            switch (choice) {
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
            }
        } else {
            if (currEvent.getClientID() != 0) {
                relation_type.getSelectionModel().select("Client");
                txt_name.setText(currEvent.getUsers().getFullName());
            } else if (currEvent.getLeadsId() != 0) {
                relation_type.getSelectionModel().select("Lead");
                txt_name.setText(currEvent.getUsers().getFullName());
            }
        }

        btn_save.setOnAction(event -> {
            String title = txt_title.getText().toString(),
                    desc = txt_desc.getText().toString(),
                    loc = txt_location.getText().toString(),
                    type = relation_type.getSelectionModel().getSelectedItem(),
                    name = txt_name.getText().toString();


            String fromDate = null, fromTime = null, toDate = null, toTime = null;
            if (from_date.getValue() != null)
                fromDate = from_date.getValue().toString();
            if (from_time.getValue() != null)
                fromTime = from_time.getValue().toString();
            if (to_date.getValue() != null)
                toDate = to_date.getValue().toString();
            if (to_time.getValue() != null)
                toTime = to_time.getValue().toString();
            boolean allDay = check_allDay.isSelected();

            if (title.equals("") || loc.equals("") || desc.equals("") || fromDate == null || toDate == null) {
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
                    if (toTime == null)
                        toTime = "00:00";
                    if (fromTime == null)
                        fromTime = "00:00";
                    currEvent.setTittle(title);
                    currEvent.setLocation(loc);
                    currEvent.setFrom(fromDate);
                    currEvent.setTo(toDate);
                    currEvent.setCreatedBy(FileHelper.ReadUserApiDetails().getUserCode());
//                    currEvent.setFrom(fromTime);
//                    currEvent.setTo(toTime);
                    currEvent.setDescription(desc);
                    currEvent.getEventAllDay();

                    switch (stInstance) {
                        case 'N': {
                            try {
                                if (type.equals("Client")) {
                                    currEvent.setClientID(client.getClientID());
//                                    currEvent.setClient(client.getCode());
                                } else if (type.equals("Lead")) {
                                    currEvent.setLeadsId(lead.getCode());
                                }
                            } catch (NullPointerException e) {
                                System.out.println(e);
                            }
                            String responseMessage = "";
                            try {
                                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("event/addEvent", RequestHandler.writeJSON(currEvent)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText((Stage) btn_save.getScene().getWindow(), responseMessage);
//                            sql.addEvent(currEvent);
                            break;
                        }
                        case 'U':
                        case 'D': {
                            String responseMessage = "";
                            try {
                                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("event/addEvent", RequestHandler.writeJSON(currEvent)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText((Stage) btn_save.getScene().getWindow(), responseMessage);
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
        txt_title.setText(event.getTittle());
        txt_location.setText(event.getLocation());
        txt_desc.setText(event.getDescription());
        //Dates
        if (event.getFrom() != null)
            from_date.setValue(CommonTasks.createLocalDate(event.getFrom()));

        if (event.getTo() != null)
            to_date.setValue(CommonTasks.createLocalDate(event.getTo()));

        //Times
        if (event.getFrom() != null)
            from_time.setValue(CommonTasks.getTimeFormat(event.getFrom()));

        if (event.getTo() != null)
            to_time.setValue(CommonTasks.getTimeFormat(event.getTo()));
        if (event.getEventAllDay() == 1) {
            check_allDay.setSelected(true);
        } else {
            check_allDay.setSelected(false);
        }

//
//        check_allDay.setSelected(event.isAllDay());
    }

}