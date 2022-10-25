package Home;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dashboard.dController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import objects.*;
import org.joda.time.LocalDate;
import product.details.UnlockDialogController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static JCode.CommonTasks.getDateFormattedOnly;

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
        List<Task> taskList = new ArrayList<>();
        try {

            taskList = RequestHandler.listRequestHandler(RequestHandler.run("task/getAllTask"), Task.class);
        } catch (IOException e) {
            e.printStackTrace();
        }


        HBox hBox = new HBox();
        hBox.getChildren().addAll(inflateTraditionalLabel("Subject", "headingText", 120),
                inflateTraditionalLabel("Due Date", "headingText", 90),
                inflateTraditionalLabel("Created By", "headingText", 90),
                inflateTraditionalLabel("Created On", "headingText", 160));

        vBox.getChildren().addAll(hBox);

        for (Task task : taskList) {
            HBox hbox = new HBox();
            hbox.setSpacing(5);

            Label subjectLabel = new Label(task.getSubject()),
                    dueDateLabel = new Label(task.getDueDate()),
                    createdByLabel = new Label(task.getUsers().getFullName()),
                    createdOnLabel = new Label(task.getCreatedOn());

            subjectLabel.getStyleClass().add("dataText");
            dueDateLabel.getStyleClass().add("dataText");
            createdByLabel.getStyleClass().add("dataText");
            createdOnLabel.getStyleClass().add("dataText");

            subjectLabel.setMinWidth(90);
            subjectLabel.setMaxWidth(90);
            dueDateLabel.setMinWidth(90);
            dueDateLabel.setMaxWidth(90);
            createdByLabel.setMinWidth(90);
            createdByLabel.setMaxWidth(90);
            createdOnLabel.setMinWidth(160);
            createdOnLabel.setMaxWidth(160);

            hbox.getChildren().addAll(subjectLabel, dueDateLabel, createdByLabel, createdOnLabel);
            hbox.getStyleClass().add("moduleDetails");

            vBox.getChildren().add(hbox);
        }

        scrollPane.setContent(vBox);

        pane.getChildren().clear();
        pane.getChildren().addAll(scrollPane);

        inflateClearButton(pane, panel);
    }

    private static void inflateModules(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);
        List<Product> productList = new ArrayList<>();
        List<Product> productList2 = new ArrayList<>();
        List<ProductModule> productModuleList = new ArrayList<>();
        try {
            productList = RequestHandler.listRequestHandler(RequestHandler.run("product/getAllProducts"), Product.class);


        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!productList.isEmpty()) {
            for (Product product : productList) {
                for (ProductModule productModule : product.getPdProductModule()) {
                    if (productModule.getPmModuleLockingList().isEmpty()) {
                        continue;
                    }
                    if (productModule.getPmModuleLockingList().get(0).getUnLockedTime() == null) {
                        productModuleList.add(productModule);
                        productList2.add(new Product(product.getName(), productModuleList));
                    } else {
                        continue;
                    }
                }
            }
        }

        HBox hBox = new HBox();
        hBox.getChildren().addAll(inflateTraditionalLabel("Module", "headingText", 90),
                inflateTraditionalLabel("Product", "headingText", 90),
                inflateTraditionalLabel("Locked By", "headingText", 90),
                inflateTraditionalLabel("Locked On", "headingText", 160));

        vBox.getChildren().addAll(hBox);
        for (Product product : productList2) {
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            for (ProductModule productModule : product.getPdProductModule()) {
                Label moduleLabel = new Label(productModule.getName()),
                        productLabel = new Label(product.getName()),
                        lockedByLabel = new Label(productModule.getPmModuleLockingList().get(0).getUsers().getFullName()),
                        lockedTimeLabel = new Label(CommonTasks.getTimeFormatted(productModule.getPmModuleLockingList().get(0).getLockedTime()));

                moduleLabel.getStyleClass().add("dataText");
                productLabel.getStyleClass().add("dataText");
                lockedByLabel.getStyleClass().add("dataText");
                lockedTimeLabel.getStyleClass().add("dataText");

                moduleLabel.setMinWidth(90);
                moduleLabel.setMaxWidth(90);
                productLabel.setMinWidth(90);
                productLabel.setMaxWidth(90);
                lockedByLabel.setMinWidth(90);
                lockedByLabel.setMaxWidth(90);
                lockedTimeLabel.setMinWidth(160);
                lockedTimeLabel.setMaxWidth(160);

                JFXButton btn_lock = new JFXButton("Unlock");
                btn_lock.setStyle("-fx-font-weight: bold;");
                btn_lock.setOnAction(event -> {
                    UnlockDialogController.fromPane = 'H';
                    sModule = productModule;
                    CommonTasks.inflateDialog("Unlock Module", "/product/details/unlock_dialog.fxml");
                });

                hbox.getChildren().addAll(moduleLabel, productLabel, lockedByLabel, lockedTimeLabel, btn_lock);
                hbox.getStyleClass().add("moduleDetails");

                vBox.getChildren().add(hbox);
            }
        }

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

    static String reportFilterFrom = "", reportFilterTo = "";

    private static void inflateTicketsPerUser(AnchorPane pane, int panel) {
        JFXDatePicker from = new JFXDatePicker();
        JFXDatePicker to = new JFXDatePicker();

        JFXComboBox<String> filters = new JFXComboBox<>();
        filters.setPromptText("Select Filter");
        filters.getItems().addAll("Today", "Last 7 Days", "Last 30 Days", "Custom Date"); //"Custom"  //"Last 365 Days",
        HBox hBox = new HBox(filters, from, to);

        hBox.setPadding(new Insets(0, 12, 0, 12));
        hBox.setSpacing(10);
        AnchorPane.setLeftAnchor(hBox, 5.0);
        AnchorPane.setTopAnchor(hBox, 2.0);

        from.setVisible(false);
        to.setVisible(false);

        filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Today": {
                    reportFilterFrom = CommonTasks.getCurrentDate();
                    reportFilterTo = CommonTasks.getCurrentDate();
                    break;
                }
                case "Last 7 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(7);
                    reportFilterFrom = String.valueOf(beforeDate);
                    reportFilterTo = String.valueOf(now);
                    break;
                }
                case "Last 30 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(30);
                    reportFilterFrom = String.valueOf(beforeDate);
                    reportFilterTo = String.valueOf(now);
                    break;
                }
                case "Custom Date": {
                    reportFilterFrom = "";
                    reportFilterTo = "";
                    break;
                }
                default:
                    break;
            }
            if (newValue.equals("Custom Date")) {
                from.setVisible(true);
                to.setVisible(true);

                fHelper.writeDashFiltersText(newValue, panel);
                from.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (from.getValue() != null && to.getValue() != null) {
                            pane.getChildren().clear();
                            pane.getChildren().addAll(inflatePieChart(from.getValue().toString(), to.getValue().toString()), hBox);

                            inflateClearButton(pane, panel);


                            fHelper.writeDashFilters(from.getValue().toString(), to.getValue().toString(), panel);
                            //Change focus so that combo box can be used again
                            split_mainS.requestFocus();
                        }
                    }
                });
                to.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        if (from.getValue() != null && to.getValue() != null) {
                            pane.getChildren().clear();
                            pane.getChildren().addAll(inflatePieChart(from.getValue().toString(), to.getValue().toString()), hBox);

                            inflateClearButton(pane, panel);

                            fHelper.writeDashFilters(from.getValue().toString(), to.getValue().toString(), panel);
                            //Change focus so that combo box can be used again
                            split_mainS.requestFocus();
                        }
                    }
                });


            } else {
                from.setVisible(false);
                to.setVisible(false);
                pane.getChildren().clear();
                pane.getChildren().addAll(inflatePieChart(reportFilterFrom, reportFilterTo), hBox);

                inflateClearButton(pane, panel);

                fHelper.writeDashFiltersText(newValue, panel);
                fHelper.writeDashFilters(reportFilterFrom, reportFilterTo, panel);
                //Change focus so that combo box can be used again
                split_mainS.requestFocus();
            }
        });

        List<DashFilters> filtersListText = fHelper.readDashFiltersText();

        if (!filtersListText.isEmpty()) {
            for (DashFilters dashFilters : filtersListText) {
                if (dashFilters.getPanel() == panel) {
                    if (dashFilters.getFilterText().equals("null")) {
                        filters.getSelectionModel().select("All Time");
                    } else {
                        filters.getSelectionModel().select(dashFilters.getFilterText());
                    }
                }
            }

            List<DashFilters> filtersList = fHelper.readDashFilters();

            if (!filtersList.isEmpty()) {

                for (DashFilters dashFilters : filtersList) {
                    if (dashFilters.getPanel() == panel) {
                        reportFilterFrom = dashFilters.getFrom();
                        reportFilterTo = dashFilters.getTo();
                        from.setValue(CommonTasks.createLocalDateForFilter(reportFilterFrom));
                        to.setValue(CommonTasks.createLocalDateForFilter(reportFilterTo));
                        pane.getChildren().clear();
                        pane.getChildren().addAll(inflatePieChart(reportFilterFrom, reportFilterTo), hBox);

                        inflateClearButton(pane, panel);
                    }
                }
            }
        }
        if (reportFilterTo.equals("") && reportFilterFrom.equals("")) {
            pane.getChildren().clear();
            pane.getChildren().addAll(hBox);
        } else {
            pane.getChildren().clear();
            pane.getChildren().addAll(inflatePieChart(reportFilterFrom, reportFilterTo), hBox);
        }
        inflateClearButton(pane, panel);

        //Change focus so that combo box can be used again
        split_mainS.requestFocus();

    }

    private static PieChart inflatePieChart(String reportFilterFrom, String reportFilterTo) {
        List<Users> users = null;
        try {
            users = RequestHandler.listRequestHandler(RequestHandler.run("users/getSolvedEmailsByUsers?from=" + reportFilterFrom + "&to=" + reportFilterTo), Users.class);

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
        chart.setTitle("Tickets Per User - (" + getDateFormattedOnly(reportFilterFrom) + " - " + getDateFormattedOnly(reportFilterTo) + " )");
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
