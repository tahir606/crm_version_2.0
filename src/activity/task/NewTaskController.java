package activity.task;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTaskController implements Initializable {
    
    @FXML
    private JFXTextField txt_subject;
    @FXML
    private JFXDatePicker due_date;
    @FXML
    private JFXCheckBox check_repeat;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
        btn_save.setOnAction(event -> {
        
        });
        
        btn_cancel.setOnAction(event -> {
        
        });
    }
}
