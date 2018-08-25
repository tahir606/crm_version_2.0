package reports;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.ClientProperty;
import objects.Users;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsDashController implements Initializable {

    @FXML
    private AnchorPane anchor_center;
    @FXML
    private VBox vbox_reports;
    @FXML
    private HBox hbox_filters;
    @FXML
    private Label txt_reportName;

    private mySqlConn sql;

    private String reportFilter = "";
    private int reportSelected = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        txt_reportName.setVisible(false);

        populateReports();
    }

    private void populateReports() {
        JFXButton rep = new JFXButton();
        createButton(rep, "Tickets Solved by User");
        rep.setOnAction(event -> ticketsSolvedByUser());

        JFXButton rep2 = new JFXButton();
        createButton(rep2, "Emails per Client");
        rep2.setOnAction(event -> emailsPerClient());

        vbox_reports.getChildren().addAll(rep, rep2);
    }

    private void createButton(JFXButton button, String text) {
        button.setText(text);
        button.setMinWidth(vbox_reports.getWidth());
        button.setStyle("-fx-font-size: 9pt;" +
                "-fx-text-fill: #000000;" +
                "-fx-underline: true;");
        return;
    }

    public void ticketsSolvedByUser() {
        if (reportSelected != 1)
            reportFilter = "";

        reportSelected = 1;

        txt_reportName.setVisible(true);
        txt_reportName.setText("Tickets Solved by User - All Time");

        TableView<Users> tableView = new TableView<>();

        TableColumn<Users, String> cCode = new TableColumn<>("Code");
        TableColumn<Users, Integer> cName = new TableColumn<>("Name");
        TableColumn<Users, Integer> cSolved = new TableColumn<>("Solved");

        cCode.setCellValueFactory(new PropertyValueFactory<>("UCODE"));
        cName.setCellValueFactory(new PropertyValueFactory<>("FNAME"));
        cSolved.setCellValueFactory(new PropertyValueFactory<>("Solved"));

        tableView.getColumns().addAll(cCode, cName, cSolved);

        //set filters
        hbox_filters.getChildren().clear();

        JFXComboBox<String> filters = new JFXComboBox<>();
        filters.setPromptText("Select Filter");
        filters.getItems().addAll("Today", "Last 7 Days", "Last 30 Days", "All Time"); //"Custom"  //"Last 30 Days",
        filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Today": {
                    reportFilter = " AND SOLVTIME BETWEEN '" + CommonTasks.getCurrentDate() + "' AND '" + CommonTasks.getCurrentDate() + " 23:59:59'";
                    break;
                }
                case "Last 7 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(7);
                    reportFilter = " AND SOLVTIME BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
                    break;
                }
                case "Last 30 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(30);
                    reportFilter = " AND SOLVTIME BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
                    break;
                }
                case "All Time": {
                    reportFilter = "";
                    break;
                }
                default:
                    break;
            }
            ticketsSolvedByUser();
            txt_reportName.setText("Tickets Solved by User - " + newValue);
        });

        hbox_filters.getChildren().add(filters);

        // add data
        tableView.getItems().setAll(sql.ticketsSolvedByUser(reportFilter));

        tableView.setPrefHeight(200);

        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);
    }

    public void emailsPerClient() {
        if (reportSelected != 2)
            reportFilter = "";

        reportSelected = 2;

        txt_reportName.setVisible(true);
        txt_reportName.setText("Emails Per User");

        TableView<ClientProperty> tableView = new TableView<>();

        TableColumn<ClientProperty, String> cCode = new TableColumn<>("Code");
        TableColumn<ClientProperty, Integer> cName = new TableColumn<>("Name");
        TableColumn<ClientProperty, Integer> cTotalEmails = new TableColumn<>("No. of Emails");

        cCode.setCellValueFactory(new PropertyValueFactory<>("Code"));
        cName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        cTotalEmails.setCellValueFactory(new PropertyValueFactory<>("totalEmails"));

        tableView.getColumns().addAll(cCode, cName, cTotalEmails);

        //set filters
        hbox_filters.getChildren().clear();

        JFXComboBox<String> filters = new JFXComboBox<>();
        filters.setPromptText("Select Filter");
        filters.getItems().addAll("Today", "Last 7 Days", "Last 30 Days", "All Time"); //"Custom"  //"Last 30 Days",
        filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Today": {
                    reportFilter = " AND TSTMP BETWEEN '" + CommonTasks.getCurrentDate() + "' AND '" + CommonTasks.getCurrentDate() + " 23:59:59'";
                    break;
                }
                case "Last 7 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(7);
                    reportFilter = " AND TSTMP BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
                    break;
                }
                case "Last 30 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(30);
                    reportFilter = " AND TSTMP BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
                    break;
                }
                case "All Time": {
                    reportFilter = "";
                    break;
                }
                default:
                    break;
            }
            emailsPerClient();
            txt_reportName.setText("Emails Per Client - " + newValue);
        });

        hbox_filters.getChildren().add(filters);

        // add data
        tableView.getItems().setAll(sql.emailsPerClient(reportFilter));

        tableView.setPrefHeight(200);

        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);
    }


}
