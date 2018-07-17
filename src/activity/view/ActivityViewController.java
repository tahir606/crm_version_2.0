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
    private Label txt_no;

    private mySqlConn sql = new mySqlConn();

    public static Task staticTask;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        table_contact.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        col_due_date.setCellValueFactory(new PropertyValueFactory<>("dueDateFormatted"));
        col_created_on.setCellValueFactory(new PropertyValueFactory<>("createdOn"));
        col_created_by.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("statusString"));

        table_activity.getItems().setAll(sql.getAllTasks(null));

//        table_contact.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
//            selectedContacts = table_contact.getSelectionModel().getSelectedItems();
//
//            if (selectedContacts == null || selectedContacts.size() == 0) {
//                toolbar_contacts.setVisible(false);
//                return;
//            } else {
//                toolbar_contacts.setVisible(true);
//                txt_no.setText(String.valueOf(selectedContacts.size()));
//            }
//
//        });

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

    }
}
