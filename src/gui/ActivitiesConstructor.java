package gui;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import activity.task.NewTaskController;
import client.dash.clientView.clientViewController;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.text.Font;
import objects.ClientProperty;
import objects.Task;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ActivitiesConstructor {
    
    private static VBox open_activities_list;
    private ClientProperty client;
    private static mySqlConn sql;
    
    //Which property is selected
    public static int choice;
    
    public ActivitiesConstructor(VBox open_activities_list, ClientProperty client) {
        this.open_activities_list = open_activities_list;
        this.client = client;
        
        sql = new mySqlConn();
    }
    
    private static void constructClientActivities(URL url, InputStream imageUrl) {
        constructingButtons(url);
        
        //Heading
        String labelCss = "-fx-font-weight: bold;";
        Label label = new Label("Open Activities");
        label.setStyle(labelCss);
        open_activities_list.getChildren().addAll(label);
        
        List<Task> tasks = sql.getTasks(clientViewController.staticClient);
        for (Task task : tasks) {
            constructingTask(task, url, imageUrl);
        }
    }
    
    private static void constructingButtons(URL url) {
        HBox box = new HBox();
        JFXButton newTask = new JFXButton("+ New Task"),
                newEvent = new JFXButton("+ New Event");
        
        String css = "-fx-text-fill: #011b44;";
        
        newTask.setStyle(css);
        newEvent.setStyle(css);
        
        newTask.setOnAction(event -> {
            NewTaskController.stInstance = 'N';
            CommonTasks.inflateDialog("New Task", url);
        });
        
        newEvent.setOnAction(event -> {
        
        });
        
        box.getChildren().addAll(newTask, newEvent);
        
        open_activities_list.getChildren().addAll(box);
    }
    
    private static void constructingTask(Task task, URL url, InputStream imagePath) {
        //The Subject
        HBox subject = new HBox();
        subject.setSpacing(5);
        //Note Text
        Label title = new Label("Subject: ");
        title.setStyle("-fx-font-weight: bold;");
        Label sbjct = new Label(task.getSubject());
        sbjct.setWrapText(true);
        subject.getChildren().addAll(title, sbjct);
        //The First Part
        HBox body = new HBox();
        body.setSpacing(5);
        //Note Text
        TextArea area = new TextArea(task.getDesc());
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
        Label createdBy = new Label(task.getCreatedBy()),
                createdOn = new Label(task.getCreatedOn());
        createdOn.setMinWidth(150);
        createdBy.setMinWidth(280);
        JFXButton options = new JFXButton();
        
        Image image = new Image(imagePath);
        options.setGraphic(new ImageView(image));
        details.getChildren().addAll(createdOn, createdBy, options);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeItem = new MenuItem("Close"),
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
                task.setDesc(area.getText().toString());
                switch (choice) {
                    case 1:
//                        sql.updateTask(note, contact);
                        break;
                    case 2:
//                        sql.updateNote(note, client);
                        break;
                    case 3:
//                        sql.updateNote(note, lead);
                        break;
                    case 4:
//                        sql.updateNote(note, product);
                        break;
                }
                generalConstructor(choice, url, imagePath);
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
        closeItem.setOnAction(t -> {
            switch (choice) {
                case 1:
//                    sql.closeTask(note, contact);
                    break;
                case 2:
//                    sql.deleteNote(note, client);
                    break;
                case 3:
//                    sql.deleteNote(note, lead);
                    break;
                case 4:
//                    sql.deleteNote(note, product);
                    break;
            }
            generalConstructor(choice, url, imagePath);
        });
        contextMenu.getItems().addAll(editItem, closeItem);
        options.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(options, me.getScreenX(), me.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
        
        VBox box = new VBox();
//            box.setStyle("-fx-border-color: #033300;");
        box.getChildren().addAll(subject, body, details);
        open_activities_list.setSpacing(10);
        open_activities_list.getChildren().add(box);
    }
    
    public static void generalConstructor(int choice, URL url, InputStream imagePath) {
        switch (choice) {
            case 2: {     //Clients
                ActivitiesConstructor.choice = choice;
                constructClientActivities(url, imagePath);
                break;
            }
        }
    }
}
