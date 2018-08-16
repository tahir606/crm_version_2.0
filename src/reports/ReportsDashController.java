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

        vbox_reports.getChildren().addAll(rep);
    }

    private void createButton(JFXButton button, String text) {
        button.setText(text);
        button.setStyle("-fx-font-size: 9pt;" +
                "-fx-text-fill: #000000;" +
                "-fx-underline: true;");
        return;
    }

    public void ticketsSolvedByUser() {

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
                    reportFilter = " AND SOLVTIME BETWEEN '" + beforeDate + "' AND '" + now + "'";
                    break;
                }
                case "Last 30 Days": {
                    LocalDate now = new LocalDate();
                    LocalDate beforeDate = now.minusDays(30);
                    reportFilter = " AND SOLVTIME BETWEEN '" + beforeDate + "' AND '" + now + "'";
                    break;
                }
//                case "Last 365 Days": {
//                    LocalDate now = new LocalDate();
//                    LocalDate beforeDate = now.minusDays(365);
//                    System.out.println(beforeDate);
//                    reportFilter = " AND SOLVTIME BETWEEN '" + beforeDate + "' AND '" + now + "'";
//                    ticketsSolvedByUser();
//                    break;
//                }
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


}
