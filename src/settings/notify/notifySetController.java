package settings.notify;

import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import objects.NotificationSettings;

import java.net.URL;
import java.util.ResourceBundle;

public class notifySetController implements Initializable {

    @FXML
    private JFXCheckBox check_notify_dueDate, check_notify_from;
    @FXML
    private JFXButton btn_save;

    mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();

        populateDetails();

        btn_save.setOnAction(event -> {
            boolean task_DueDate = check_notify_dueDate.isSelected(),
                    event_fromDate = check_notify_from.isSelected();

            NotificationSettings nSettings = new NotificationSettings();
            nSettings.setNotifyTaskDueDate(task_DueDate);
            nSettings.setNotifyEventFromDate(event_fromDate);

            sql.insertNotificationSettings(nSettings);

            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Notification Settings Updated");
        });
    }

    private void populateDetails() {
        NotificationSettings settings = sql.getNotificationSettings();
        if (settings == null)
            return;
        check_notify_dueDate.setSelected(settings.isNotifyTaskDueDate());
        check_notify_from.setSelected(settings.isNotifyEventFromDate());
    }

}
