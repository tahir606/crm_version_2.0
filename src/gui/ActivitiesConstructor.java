package gui;

import JCode.CommonTasks;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import objects.ClientProperty;

public class ActivitiesConstructor {
    
    VBox open_activities_list;
    ClientProperty client;
    
    public ActivitiesConstructor(VBox open_activities_list, ClientProperty client) {
        this.open_activities_list = open_activities_list;
        this.client = client;
    }
    
    private void constructClientActivities() {
        constructingButtons();
    }
    
    private void constructingButtons() {
        HBox box = new HBox();
        JFXButton newTask = new JFXButton("+ New Task"),
                newEvent = new JFXButton("+ New Event");
        
        String css = "-fx-text-fill: #011b44;";
        
        newTask.setStyle(css);
        newEvent.setStyle(css);
        
        newTask.setOnAction(event -> {
            CommonTasks.inflateDialog("New Task", this.getClass().getResource("../activity/task/new_task.fxml"));
        });
        
        newEvent.setOnAction(event -> {
        
        });
        
        box.getChildren().addAll(newTask, newEvent);
        
        String labelCss = "-fx-font-weight: bold;";
        
        Label label = new Label("Open Activities");
        label.setStyle(labelCss);
        
        open_activities_list.getChildren().addAll(box, label);
    }
    
    public void generalConstructor(int choice) {
        switch (choice) {
            case 2:     //Clients
                constructClientActivities();
                break;
        }
    }
}
