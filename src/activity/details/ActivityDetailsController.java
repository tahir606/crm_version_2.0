package activity.details;

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
import objects.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityDetailsController implements Initializable {

    @FXML
    private Label txt_fname;
    @FXML
    private Label txt_company;
    @FXML
    private Label txt_website;
    @FXML
    private Label txt_email;
    @FXML
    private Label txt_mobile;
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
        txt_fname.setText(lead.getFullNameProperty());
        txt_company.setText(lead.getCompany());
        txt_website.setText(lead.getWebsite());
        txt_email.setText(lead.getEmail());
        txt_mobile.setText(lead.getPhone());
        txt_desc.setText(lead.getNote());

//        btn_email.setOnAction(event -> {
//            EResponseController.stTo = contact.getEmail();
//            EResponseController.stInstance = 'N';
//            inflateEResponse(1);
//        });

        btn_edit.setOnAction(event -> {
            NewTaskController.stInstance = 'U';
            try {
                ActivityDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("lead/newLead/new_lead.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        new NotesConstructor(notes_list, sql, lead).generalConstructor(3);
//        new ActivitiesConstructor(open_activities_list, closed_activities_list, lead).generalConstructor(3);
    }
}
}
