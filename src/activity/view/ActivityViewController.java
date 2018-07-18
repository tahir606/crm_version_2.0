package activity.view;

import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import objects.Event;
import objects.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActivityViewController implements Initializable {

    @FXML
    private TableView<Task> table_activity;
    @FXML
    private TableColumn<Task, String> col_subject;
    @FXML
    private TableColumn<Task, String> col_due_date;
    @FXML
    private TableColumn<Task, String> col_created_on;
    @FXML
    private TableColumn<Task, String> col_created_by;
    @FXML
    private TableColumn<Task, String> col_status;
    @FXML
    private TableView<Event> table_events;
    @FXML
    private TableColumn<Event, String> col_title;
    @FXML
    private TableColumn<Event, String> col_location;
    @FXML
    private TableColumn<Event, String> col_from;
    @FXML
    private TableColumn<Event, String> col_to;
    @FXML
    private TableColumn<Event, String> col_created_by_event;
    @FXML
    private TableColumn<Event, String> col_status_event;

    private mySqlConn sql = new mySqlConn();

    public static Task staticTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("dueDateFormatted"));
        col_created_on.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
        col_created_by.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("statusString"));

        table_activity.getItems().setAll(sql.getAllTasks(null));

        table_activity.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    staticTask = row.getItem();
                    try {
                        ActivityDashController.main_paneF.setCenter(
                                FXMLLoader.load(
                                        getClass().getClassLoader().getResource
                                                ("activity/details/activity_details.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        col_from.setCellValueFactory(new PropertyValueFactory<>("fromDate"));
        col_to.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
        col_created_by.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("statusString"));
    }
}
