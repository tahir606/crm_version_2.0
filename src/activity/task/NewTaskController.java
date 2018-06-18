package activity.task;

import JCode.Toast;
import JCode.mysql.mySqlConn;
import client.dash.clientView.clientViewController;
import client.dash.contactView.contactViewController;
import com.jfoenix.controls.*;
import gui.ActivitiesConstructor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import objects.ClientProperty;
import objects.ContactProperty;
import objects.Task;

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
    
    //Which property is selected
    private int choice;
    private mySqlConn sql;
    
    public static char stInstance;
    
    private ClientProperty client;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        sql = new mySqlConn();
        
        relation_type.getItems().addAll("Contact", "Client", "Lead", "Product");
        
        choice = ActivitiesConstructor.choice;
        
        switch (choice) {
            case 1: {       //Contacts
                ContactProperty contact = contactViewController.staticContact;
                relation_type.getSelectionModel().select("Contact");
                txt_name.setText(contact.getFullName());
                break;
            }
            case 2: {       //Clients
                client = clientViewController.staticClient;
                relation_type.getSelectionModel().select("Client");
                txt_name.setText(client.getName());
                break;
            }
        }
        
        btn_save.setOnAction(event -> {
            String subject = txt_subject.getText().toString(),
                    desc = txt_desc.getText().toString(),
                    dueDate = due_date.getValue().toString(),
                    type = relation_type.getSelectionModel().getSelectedItem(),
                    name = txt_name.getText().toString();
            boolean repeat = check_repeat.isSelected();
            
            Task task = new Task();
            
            if (subject.equals("") || desc.equals("") || dueDate.equals("")) {
                Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
                return;
            } else {
                String msg = "";
                switch (stInstance) {
                    case 'N': {
                        msg = "Are you sure you want to add Task?";
                        break;
                    }
                    case 'U': {
                        msg = "Are you sure you want to update Task?";
                        break;
                    }
                    default: {
                        break;
                    }
                }
                Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, msg,
                        ButtonType.YES, ButtonType.NO);
                alert2.showAndWait();
                
                if (alert2.getResult() == ButtonType.YES) {
                    
                    switch (stInstance) {
                        case 'N': {
                            task.setSubject(subject);
                            task.setDueDate(dueDate);
                            task.setDesc(desc);
                            task.setRepeat(repeat);
                            
                            if (type.equals("Contact")) {
                            
                            } else if (type.equals("Client")) {
                                task.setClient(client.getCode());
                            }
                            
                            sql.addTask(task);
                            
                            break;
                        }
                        case 'U': {
//                            sql.updateTask(task);
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                    
                } else {
                    return;
                }
                closeStage();
            }
        });
        
        btn_cancel.setOnAction(event -> closeStage());
    }
    
    private void closeStage() {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        
        switch (choice) {
            case 1: {
                break;
            }
            case 2: {
                ActivitiesConstructor.generalConstructor(2,this.getClass().getResource
                        ("new_task.fxml"), getClass().getResourceAsStream("/res/img/options.png"));
                break;
            }
        }
    }
}
