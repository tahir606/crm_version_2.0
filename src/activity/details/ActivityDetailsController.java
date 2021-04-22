package activity.details;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.task.NewTaskController;
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
import objects.Task;
import objects.TaskOld;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.TasksConstructor.inflateNewTask;

public class ActivityDetailsController implements Initializable {

    @FXML
    private Label txt_subject, txt_dueDate, txt_entryDate, txt_createdOn, txt_createdBy, txt_type, txt_name;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_back, btn_edit, btn_close;
    @FXML
    private HBox hbox_tools;
//    @FXML
//    private VBox notes_list;

    private mySqlConn sql;

    private TaskOld taskOld;
    private Task task;
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

        task = ActivityViewController.staticTask;
        populateDetails();
    }

    private void populateDetails() {
        txt_subject.setText(taskOld.getSubject());
        txt_entryDate.setText(taskOld.getEntryDate() == null ? " - " : CommonTasks.getDateFormatted(taskOld.getEntryDate()));
        txt_dueDate.setText(CommonTasks.getDateFormatted(taskOld.getDueDate()));
        txt_createdOn.setText(taskOld.getCreatedOn());
        txt_createdBy.setText(taskOld.getCreatedBy());
        txt_desc.setText(taskOld.getDesc());

        txt_type.setVisible(true);
        txt_name.setVisible(true);

        if (taskOld.getClient() != 0) {
            txt_type.setText("Client");
            txt_name.setText(taskOld.getClientName());
        } else if (taskOld.getLead() != 0) {
            txt_type.setText("Lead");
            txt_name.setText(taskOld.getLeadName());
        } else if (taskOld.getProduct() != 0) {
            txt_type.setText("Product");
            txt_name.setText(taskOld.getProductName());
        } else {
            txt_type.setVisible(false);
            txt_name.setVisible(false);
        }

        if (!taskOld.isStatus())
            btn_close.setDisable(false);
        else
            btn_close.setDisable(true);

        btn_close.setOnAction(event -> {
            sql.closeTask(taskOld);
            btn_close.setDisable(true);
        });

        btn_edit.setOnAction(event -> {
            NewTaskController.stInstance = 'D';
            inflateNewTask("Update Task");
        });

        JFXButton buttonArchive = new JFXButton("Archive");
        buttonArchive.getStyleClass().add("emailDetailsButton");
        buttonArchive.setPrefWidth(84);
        buttonArchive.setPrefHeight(34);
        buttonArchive.setOnAction(event -> {
            sql.archiveTask(taskOld);
            CommonTasks.loadInPane(ActivityDashController.main_paneF, "activity/view/activity_view.fxml");
        });
        hbox_tools.getChildren().add(buttonArchive);

//        new NotesConstructor(notes_list, sql, lead).generalConstructor(3);
//        new TasksConstructor(open_activities_list, closed_activities_list, lead).generalConstructor(3);
    }
}

