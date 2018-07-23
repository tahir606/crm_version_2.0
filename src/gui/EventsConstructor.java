package gui;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import activity.event.NewEventController;
import client.dash.clientView.clientViewController;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import lead.view.LeadViewController;
import objects.ClientProperty;
import objects.Event;
import objects.Lead;

import java.io.IOException;
import java.util.List;

public class EventsConstructor {

    private static TabPane tabPane;
    private static Tab tab;
    private static VBox open_events_list, closed_events_list;

    private ClientProperty client;
    private Lead lead;

    private static mySqlConn sql;

    //Which property is selected
    public static int choice;
    public static Event updatingEvent;


    public EventsConstructor(TabPane tabPane, ClientProperty client) {
        this.tabPane = tabPane;
        this.tab = new Tab("Events");
        this.open_events_list = new VBox();
        this.closed_events_list = new VBox();
        this.client = client;

        sql = new mySqlConn();
    }

    public EventsConstructor(TabPane tabPane, Lead lead) {
        this.tabPane = tabPane;
        this.tab = new Tab("Events");
        this.open_events_list = new VBox();
        this.closed_events_list = new VBox();
        this.lead = lead;

        sql = new mySqlConn();
    }

    private static void constructClientActivities() {
        constructingButtons();

        //Heading
        String labelCss = "-fx-font-weight: bold;";
        Label label = new Label("Open Events");
        label.setStyle(labelCss);
        open_events_list.getChildren().addAll(returnSpaceHbox(), label, returnSpaceHbox());

        Label label2 = new Label("Closed Events");
        label2.setStyle(labelCss);
        closed_events_list.getChildren().addAll(returnSpaceHbox(), label2, returnSpaceHbox());

        List<Event> events = sql.getEvents(clientViewController.staticClient);
        for (Event event : events) {
            if (!event.isStatus())
                constructingOpenEvent(event);
            else
                constructingCloseEvent(event);
        }
    }

    private static void constructLeadActivities() {
        constructingButtons();

        //Heading
        String labelCss = "-fx-font-weight: bold;";
        Label label = new Label("Open Activities");
        label.setStyle(labelCss);
        open_events_list.getChildren().addAll(label);

        Label label2 = new Label("Closed Activities");
        label2.setStyle(labelCss);
        closed_events_list.getChildren().addAll(label2);

        List<Event> events = sql.getEvents(LeadViewController.staticLead);
        for (Event event : events) {
            if (!event.isStatus())
                constructingOpenEvent(event);
            else
                constructingCloseEvent(event);
        }
    }

    private static void constructingButtons() {
        HBox box = new HBox();
        JFXButton newEvent = new JFXButton("+ New Event");

        String css = "-fx-text-fill: #005ff7;";

        newEvent.setStyle(css);
        newEvent.setOnAction(event -> {
            NewEventController.stInstance = 'N';
            CommonTasks.inflateDialog("New Event", "/activity/event/new_event.fxml");
        });
        box.getChildren().addAll(newEvent);

        open_events_list.getChildren().addAll(box);
    }

