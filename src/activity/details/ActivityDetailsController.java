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
    private JFXButton btn_close;
    @FXML
    private Label txt_type;
    @FXML
    private Label txt_name;
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
    
        txt_type.setVisible(true);
        txt_name.setVisible(true);
        
        if (task.getClient() != 0 ) {
            txt_type.setText("Client");
            txt_name.setText(task.getClientName());
        } else if (task.getLead() != 0 ) {
            txt_type.setText("Lead");
            txt_name.setText(task.getLeadName());
        } else if (task.getProduct() != 0 ) {
            txt_type.setText("Product");
            txt_name.setText(task.getProductName());
        } else {
            txt_type.setVisible(false);
            txt_name.setVisible(false);
        }
        
        if (!task.isStatus())
            btn_close.setDisable(false);
        else
            btn_close.setDisable(true);

        btn_close.setOnAction(event -> {
            sql.closeTask(task);
            btn_close.setDisable(true);
        });

//        new NotesConstructor(notes_list, sql, lead).generalConstructor(3);
//        new ActivitiesConstructor(open_activities_list, closed_activities_list, lead).generalConstructor(3);
    }
}

