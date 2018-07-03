package activity.details;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.task.NewTaskController;
import activity.view.ActivityViewController;
import com.jfoenix.controls.JFXButton;
import gui.ActivitiesConstructor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import objects.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityDetailsController implements Initializable {

    @FXML
    private Label txt_subject;
    @FXML
    private Label txt_dueDate;
    @FXML
    private Label txt_createdOn;
    @FXML
    private Label txt_createdBy;

    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_back;
    @FXML
    private JFXButton btn_edit;
//    @FXML
//    private VBox notes_list;

    private mySqlConn sql;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

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

        task = ActivityViewController.staticTask;
        populateDetails();
    }

    private void populateDetails() {
        txt_subject.setText(task.getSubject());
        txt_dueDate.setText(CommonTasks.getDateFormatted(task.getDueDate()));
        txt_createdOn.setText(task.getCreatedOn());
        txt_createdBy.setText(task.getCreatedBy());
        txt_desc.setText(task.getDesc());

//        btn_email.setOnAction(event -> {
//            EResponseController.stTo = contact.getEmail();
//            EResponseController.stInstance = 'N';
//            inflateEResponse(1);
//        });

        btn_edit.setOnAction(event -> {
            NewTaskController.stInstance = 'D';
//                ActivityDashController.main_paneF.setCenter(
//                        FXMLLoader.load(
//                                getClass().getClassLoader().getResource("activity/task/new_task.fxml")));
            CommonTasks.inflateDialog("Update Task", ActivitiesConstructor.class.getResource("../activity/task/new_task.fxml"));

        });

//        new NotesConstructor(notes_list, sql, lead).generalConstructor(3);
//        new ActivitiesConstructor(open_activities_list, closed_activities_list, lead).generalConstructor(3);
    }
}

