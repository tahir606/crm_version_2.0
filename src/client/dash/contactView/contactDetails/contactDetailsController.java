package client.dash.contactView.contactDetails;

import Email.EResponse.EResponseController;
import JCode.CommonTasks;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import client.dash.contactView.contactViewController;
import client.dashBaseController;
import client.newContact.newContactController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.ContactProperty;
import objects.Note;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class contactDetailsController implements Initializable {
    
    @FXML
    private Label txt_fname;
    @FXML
    private Label txt_email;
    @FXML
    private Label txt_mobile;
    @FXML
    private Label txt_client;
    @FXML
    private Label txt_dob;
    @FXML
    private Label txt_age;
    @FXML
    private JFXButton btn_back;
    @FXML
    private JFXButton btn_email;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private VBox notes_list;
    
    private mySqlConn sql;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        sql = new mySqlConn();
        
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Contacts"));
        btn_back.setOnAction(event -> {
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/dash/contactView/contactView.fxml")));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        ContactProperty contact = contactViewController.staticContact;
        populateDetails(contact);
    }
    
    TextArea noteTxt;
    JFXButton btnAdd;
    
    private void populateDetails(ContactProperty contact) {
        txt_fname.setText(contact.getFullName());
        txt_email.setText(contact.getEmail());
        txt_mobile.setText(contact.getMobile());
        txt_client.setText(contact.getClientName());
        txt_dob.setText(CommonTasks.getDateFormatted(contact.getDob()));
        txt_age.setText(String.valueOf(contact.getAge()));
    
        btn_email.setOnAction(event -> {
            EResponseController.stTo = contact.getEmail();
            EResponseController.stInstance = 'N';
            inflateEResponse(1);
        });
    
        btn_edit.setOnAction(event -> {
            newContactController.stInstance = 'U';
            try {
                dashBaseController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("client/newContact/newContact.fxml")));
            
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        constructingNotes(contact);
    }
    
    private void constructingNotes(ContactProperty contact) {
        notes_list.getChildren().clear();
        //Constructing Notes
        for (Note note : contact.getContactNotes()) {
            //The First Part
            HBox body = new HBox();
            body.setSpacing(5);
            //Note Text
            TextArea area = new TextArea(note.getText());
            area.setWrapText(true);
            area.setEditable(false);
            area.setMinHeight(50);
            area.setStyle("-fx-background-color: #fcfcfc;" +
                    "-fx-background: transparent;");
            body.getChildren().add(area);
            //The Second Part
            HBox details = new HBox();
            details.setSpacing(10);
            details.setMinHeight(25);
            details.setPadding(new Insets(3));
            Label createdBy = new Label(note.getCreatedByName()),
                    createdOn = new Label(note.getCreatedOn());
            createdOn.setMinWidth(150);
            createdBy.setMinWidth(280);
            JFXButton options = new JFXButton();
            Image image = new Image(getClass().getResourceAsStream("/res/img/options.png"));
            options.setGraphic(new ImageView(image));
            details.getChildren().addAll(createdOn, createdBy, options);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem delItem = new MenuItem("Delete"),
                    editItem = new MenuItem("Edit");
            editItem.setOnAction(t -> {
                area.setEditable(true);
                area.setStyle("-fx-background: #fcfcfc;");
                area.requestFocus();
                JFXButton saveBtn = new JFXButton("Save"),
                        cancelBtn = new JFXButton("Cancel");
                saveBtn.setMinWidth(60);
                saveBtn.setButtonType(JFXButton.ButtonType.RAISED);
                saveBtn.setStyle("-fx-background-color: #e8f0ff;");
                cancelBtn.setMinWidth(60);
                cancelBtn.setButtonType(JFXButton.ButtonType.RAISED);
                cancelBtn.setStyle("-fx-background-color: #e8f0ff;");
                
                saveBtn.setOnAction(event -> {
                    note.setText(area.getText().toString());
                    sql.updateContactNote(note, contact);
                    populateDetails(sql.getParticularContact(contact));
                });
                cancelBtn.setOnAction(event -> {
                    body.getChildren().remove(saveBtn);
                    body.getChildren().remove(cancelBtn);
                    area.setEditable(false);
                    area.setStyle("-fx-background-color: #fcfcfc;" +
                            "-fx-background: transparent;");
                });
                
                body.getChildren().addAll(saveBtn, cancelBtn);
            });
            delItem.setOnAction(t -> {
                sql.deleteContactNote(note, contact);
                populateDetails(sql.getParticularContact(contact));
            });
            contextMenu.getItems().addAll(editItem, delItem);
            options.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
                if (me.getButton() == MouseButton.PRIMARY) {
                    contextMenu.show(options, me.getScreenX(), me.getScreenY());
                } else {
                    contextMenu.hide();
                }
            });
            
            VBox box = new VBox();
//            box.setStyle("-fx-border-color: #033300;");
            box.getChildren().addAll(body, details);
            notes_list.setSpacing(10);
            notes_list.getChildren().add(0, box);
        }
        
        HBox addNew = new HBox();
        addNew.setSpacing(5);
        noteTxt = new TextArea();
        noteTxt.setPromptText("New Note");
        btnAdd = new JFXButton();
        btnAdd.setText("Add");
        btnAdd.setButtonType(JFXButton.ButtonType.RAISED);
        btnAdd.setStyle("-fx-background-color: #efefef;");
        addNew.getChildren().addAll(noteTxt, btnAdd);
        notes_list.getChildren().add(addNew);
        
        btnAdd.setOnAction(event -> {
            String note = noteTxt.getText().toString();
            if (note.trim().equals("")) {
                Toast.makeText((Stage) noteTxt.getScene().getWindow(), "Note cannot be empty");
                return;
            } else {
                sql.addContactNote(note, contact);
                populateDetails(sql.getParticularContact(contact));
            }
        });
    }
    
    private void inflateEResponse(int i) {
        try {
            EResponseController.choice = i;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../../../../Email/EResponse/EResponse.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("New Email");
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
