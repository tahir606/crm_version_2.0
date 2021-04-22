package reports;

import ApiHandler.RequestHandler;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.*;

import java.io.IOException;
import java.net.URL;
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
    private HBox hbox_User;
    //
    private List<Users> usersList = null;
    private List<Client> clientList = null;
    //
    @FXML
    private Label txt_reportName;
    @FXML
    private Label txt_Count;
    private mySqlConn sql;

    private String reportFilter = "";
    private int reportSelected = 0;
    final ObservableList<EmailProperty> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();
        txt_reportName.setVisible(false);
        txt_Count.setVisible(false);
        populateReports();
    }

    private void populateReports() {
        JFXButton rep = new JFXButton();
        createButton(rep, "Tickets Solved by User");
        rep.setOnAction(event -> ticketsSolvedByUser());

        JFXButton rep2 = new JFXButton();
        createButton(rep2, "Emails Received per Client");
        rep2.setOnAction(event -> emailsPerClient());

        JFXButton rep3 = new JFXButton();
        createButton(rep3, "Tickets solved by each user: Details");
        rep3.setOnAction(event -> ticketsSolvedByUser_Details());

        JFXButton rep4 = new JFXButton();
        createButton(rep4, "Average Time of Tickets solved by each user");
        rep4.setOnAction(event -> averageTime());

        JFXButton rep5 = new JFXButton();
        createButton(rep5, "Tickets Details by Clients Reports");
        rep5.setOnAction(event -> clientReport());

        vbox_reports.getChildren().addAll(rep, rep2, rep3, rep4, rep5);
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
        if (reportSelected != 1) {
            reportFilter = "";
            hbox_User.setVisible(false);
        }

        reportSelected = 1;

        txt_reportName.setVisible(true);
        txt_reportName.setText("Tickets Solved by User - All Time");

        txt_Count.setVisible(true);
        TableView<Users> tableView = new TableView<>();

        TableColumn<Users, String> cCode = new TableColumn<>("Code");
        TableColumn<Users, Integer> cName = new TableColumn<>("Name");
        TableColumn<Users, Integer> cSolved = new TableColumn<>("Solved");

        cCode.setCellValueFactory(new PropertyValueFactory<>("userCode"));
        cName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        cSolved.setCellValueFactory(new PropertyValueFactory<>("availableCount"));
        tableView.getColumns().addAll(cCode, cName, cSolved);
        durationFilter();
        List<Users> user = null;
        try {

            user = RequestHandler.listRequestHandler(RequestHandler.run("users/getSolvedEmailsByUsers?filter=" + reportFilter), Users.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<Users> user = sql.ticketsSolvedByUser(reportFilter);
        txt_Count.setText("Displaying " + (user == null ? 0 : user.size()) + " Records");
        tableView.getItems().setAll(user);
        tableView.setPrefHeight(200);

        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 20.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);
    }

    public void emailsPerClient() {
        if (reportSelected != 2) {
            reportFilter = "";
            hbox_User.setVisible(false);
        }
        reportSelected = 2;

        txt_reportName.setVisible(true);
        txt_reportName.setText("Emails Received Per User");
        txt_Count.setVisible(true);

        TableView<Client> tableView = new TableView<>();
        TableColumn<Client, String> cCode = new TableColumn<>("Code");
        TableColumn<Client, Integer> cName = new TableColumn<>("Name");
        TableColumn<Client, Integer> cTotalEmails = new TableColumn<>("No. of Emails");

        cCode.setCellValueFactory(new PropertyValueFactory<>("clientID"));
        cName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cTotalEmails.setCellValueFactory(new PropertyValueFactory<>("availableCount"));
        tableView.getColumns().addAll(cCode, cName, cTotalEmails);
        durationFilter();
        List<Client> clients = null;
        try {
            clients = RequestHandler.listRequestHandler(RequestHandler.run("client/emailsPerClient?filter=" + reportFilter), Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<ClientProperty> clientProperties = sql.emailsPerClient(reportFilter);
        txt_Count.setText("Displaying " + (clients == null ? 0 : clients.size()) + " Records");
        tableView.getItems().setAll(clients);
        tableView.setPrefHeight(200);
        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 20.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);

    }

    Users users = new Users();
    String filter = "all time";

    private void ticketsSolvedByUser_Details() {
        if (reportSelected != 3) {
            reportFilter = "";
            hbox_User.setVisible(true);
            hbox_filters.setVisible(false);
        }
        reportSelected = 3;
        txt_reportName.setVisible(true);
        txt_Count.setVisible(true);
        txt_Count.setText("Displaying " + 0 + " Records");
        //set filters_Select
        TableView<Email> tableView = new TableView<>();
        TableColumn<Email, Integer> email_No = new TableColumn<>("Ticket No");
        TableColumn<Email, String> subject = new TableColumn<>("Subject");
        TableColumn<Email, String> from = new TableColumn<>("From");
        TableColumn<Email, String> timesTamp = new TableColumn<>("Timestamp");
        TableColumn<Email, String> lockTime = new TableColumn<>("Lock Time");
        TableColumn<Email, String> solved_Time = new TableColumn<>("Solve Time");
        TableColumn<Email, String> duration = new TableColumn<>("Duration");
        hbox_User.getChildren().clear();
        JFXComboBox<Users> user_filters = new JFXComboBox<>();
        txt_reportName.setText("Tickets Solved by User: Details");
        user_filters.setPromptText("Select User");
        try {
            usersList = RequestHandler.listRequestHandler(RequestHandler.run("users/getALlUsers"), Users.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        usersList = sql.getAllUsers();
        user_filters.getItems().addAll(usersList);
        user_filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            users = newValue;
            ticketsSolvedByUser_Details();
            txt_reportName.setText("Ticket Solved By " + users + ": Details" + " in " + filter);
            durationFilter();
        });
        hbox_User.getChildren().add(user_filters);
        List<Email> emails = null;
        try {

            emails = RequestHandler.jsonListRequestHandler(RequestHandler.run("ticket/ticketsSolvedByUserDetails/" + users.getUserCode() + "?filter=" + reportFilter));

        } catch (IOException e) {
            e.printStackTrace();
        }
        txt_Count.setText("Displaying " + (emails == null ? 0 : emails.size()) + " Records");
        tableView.getColumns().clear();
        email_No.setCellValueFactory(new PropertyValueFactory<>("ticketNo"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        from.setCellValueFactory(new PropertyValueFactory<>("fromAddress"));
        solved_Time.setCellValueFactory(new PropertyValueFactory<>("solvedTime"));
        lockTime.setCellValueFactory(new PropertyValueFactory<>("lockedTime"));
        timesTamp.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableView.getColumns().addAll(email_No, subject, from, timesTamp, lockTime, solved_Time, duration);
        tableView.getItems().setAll(emails);
        tableView.setPrefHeight(200);
        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 20.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);

    }

    private void averageTime() {
        if (reportSelected != 4) {
            reportFilter = "";
            hbox_User.setVisible(false);
            hbox_filters.setVisible(false);
        }
        reportSelected = 4;
        txt_reportName.setVisible(true);
        txt_reportName.setText("Average Time For Each User To Solve a Ticket");
        TableView<Users> tableView = new TableView<>();
        TableColumn<Users, String> user_name = new TableColumn<>("User Name");
        TableColumn<Users, String> average_Time = new TableColumn<>("Average Time");
        user_name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        average_Time.setCellValueFactory(new PropertyValueFactory<>("availableString"));
        tableView.getColumns().addAll(user_name, average_Time);
        List<Users> emails = null;
        try {

            emails = RequestHandler.jsonListUserRequestHandler(RequestHandler.run("users/averageCalculate" ));

        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<EmailProperty> averageTime = sql.average_Calculate();
        tableView.getItems().setAll(emails);
        tableView.setPrefHeight(200);
        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 20.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);

    }

    Client clientName = new Client();

    public void clientReport() {
        if (reportSelected != 5) {
            reportFilter = "";
            hbox_User.setVisible(true);
            hbox_filters.setVisible(false);
        }
        reportSelected = 5;
        txt_reportName.setVisible(true);
        txt_Count.setVisible(true);
        txt_Count.setText("Displaying " + 0 + " Records");

        TableView<Email> tableView = new TableView<>();
        TableColumn<Email, Integer> ticketNumber = new TableColumn<>("Ticket No");
        TableColumn<Email, Integer> from = new TableColumn<>("From");
        TableColumn<Email, String> subject = new TableColumn<>("Subject");
        TableColumn<Email, String> receivedTime = new TableColumn<>("Received Time");
        TableColumn<Email, String> lockTime = new TableColumn<>("Lock Time");
        TableColumn<Email, Integer> solvedTime = new TableColumn<>("Solved Time");
        TableColumn<Email, Integer> duration = new TableColumn<>("Duration");
        hbox_User.getChildren().clear();

        JFXComboBox<Client> client_filters = new JFXComboBox<>();
        txt_reportName.setText("Tickets Details by Clients Reports");
        client_filters.setPromptText("Select Client");
        try {
            clientList = RequestHandler.listRequestHandler(RequestHandler.run("client/clientList"), Client.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        clientList = sql.clientName();
        client_filters.getItems().addAll(clientList);
        client_filters.valueProperty().addListener((observable, oldValue, newValue) -> {
            clientName = newValue;
            clientReport();
            txt_reportName.setText("Tickets Details by Clients Reports: " + clientName + " in " + filter);
            durationFilter();
        });
        hbox_User.getChildren().add(client_filters);
        List<Email> emails = null;
        try {
            emails = RequestHandler.jsonListRequestHandler(RequestHandler.run("ticket/clientReportWithDomain/" + clientName.getClientID() + "?filter=" + reportFilter));

        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<EmailProperty> clientReport = sql.clientReportWithDomain(clientName, reportFilter);
        txt_Count.setText("Displaying " + (emails == null ? 0 : emails.size()) + " Records");

        tableView.getColumns().clear();

        ticketNumber.setCellValueFactory(new PropertyValueFactory<>("ticketNo"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        from.setCellValueFactory(new PropertyValueFactory<>("fromAddress"));
        solvedTime.setCellValueFactory(new PropertyValueFactory<>("solvedTime"));
        lockTime.setCellValueFactory(new PropertyValueFactory<>("lockedTime"));
        receivedTime.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        tableView.getColumns().addAll(ticketNumber, from, subject, receivedTime, lockTime, solvedTime, duration);

        tableView.getItems().setAll(emails);
        tableView.setPrefHeight(200);
        AnchorPane.setTopAnchor(tableView, 40.0);
        AnchorPane.setBottomAnchor(tableView, 20.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);

        anchor_center.getChildren().add(tableView);
    }

    String column = "";

    private void durationFilter() {
        hbox_filters.getChildren().clear();
        if (reportSelected < 3 || reportSelected > 3) {
            column = "TSTMP";
        } else if (reportSelected == 3) {
            column = "SOLVTIME";
        }

        hbox_filters.setVisible(true);
        JFXComboBox<String> filters = new JFXComboBox<>();
        filters.setPromptText("Select Filter");
        filters.getItems().addAll("Today", "Last 7 Days", "Last 30 Days", "All Time"); //"Custom"  //"Last 30 Days",
        filters.valueProperty().addListener((observable, oldValue, newValue) -> {
//            if (reportSelected == 1 && reportSelected == 2) {
            switch (newValue) {
                case "Today": {
                    reportFilter = "Today";
                    break;
                }
                case "Last 7 Days": {
                    reportFilter = "Last 7 Days";
                    break;
                }
                case "Last 30 Days": {
                    reportFilter = "Last 30 Days";
                    break;
                }
                case "All Time": {
                    reportFilter = "All Time";
                    break;
                }
                default:
                    break;
//                case "Today": {
//                    reportFilter = " AND " + column + " BETWEEN '" + CommonTasks.getCurrentDate() + "' AND '" + CommonTasks.getCurrentDate() + " 23:59:59'";
//                    break;
//                }
//                case "Last 7 Days": {
//                    LocalDate now = new LocalDate();
//                    LocalDate beforeDate = now.minusDays(7);
//                    reportFilter = " AND " + column + " BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
//                    break;
//                }
//                case "Last 30 Days": {
//                    LocalDate now = new LocalDate();
//                    LocalDate beforeDate = now.minusDays(30);
//                    reportFilter = " AND " + column + " BETWEEN '" + beforeDate + "' AND '" + now + " 23:59:59'";
//                    break;
//                }
//                case "All Time": {
//                    reportFilter = "";
//                    break;
//                }
//                default:
//                    break;
            }
            if (reportSelected == 1) {
                ticketsSolvedByUser();
                txt_reportName.setText("Tickets Solved by User - " + newValue);
            } else if (reportSelected == 2) {
                emailsPerClient();
                txt_reportName.setText("Emails Received Per User - " + newValue);
            } else if (reportSelected == 3) {
                ticketsSolvedByUser_Details();
                txt_reportName.setText("Ticket Solved By " + users + ": Details" + " in " + newValue);
                filter = newValue;
            } else if (reportSelected == 5) {
                clientReport();
                txt_reportName.setText("Tickets Details by Clients Reports: " + clientName + " in " + newValue);
                filter = newValue;
            } else {
                txt_reportName.setText("Average Time For Each User To Solve a Ticket");
            }
        });
        hbox_filters.getChildren().add(filters);
    }


}
