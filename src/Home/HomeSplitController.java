package Home;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dashboard.dController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.ProductModule;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HomeSplitController implements Initializable {

    @FXML
    private AnchorPane main_anchor;
    @FXML
    private AnchorPane pane_one;
    @FXML
    private AnchorPane pane_three;
    @FXML
    private AnchorPane pane_two;
    @FXML
    private AnchorPane pane_four;
    @FXML
    private SplitPane split_main;
    @FXML
    private SplitPane split_one;
    @FXML
    private SplitPane split_two;
    private static SplitPane split_mainS,
            split_oneS,
            split_twoS;
    private static AnchorPane pane_oneS,
            pane_twoS,
            pane_threeS,
            pane_fourS;
    private static Users user;
    private static FileHelper fHelper;
//    private static mySqlConn sql;

    public static ProductModule sModule;
//    private static ArrayList<ProductModule> lockedModules;

    private static String[] dashBoardDets, splitPanes;
    private int noOfPanels = 4;

    private static String ticketsFilter = "";
    static int noOfSolvedEmails, noOfLockedEmails, noOfUnlockedEmails, noOfUnSolvedEmails;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        split_mainS = split_main;
        split_oneS = split_one;
        split_twoS = split_two;

        pane_oneS = pane_one;
        pane_twoS = pane_two;
        pane_threeS = pane_three;
        pane_fourS = pane_four;

        split_main.getStyleClass().add("scroll-view");

        setAddButton(pane_one, 1);
        setAddButton(pane_two, 2);
        setAddButton(pane_three, 3);
        setAddButton(pane_four, 4);

        fHelper = new FileHelper();
//        sql = new mySqlConn();

        user = FileHelper.ReadUserApiDetails();
        try {
            noOfSolvedEmails = Integer.parseInt(RequestHandler.run("ticket/CountOfSolvedOrLockedEmails?status=" + 3 + "&userCode=" + user.getUserCode()).body().string());
            noOfLockedEmails = Integer.parseInt(RequestHandler.run("ticket/CountOfSolvedOrLockedEmails?status=" + 2 + "&userCode=" + user.getUserCode()).body().string());
            noOfUnlockedEmails = Integer.parseInt(RequestHandler.run("ticket/CountOfUnLockedEmails").body().string());
            noOfUnSolvedEmails = Integer.parseInt(RequestHandler.run("ticket/CountOfUnSolvedEmails").body().string());
            user = (Users) RequestHandler.objectRequestHandler(RequestHandler.run("users/getUser/" + user.getUserCode()), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        dashBoardDets = new String[noOfPanels];

        setUpPreviousPanels();

        dController.img_load.setVisible(false);
    }

    public static void saveDividerPositions() {
        try {
            fHelper.writeSplitPaneDiveders(new String[]{String.valueOf(split_mainS.getDividerPositions()[0]),
                    String.valueOf(split_oneS.getDividerPositions()[0]),
                    String.valueOf(split_twoS.getDividerPositions()[0])});
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    public static void setUpPreviousPanels() {
        dashBoardDets = fHelper.readDashboardPanels();
        splitPanes = fHelper.readSplitPaneDividers();

        String p1 = dashBoardDets[0],
                p2 = dashBoardDets[1],
                p3 = dashBoardDets[2],
                p4 = dashBoardDets[3];

        double d1 = Double.parseDouble(splitPanes[0]),
                d2 = Double.parseDouble(splitPanes[1]),
                d3 = Double.parseDouble(splitPanes[2]);

        split_mainS.setDividerPositions(d1);
        split_oneS.setDividerPositions(d2);
        split_twoS.setDividerPositions(d3);

        setUpPane(p1, pane_oneS, 1);
        setUpPane(p2, pane_twoS, 2);
        setUpPane(p3, pane_threeS, 3);
        setUpPane(p4, pane_fourS, 4);

    }

    private static void setAddButton(AnchorPane pane, int panel) {

        JFXComboBox<String> paneList = new JFXComboBox<>();
        paneList.setPromptText("Choose panel");
        paneList.getItems().addAll(new String[]{"Profile", "Tickets", "Tickets Per User", "Activities", "Modules"});
        paneList.setOpacity(0.5);
        paneList.setPadding(new Insets(-3.5));
        paneList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setUpPane(newValue, pane, panel));
        pane.getChildren().add(paneList);
        AnchorPane.setRightAnchor(paneList, 4.0);
        AnchorPane.setTopAnchor(paneList, 2.0);
    }

    private static void setUpPane(String choice, AnchorPane pane, int panel) {
        if (choice.equals("null"))
            return;

        switch (choice) {
            case "Profile": {
                inflateProfile(pane, panel);
                break;
            }
            case "Tickets": {
                inflateTickets(pane, panel);
                break;
            }
            case "Tickets Per User": {
                inflateTicketsPerUser(pane, panel);
                break;
            }
            case "Activities": {
                inflateActivities(pane, panel);
                break;
            }
            case "Modules": {
                inflateModules(pane, panel);
                break;
            }
        }

        dashBoardDets[panel - 1] = choice;
        fHelper.writeDashboardPanels(dashBoardDets);
    }

    private static void inflateActivities(AnchorPane pane, int panel) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-view");

        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(scrollPane, 20.0);
        AnchorPane.setTopAnchor(scrollPane, 20.0);

//        List<TaskOld> openTaskOlds = sql.getAllTasks(" AND TS_STATUS = 0 ");

        HBox hBox = new HBox();
        hBox.getChildren().addAll(inflateTraditionalLabel("Subject", "headingText", 120),
                inflateTraditionalLabel("Due Date", "headingText", 90),
                inflateTraditionalLabel("Created By", "headingText", 90),
                inflateTraditionalLabel("Created On", "headingText", 160));

        vBox.getChildren().addAll(hBox);

//        for (TaskOld taskOld : openTaskOlds) {
//            HBox hbox = new HBox();
//            hbox.setSpacing(5);
//
//            Label subjectLabel = new Label(taskOld.getSubject()),
//                    dueDateLabel = new Label(taskOld.getDueDateFormatted()),
//                    createdByLabel = new Label(taskOld.getCreatedBy()),
//                    createdOnLabel = new Label(taskOld.getCreatedOn());
//
//            subjectLabel.getStyleClass().add("dataText");
//            dueDateLabel.getStyleClass().add("dataText");
//            createdByLabel.getStyleClass().add("dataText");
//            createdOnLabel.getStyleClass().add("dataText");
//
//            subjectLabel.setMinWidth(90);
//            subjectLabel.setMaxWidth(90);
//            dueDateLabel.setMinWidth(90);
//            dueDateLabel.setMaxWidth(90);
//            createdByLabel.setMinWidth(90);
//            createdByLabel.setMaxWidth(90);
//            createdOnLabel.setMinWidth(160);
//            createdOnLabel.setMaxWidth(160);
//
//            hbox.getChildren().addAll(subjectLabel, dueDateLabel, createdByLabel, createdOnLabel);
//            hbox.getStyleClass().add("moduleDetails");
//
//            vBox.getChildren().add(hbox);
//        }

        scrollPane.setContent(vBox);

        pane.getChildren().clear();
        pane.getChildren().addAll(scrollPane);

        inflateClearButton(pane, panel);
    }

    private static void inflateModules(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

//        lockedModules = sql.getLockedModules();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(inflateTraditionalLabel("Module", "headingText", 90),
                inflateTraditionalLabel("Product", "headingText", 90),
                inflateTraditionalLabel("Locked By", "headingText", 90),
                inflateTraditionalLabel("Locked On", "headingText", 160));

        vBox.getChildren().addAll(hBox);

//        for (ProductModule module : lockedModules) {
//            HBox hbox = new HBox();
//            hbox.setSpacing(5);
//
//            Label moduleLabel = new Label(module.getName()),
//                    productLabel = new Label(module.getProductName()),
//                    lockedByLabel = new Label(module.getLockedByName()),
//                    lockedTimeLabel = new Label(CommonTasks.getTimeFormatted(module.getLockedTime()));
//
//            moduleLabel.getStyleClass().add("dataText");
//            productLabel.getStyleClass().add("dataText");
//            lockedByLabel.getStyleClass().add("dataText");
//            lockedTimeLabel.getStyleClass().add("dataText");
//
//            moduleLabel.setMinWidth(90);
//            moduleLabel.setMaxWidth(90);
//            productLabel.setMinWidth(90);
//            productLabel.setMaxWidth(90);
//            lockedByLabel.setMinWidth(90);
//            lockedByLabel.setMaxWidth(90);
//            lockedTimeLabel.setMinWidth(160);
//            lockedTimeLabel.setMaxWidth(160);
//
//            JFXButton btn_lock = new JFXButton("Unlock");
//            btn_lock.setStyle("-fx-font-weight: bold;");
//            btn_lock.setOnAction(event -> {
//                UnlockDialogController.fromPane = 'H';
//                sModule = module;
//                CommonTasks.inflateDialog("Unlock Module", "/product/details/unlock_dialog.fxml");
//            });
//
//            hbox.getChildren().addAll(moduleLabel, productLabel, lockedByLabel, lockedTimeLabel, btn_lock);
//            hbox.getStyleClass().add("moduleDetails");
//
//            vBox.getChildren().add(hbox);
//        }

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }

    private static void inflateProfile(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

        vBox.getChildren().addAll(inflateTraditionalHbox("Profile", "", "labelHeadingText", "headingText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("Name: ", String.valueOf(user.getFullName()), "labelHeadingText", "dataText"),
                inflateTraditionalHbox("Email: ", String.valueOf(user.getEmail()), "labelHeadingText", "dataText"));
        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }

    private static void inflateTickets(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

        vBox.getChildren().addAll(inflateTraditionalHbox("Tickets", "", "headingText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("Solved by you: ", String.valueOf(noOfSolvedEmails), "labelHeadingText", "dataText"),
                inflateTraditionalHbox("Locked by you: ", String.valueOf(noOfLockedEmails), "labelHeadingText", "dataText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("No. of Unlocked: ", String.valueOf(noOfUnlockedEmails), "labelHeadingText", "dataText"),
                inflateTraditionalHbox("No. of Unsolved: ", String.valueOf(noOfUnSolvedEmails), "labelHeadingText", "dataText"));

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }

    private static void inflateTicketsPerUser(AnchorPane pane, int panel) {


        JFXComboBox<String> filters = new JFXComboBox<>();

        filters.setPromptText("Select Filter");
        filters.getItems().addAll("Today", "Last 7 Days", "Last 30 Days", "All Time"); //"Custom"  //"Last 365 Days",
        AnchorPane.setLeftAnchor(filters, 5.0);
        AnchorPane.setTopAnchor(filters, 2.0);
        filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Today": {
                    ticketsFilter = "Today";
                    break;
                }
                case "Last 7 Days": {
                    ticketsFilter = "Last 7 Days";
                    break;
                }
                case "Last 30 Days": {
                    ticketsFilter = "Last 30 Days";
                    break;
                }
                case "All Time": {
                    ticketsFilter = "All Time";
                    break;
                }
                default:
                    break;
            }
            pane.getChildren().clear();
            pane.getChildren().addAll(inflatePieChart(newValue), filters);

            inflateClearButton(pane, panel);

            fHelper.writeDashFilters(newValue);
            //Change focus so that combo box can be used again
            split_mainS.requestFocus();
        });
        String filter = fHelper.readDashFilters();
        if (filter == null)
            filters.getSelectionModel().select("All Time");
        else
            filters.getSelectionModel().select(filter);

    }

    private static PieChart inflatePieChart(String title) {
        List<Users> users = null;
        try {
            users = RequestHandler.listRequestHandler(RequestHandler.run("users/getSolvedEmailsByUsers?filter=" + ticketsFilter), Users.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<PieChart.Data> list = new ArrayList<>();
        double total = 0;
        for (Users u : users) {
            total = total + u.getAvailableCount();
        }
        for (Users u : users) {
            if (u.getAvailableCount() == 0)
                continue;
            list.add(new PieChart.Data(u.getFullName() + " " + String.format("%.1f%%", 100 * u.getAvailableCount() / total), u.getAvailableCount()));
        }

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(list);
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Tickets Per User - " + title);
        chart.getStyleClass().add("pieChartStyle");
        chart.setLegendVisible(false);
        AnchorPane.setTopAnchor(chart, 40.0);

        return chart;
    }

    private static HBox inflateTraditionalHbox(String label, String data, String style) {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(inflateTraditionalLabel(label, style),
                inflateTraditionalLabel(data, style));
        return hbox;
    }

    private static HBox inflateTraditionalHbox(String label, String data, String labelStyle, String dataStyle) {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(inflateTraditionalLabel(label, labelStyle),
                inflateTraditionalLabel(data, dataStyle));
        return hbox;
    }

    private static Label inflateTraditionalLabel(String data, String style) {
        Label label = new Label(data);
        label.getStyleClass().add(style);
        return label;
    }

    private static Label inflateTraditionalLabel(String data, String style, int width) {
        Label label = inflateTraditionalLabel(data, style);
        label.setPrefWidth(width);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        return label;
    }

    private static void inflateClearButton(AnchorPane pane, int panel) {
        JFXButton clear = new JFXButton("X");
        AnchorPane.setRightAnchor(clear, 1.0);
        AnchorPane.setTopAnchor(clear, 1.0);
        clear.setOnAction(event -> {
            pane.getChildren().clear();

            dashBoardDets[panel - 1] = "null";
            fHelper.writeDashboardPanels(dashBoardDets);

            setAddButton(pane, panel);
        });

        pane.getChildren().addAll(clear);
    }
}