    private static void constructingOpenEvent(Event event) {
        //The Subject
        HBox subject = new HBox();
        subject.setSpacing(5);
        //Note Text
        Label title = new Label("Title: ");
        title.setStyle("-fx-font-weight: bold;");
        Label titleText = new Label(event.getTitle());
        titleText.setWrapText(true);
        subject.getChildren().addAll(title, titleText);
        //The First Part
        HBox body = new HBox();
        body.setSpacing(5);
        //Note Text
        TextArea area = new TextArea(event.getDesc());
        area.setWrapText(true);
        area.setEditable(false);
        area.setMinHeight(50);
        area.setMaxHeight(50);
        area.setStyle("-fx-background-color: #fcfcfc;" +
                "-fx-background: transparent;");
        body.getChildren().add(area);
        //The Second Part
        HBox details = new HBox();
        details.setSpacing(10);
        details.setMinHeight(25);
        details.setMaxHeight(25);
        details.setPadding(new Insets(3));
        Label createdBy = new Label(event.getCreatedBy()),
                timeSchedule = new Label(CommonTasks.getTimeFormatted(event.getFromDate() + " " + event.getFromTime()) + " -> " + CommonTasks.getTimeFormatted(event.getToDate() + " " + event.getToTime()));
        timeSchedule.setMinWidth(150);
        createdBy.setMinWidth(280);
        JFXButton options = new JFXButton();

        Image image = new Image(EventsConstructor.class.getResourceAsStream("/res/img/options.png"));
        options.setGraphic(new ImageView(image));
        details.getChildren().addAll(timeSchedule, createdBy, options);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeItem = new MenuItem("Close"),
                editItem = new MenuItem("Edit"),
                delItem = new MenuItem("Delete");
        editItem.setOnAction(t -> {
            NewEventController.stInstance = 'U';
            updatingEvent = event;
            CommonTasks.inflateDialog("Update Event", "/activity/event/new_event.fxml");
        });
        closeItem.setOnAction(t -> {
            sql.closeEvent(event);
            generalConstructor(choice);
        });
        delItem.setOnAction(t -> {
            sql.archiveEvent(event);
            generalConstructor(choice);
        });
        contextMenu.getItems().addAll(editItem, closeItem, delItem);
        options.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(options, me.getScreenX(), me.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

        VBox box = new VBox();
        box.getChildren().addAll(subject, body, details);
        open_events_list.getChildren().add(box);
    }

    private static void constructingCloseEvent(Event event) {
        //The Subject
        HBox subject = new HBox();
        subject.setSpacing(5);
        //Note Text
        Label title = new Label("Title: ");
        title.setStyle("-fx-font-weight: bold;");
        Label sbjct = new Label(event.getTitle());
        sbjct.setWrapText(true);
        subject.getChildren().addAll(title, sbjct);
        //The First Part
        HBox body = new HBox();
        body.setSpacing(5);
        //Note Text
        TextArea area = new TextArea(event.getDesc());
        area.setWrapText(true);
        area.setEditable(false);
        area.setMinHeight(50);
        area.setMaxHeight(50);
        area.setStyle("-fx-background-color: #fcfcfc;" +
                "-fx-background: transparent;");
        body.getChildren().add(area);
        //The Second Part
        HBox details = new HBox();
        details.setSpacing(10);
        details.setMinHeight(25);
        details.setMaxHeight(25);
        details.setPadding(new Insets(3));
        Label createdBy = new Label(event.getCreatedBy()),
                createdOn = new Label(CommonTasks.getTimeFormatted(event.getFromDate() + " " + event.getFromTime()) + " -> " + CommonTasks.getTimeFormatted(event.getToDate() + " " + event.getToTime()));
        createdOn.setMinWidth(150);
        createdBy.setMinWidth(280);

        details.getChildren().addAll(createdOn, createdBy);
        ContextMenu contextMenu = new ContextMenu();

        VBox box = new VBox();
//            box.setStyle("-fx-border-color: #033300;");
        box.getChildren().addAll(subject, body, details);
        closed_events_list.getChildren().add(box);
    }

    public static void generalConstructor(int choice) throws NullPointerException {

        open_events_list.getChildren().clear();
        closed_events_list.getChildren().clear();

        EventsConstructor.choice = choice;
        switch (choice) {
            case 2: {     //Clients
                constructClientActivities();
                break;
            }
            case 3: {
                constructLeadActivities();
                break;
            }
        }

        VBox vBox = new VBox();
        vBox.getChildren().addAll(returnSpaceHbox(), open_events_list, returnSpaceHbox(), new Separator(), returnSpaceHbox(), closed_events_list);
        ScrollPane sp = new ScrollPane(vBox);
        sp.getStyleClass().add("scroll-view");
        tab.setContent(sp);
        tabPane.getTabs().add(tab);
    }

    private static HBox returnSpaceHbox() {
        HBox hBox = new HBox();
        hBox.setMinHeight(10);
        return hBox;
    }
}
