package activity.details_event;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.event.NewEventController;
import activity.view.ActivityViewController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import objects.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventDetailsController implements Initializable {

    @FXML
    private Label txt_title, txt_location, txt_fromDate, txt_toDate, txt_createdOn, txt_createdBy, txt_type, txt_name;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_back, btn_edit, btn_close;
    @FXML
    private HBox hbox_tools;
//    @FXML
//    private VBox notes_list;

    private mySqlConn sql;

    private Event event;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();

        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Activities"));
        btn_back.setOnAction(event -> {
            try {
                ActivityDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("activity/view/activity_view.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        event = ActivityViewController.staticEvent;
        populateDetails();
    }

    private void populateDetails() {
        txt_title.setText(event.getTittle());
        txt_location.setText(event.getLocation());
        txt_fromDate.setText(event.getFrom());
        txt_toDate.setText(event.getTo());
        txt_createdOn.setText(event.getCreatedOn());
        txt_createdBy.setText(event.getUsers().getFullName());
        txt_desc.setText(event.getDescription());

        txt_type.setVisible(true);
        txt_name.setVisible(true);

        if (event.getClientID() != 0) {
            txt_type.setText("Client");
            txt_name.setText(event.getUsers().getFullName());
        } else if (event.getLeadsId() != 0) {
            txt_type.setText("Lead");
            txt_name.setText(event.getUsers().getFullName());
        } else {
            txt_type.setVisible(false);
            txt_name.setVisible(false);
        }

        if (event.getStatus()==0)
            btn_close.setDisable(false);
        else
            btn_close.setDisable(true);

        btn_close.setOnAction(event -> {
            this.event.setClosedOn(CommonTasks.getCurrentTimeStamp());
            this.event.setStatus(1);
            String responseMessage = "";
            try {
                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("event/addEvent", RequestHandler.writeJSON(this.event)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText((Stage) btn_close.getScene().getWindow(), responseMessage);
//            sql.closeEvent(this.event);
            btn_close.setDisable(true);
        });

        btn_edit.setOnAction(event -> {
            NewEventController.stInstance = 'D';
            CommonTasks.inflateDialog("Update Event", "/activity/event/new_event.fxml");
        });

        JFXButton buttonArchive = new JFXButton("Archive");
        buttonArchive.getStyleClass().add("emailDetailsButton");
        buttonArchive.setPrefWidth(84);
        buttonArchive.setPrefHeight(34);
        buttonArchive.setOnAction(event -> {
            String responseMessage = "";
            try {
                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.run("event/deleteEvent/"+this.event.getEventID()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        /*    try {
                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("event/addEvent", RequestHandler.writeJSON(this.event)));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Toast.makeText((Stage) buttonArchive.getScene().getWindow(), responseMessage);
//            sql.archiveEvent(this.event);
            CommonTasks.loadInPane(ActivityDashController.main_paneF, "activity/view/activity_view.fxml");
        });
        hbox_tools.getChildren().add(buttonArchive);

//        new NotesConstructor(notes_list, sql, lead).generalConstructor(3);
//        new TasksConstructor(open_activities_list, closed_activities_list, lead).generalConstructor(3);
    }
}

