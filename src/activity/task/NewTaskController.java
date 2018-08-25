package activity.task;

import JCode.CommonTasks;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.view.ActivityViewController;
import client.dash.clientView.clientViewController;
import client.dash.contactView.contactViewController;
import com.jfoenix.controls.*;
import gui.EventsConstructor;
import gui.TasksConstructor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lead.view.LeadViewController;
import objects.*;
import product.view.ProductViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewTaskController implements Initializable {
    
    @FXML
    private JFXTextField txt_subject, txt_name;
    @FXML
    private JFXDatePicker due_date, entry_date;
    @FXML
    private JFXCheckBox check_repeat;
    @FXML
    private TextArea txt_desc;
    @FXML
    private HBox combo_type;
    @FXML
    private JFXComboBox<String> relation_type;
    @FXML
    private JFXButton btn_save, btn_cancel;
    
    //Which property is selected
    private int choice;
    
    private mySqlConn sql;
    
    public static char stInstance;
    
    private ClientProperty client;
    private Lead lead;
    private ProductProperty product;
    
    private Task task;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        sql = new mySqlConn();
        
        relation_type.getItems().addAll("Contact", "Client", "Lead", "Product");
        
        choice = EventsConstructor.choice;
        
        relation_type.setDisable(true);
        txt_name.setDisable(true);
        
        switch (stInstance) {
            case 'N': {
                btn_save.setText("Add");
                
                task = new Task();
                break;
            }
            case 'U': {
                btn_save.setText("Update");
                
                task = TasksConstructor.updatingTask;
                populateFields(task);
                break;
            }
            case 'D': { //D for from details
                btn_save.setText("Update");
                
                task = ActivityViewController.staticTask;
                populateFields(task);
                break;
            }
        }
        
        if (stInstance != 'D') {
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
                case 3: {
                    lead = LeadViewController.staticLead;
                    relation_type.getSelectionModel().select("Lead");
                    txt_name.setText(lead.getFullNameProperty().toString());
                    break;
                }
                case 4: {
                    product = ProductViewController.staticProduct;
                    relation_type.getSelectionModel().select("Product");
                    txt_name.setText(product.getName().toString());
                    break;
                }
            }
        } else {
            if (task.getClient() != 0) {
                relation_type.getSelectionModel().select("Client");
                txt_name.setText(task.getClientName());
            } else if (task.getLead() != 0) {
                relation_type.getSelectionModel().select("Lead");
                txt_name.setText(task.getLeadName());
            } else if (task.getProduct() != 0) {
                relation_type.getSelectionModel().select("Product");
                txt_name.setText(task.getProductName());
            }
        }
        
        btn_save.setOnAction(event -> {
            String subject = txt_subject.getText().toString(),
                    desc = txt_desc.getText().toString(),
                    entryDate = entry_date.getValue().toString(),
                    dueDate = due_date.getValue().toString(),
                    type = relation_type.getSelectionModel().getSelectedItem(),
                    name = txt_name.getText().toString();
            boolean repeat = check_repeat.isSelected();
            
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
                    
                    task.setSubject(subject);
                    task.setEntryDate(entryDate);
                    task.setDueDate(dueDate);
                    task.setDesc(desc);
                    task.setRepeat(repeat);
                    
                    switch (stInstance) {
                        case 'N': {
                            try {
                                if (type.equals("Contact")) {
                                
                                } else if (type.equals("Client")) {
                                    task.setClient(client.getCode());
                                } else if (type.equals("Lead")) {
                                    task.setLead(lead.getCode());
                                } else if (type.equals("Product")) {
                                    task.setProduct(product.getCode());
                                }
                            } catch (NullPointerException e) {
                                System.out.println(e);
                            }
                            sql.addTask(task);
                            break;
                        }
                        case 'U': {
                            sql.updateTask(task);
                            break;
                        }
                        case 'D': {
                            sql.updateTask(task);
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
        if (stInstance != 'D')
            try {
                TasksConstructor.generalConstructor(choice);
            } catch (NullPointerException e) {
                try {
                    ActivityDashController.main_paneF.setCenter(
                            FXMLLoader.load(
                                    getClass().getClassLoader().getResource("activity/view/activity_view.fxml")));
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        else {
            try {
                ActivityDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("activity/view/activity_view.fxml")));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void populateFields(Task task) {
        txt_subject.setText(task.getSubject());
        txt_desc.setText(task.getDesc());
        if (task.getEntryDate() != null)
            entry_date.setValue(CommonTasks.createLocalDate(task.getEntryDate()));
        else
            entry_date.setValue(null);
        
        if (task.getDueDate() != null)
            due_date.setValue(CommonTasks.createLocalDate(task.getDueDate()));
        else
            due_date.setValue(null);
        check_repeat.setSelected(task.isRepeat());
    }
}
