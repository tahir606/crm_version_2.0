package JCode;

import Email.EResponse.EResponseController;
import JCode.mysql.mySqlConn;
import client.dashBaseController;
import client.newContact.newContactController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
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

public class GUIConstructor {
    
    TextArea noteTxt;
    JFXButton btnAdd;
    
    VBox notes_list;
    mySqlConn sql;
    
    public GUIConstructor(VBox notes_list, mySqlConn sql) {
        this.notes_list = notes_list;
        this.sql = sql;
    }
    
    public void constructingNotes(ContactProperty c) {
        ContactProperty contact = sql.getParticularContact(c);
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
//                    populateDetails(sql.getParticularContact(contact));
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
//                populateDetails(sql.getParticularContact(contact));
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
//                populateDetails(sql.getParticularContact(contact));
            }
        });
    }
    
    private void populateDetails(int choice) {
        switch (choice) {
            case 1: //Contact
                break;
        }
    }
}
