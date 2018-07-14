package gui;

import JCode.mysql.mySqlConn;
import client.dash.clientView.clientViewController;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import objects.ClientProperty;
import objects.Event;
import objects.Lead;

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
        for (Event task : events) {
//            if (!task.isStatus())
//                constructingOpenTask(task);
//            else
//                constructingCloseTask(task);
        }
    }

    private static void constructLeadActivities() {
        constructingButtons();

        //Heading
        String labelCss = "-fx-font-weight: bold;";
        Label label = new Label("Open Activities");
        label.setStyle(labelCss);
        open_activities_list.getChildren().addAll(label);

        Label label2 = new Label("Closed Activities");
        label2.setStyle(labelCss);
        closed_activities_list.getChildren().addAll(label2);

        List<Task> tasks = sql.getTasks(LeadViewController.staticLead);
        for (Task task : tasks) {
            if (!task.isStatus())
                constructingOpenTask(task);
            else
                constructingCloseTask(task);
        }
    }

    private static void constructingButtons() {
        HBox box = new HBox();
        JFXButton newTask = new JFXButton("+ New Task");

        String css = "-fx-text-fill: #005ff7;";

        newTask.setStyle(css);
        newTask.setOnAction(event -> {
            NewTaskController.stInstance = 'N';
//            CommonTasks.inflateDialog("New Task", TasksConstructor.class.getResource("../activity/task/new_task" +
//                    ".fxml"));
//            CommonTasks.inflateDialog("New Task", path);
            inflateNewTask("New Task");
        });
        box.getChildren().addAll(newTask);

        open_activities_list.getChildren().addAll(box);
    }

    private static void constructingOpenTask(Task task) {
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
        Label createdBy = new Label(task.getCreatedBy()),
                createdOn = new Label(task.getCreatedOn());
        createdOn.setMinWidth(150);
        createdBy.setMinWidth(280);
        JFXButton options = new JFXButton();

        Image image = new Image(TasksConstructor.class.getResourceAsStream("/res/img/options.png"));
        options.setGraphic(new ImageView(image));
        details.getChildren().addAll(createdOn, createdBy, options);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem closeItem = new MenuItem("Close"),
                editItem = new MenuItem("Edit"),
                delItem = new MenuItem("Delete");
        editItem.setOnAction(t -> {
            NewTaskController.stInstance = 'U';
            updatingTask = task;
            inflateNewTask("Update Task");
        });
        closeItem.setOnAction(t -> {
            sql.closeTask(task);
            generalConstructor(choice);
        });
        delItem.setOnAction(t -> {
            sql.archiveTask(task);
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
        open_activities_list.getChildren().add(box);
    }

    private static void constructingCloseTask(Task task) {
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
        Label createdBy = new Label(task.getCreatedBy()),
                createdOn = new Label(task.getCreatedOn());
        createdOn.setMinWidth(150);
        createdBy.setMinWidth(280);

        details.getChildren().addAll(createdOn, createdBy);
        ContextMenu contextMenu = new ContextMenu();

        VBox box = new VBox();
//            box.setStyle("-fx-border-color: #033300;");
        box.getChildren().addAll(subject, body, details);
        closed_activities_list.getChildren().add(box);
    }

    public static void generalConstructor(int choice) throws NullPointerException {

        open_activities_list.getChildren().clear();
        closed_activities_list.getChildren().clear();

        TasksConstructor.choice = choice;
        switch (choice) {
            case 2: {     //Clients
                constructClientActivities();
                break;
            }
            case 3: {
                constructLeadActivities();
                break;
            }
            case 4: {
                constructProductActivities();
                break;
            }
        }

        VBox vBox = new VBox();
        vBox.getChildren().addAll(returnSpaceHbox(), open_activities_list, returnSpaceHbox(), new Separator(), returnSpaceHbox(), closed_activities_list);
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

    public static void inflateWindow(String title, String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TasksConstructor.class.getResource(path));
            Parent root1 = fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle(title);
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inflateNewTask(String title) {
        inflateWindow(title, "/activity/task/new_task.fxml");
    }
}
