package activity.details;

import ApiHandler.RequestHandler;
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
import objects.Client;
import objects.Task;

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

    private mySqlConn sql;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
       txt_entryDate.setText(task.getEntryDate() == null ? " - " : task.getEntryDate());
        txt_dueDate.setText(task.getDueDate());
        txt_createdOn.setText(task.getCreatedOn());
        txt_createdBy.setText(task.getUsers().getFullName());
        txt_desc.setText(task.getDescription());

        txt_type.setVisible(true);
        txt_name.setVisible(true);

        if (task.getClientID() != 0) {
            txt_type.setText("Client");
            try {
                Client client = (Client) RequestHandler.objectRequestHandler(RequestHandler.run("client/" + task.getClientID()), Client.class);
                if (client != null) {

                    txt_name.setText(client.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (task.getLeadsId() != 0) {
            txt_type.setText("Lead");
            txt_name.setText("");
        } else if (task.getPsID() != 0) {
            txt_type.setText("Product");
            txt_name.setText("");
        } else {
            txt_type.setVisible(false);
            txt_name.setVisible(false);
        }

        if (task.getStatus()==0)
            btn_close.setDisable(false);
        else
            btn_close.setDisable(true);

        btn_close.setOnAction(event -> {
            try {
                RequestHandler.run("task/closeTask/"+task.getTaskID());
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            try {
                RequestHandler.run("task/archiveTask/"+task.getTaskID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            CommonTasks.loadInPane(ActivityDashController.main_paneF, "activity/view/activity_view.fxml");
        });
        hbox_tools.getChildren().add(buttonArchive);


    }
}

