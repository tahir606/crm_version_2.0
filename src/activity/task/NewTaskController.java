package activity.task;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import activity.ActivityDashController;
import activity.view.ActivityViewController;
import client.dash.clientView.clientViewController;
import client.dash.contactView.contactViewController;
import com.jfoenix.controls.*;
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

    private int choice;

    private mySqlConn sql;

    public static char stInstance;
    Contact contact;
    private Client client;
    private Lead lead;
    private Product product;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        task = new Task();
        relation_type.getItems().addAll("Contact", "Client", "Lead", "Product");

//        choice = EventsConstructor.choice;
        choice = TasksConstructor.choice;
        relation_type.setDisable(true);
        txt_name.setDisable(true);

        switch (stInstance) {
            case 'N': {
                btn_save.setText("Add");
                break;
            }
            case 'U': {
                btn_save.setText("Update");
                populateFields(TasksConstructor.updatingTask);
                break;
            }
            case 'D': { //D for from details
                btn_save.setText("Update");
                populateFields(ActivityViewController.staticTask);
                break;
            }
        }

        if (stInstance != 'D') {
            switch (choice) {
                case 1: {       //Contacts
                    contact = contactViewController.staticContact;
                    relation_type.getSelectionModel().select("Contact");
                   txt_name.setText(contact.getFirstName());
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
                    txt_name.setText(lead.getFullName().toString());

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
            if (task.getClientID() != 0) {
                relation_type.getSelectionModel().select("Client");
                try {
                    Client client = (Client) RequestHandler.objectRequestHandler(RequestHandler.run("client/" + task.getClientID()), Client.class);
                    if (client != null) {

                        txt_name.setText(client.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (task.getLeadsId() != 0) {
                relation_type.getSelectionModel().select("Lead");
                try {
                    Lead lead = (Lead) RequestHandler.objectRequestHandler(RequestHandler.run("leads/" + task.getLeadsId()), Lead.class);
                    if (client != null) {

                        txt_name.setText(lead.getFullName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                txt_name.setText(task.getLeadName());
            } else if (task.getPsID() != 0) {
                relation_type.getSelectionModel().select("Product");
                try {
                    Product product = (Product) RequestHandler.objectRequestHandler(RequestHandler.run("product/" + task.getPsID()), Product.class);
                    if (client != null) {

                        txt_name.setText(product.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                txt_name.setText(task.getProductName());
            }
        }

        btn_save.setOnAction(event -> {

            String subject = txt_subject.getText().toString(),
                    desc = txt_desc.getText().toString(),
                    entryDate = null,
                    dueDate = null,
                    type = relation_type.getSelectionModel().getSelectedItem(),
                    name = txt_name.getText().toString();
            boolean repeat = check_repeat.isSelected();
            if (due_date.getValue() != null) {
                dueDate = due_date.getValue().toString();
            }
            if (entry_date.getValue() != null) {
                entryDate = entry_date.getValue().toString();
            }
            if (subject.equals("") || desc.equals("") || dueDate == null || entryDate == null) {
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
                    task.setDescription(desc);
                    task.setCreatedOn(CommonTasks.getCurrentTimeStamp());
                    task.setCreatedBy(FileHelper.ReadUserApiDetails().getUserCode());
                    if (repeat) {
                        task.setRepeat(1);
                    } else {
                        task.setRepeat(0);
                    }


                    switch (stInstance) {
                        case 'N': {
                            try {
                                if (type.equals("Contact")) {
                                   task.setContactID(contact.getContactID());
                                } else if (type.equals("Client")) {
                                    task.setClientID(client.getClientID());
                                } else if (type.equals("Lead")) {
                                    task.setLeadsId(lead.getLeadsId());
                                } else if (type.equals("Product")) {
                                   task.setPsID(product.getPsID());
                                }
                            } catch (NullPointerException e) {
                                System.out.println(e);
                            }

                            String responseMessage = "";
                            try {
                                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("task/addTask", RequestHandler.writeJSON(task)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText((Stage) btn_save.getScene().getWindow(), responseMessage);

                            break;
                        }
                        case 'U':
                        case 'D': {
                            String responseMessage = "";
                            try {
                                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("task/addTask", RequestHandler.writeJSON(task)));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText((Stage) btn_save.getScene().getWindow(), responseMessage);
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

    private void populateFields(Task task1) {
        task.setTaskID(task1.getTaskID());
        task.setClientID(task1.getClientID());
        task.setContactID(task1.getContactID());
        txt_subject.setText(task1.getSubject());
        txt_desc.setText(task1.getDescription());
        if (task1.getEntryDate() != null)
            entry_date.setValue(CommonTasks.createLocalDateForEventTask(task1.getEntryDate()));
        else
            entry_date.setValue(null);

        if (task1.getDueDate() != null)
            due_date.setValue(CommonTasks.createLocalDateForEventTask(task1.getDueDate()));
        else
            due_date.setValue(null);
        if (task1.getRepeat() == 0) {
            check_repeat.setSelected(false);
        } else {
            check_repeat.setSelected(true);
        }

    }
}
