package Email;

import ApiHandler.RequestHandler;
import Email.EResponse.EResponseController;
import JCode.FileDev;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import ZipFile.ZipFileUtility;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dashboard.dController;
import gui.NotesConstructor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import objects.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static JCode.CommonTasks.convertFormat;

public class EmailDashController implements Initializable {


    @FXML
    private AnchorPane anchor_body, anchor_details, anchor_remarks;
    @FXML
    private Label label_ticket, label_time, label_locked, label_created, title_created, label_from, title_locked, label_count, tittle_solveTime, label_solveTime;
    @FXML
    private TextArea txt_subject;
    @FXML
    private JFXButton btn_lock, btn_solv, btn_unlock, btn_resolve;
    @FXML
    private JFXComboBox<String> combo_respond;
    @FXML
    private VBox category_box, vbox_from, vbox_cc, vbox_filter;
    @FXML
    private JFXComboBox<FileDev> combo_attach;
    @FXML
    private JFXComboBox<Users> selectUser;
    @FXML
    private MenuBar menu_bar;
    @FXML
    private JFXTextField search_txt;
    @FXML
    private ListView<Email> list_emails;

    private mySqlConn sql;
    private FileHelper fHelper;
    private Users user;

    private static ImageView imgLoader = dController.img_load;
    public static String subject, body;

    public static Email selectedEmail = null;

    public static ListView<Email> list_emailsF;

    public static int Email_Type = 1;
    public static String isAdmin;

    public int ticketNumberLatest,
            generalNumberLatest;
    private int ticketLastNumberApi, generalLastNumberApi;
    TabPane tabPane = new TabPane();
    List<Users> usersList = new ArrayList<>();

    public EmailDashController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoader.setVisible(true);

        list_emailsF = list_emails;
        anchor_details.setVisible(false);

        fHelper = new FileHelper();

        user = FileHelper.ReadUserApiDetails();
        Users users = null;
        try {
            users = (Users) RequestHandler.objectRequestHandler(RequestHandler.run("users/getUser/" + user.getUserCode()), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isAdmin = users.getUserRight();

        //Setting icons notifier for unread emails
        ticketNumberLatest = fHelper.ReadLastEmailNumber(1);
        generalNumberLatest = fHelper.ReadLastEmailNumber(2);

        try {
            if (RequestHandler.run("ticket/getMaxTicketNo").body().string()!=null){
                ticketLastNumberApi =  Integer.parseInt(RequestHandler.run("ticket/getMaxTicketNo").body().string());
            }
           if (RequestHandler.run("general/getMaxGeneralNo").body().string()!=null){
               generalLastNumberApi = Integer.parseInt(RequestHandler.run("general/getMaxGeneralNo").body().string());
           }
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (ticketLastNumberApi > ticketNumberLatest) {
            tickets.setText("Tickets*");
        } else {
            tickets.setText("Tickets");
        }

        if (generalLastNumberApi > generalNumberLatest) {
            allMail.setText("General*");
        } else {
            allMail.setText("General");
        }

        fHelper.WriteLastEmailNumber(1, ticketLastNumberApi);
        fHelper.WriteLastEmailNumber(2, generalLastNumberApi);


        populateCategoryBoxes();
        populateMenuBar();
        populateFilters();

        tabPane.setMinWidth(100);
        tabPane.setMinHeight(100);

        list_emails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            selectedEmail = newValue;
            populateDetails(selectedEmail);
            if (Email_Type == 1) {
                populateRemarks(tabPane, selectedEmail);
            }

        });

        //Attaching listener to attaching combo box

        combo_attach.valueProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue == null)
                return;
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(newValue);
                    loadEmailsStatic();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //Attaching and Adding to the Respond Combo Boxes
        combo_respond.getItems().addAll("Respond", "Reply", "Forward");
        combo_respond.getSelectionModel().select(0);
        combo_respond.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Respond")) {
                return;
            }
            Email sEmail = selectedEmail;
            EResponseController.stTo = sEmail.getFromAddress();
            EResponseController.stCc = sEmail.getCcAddress();
            if (newValue.equals("Reply")) {
                EResponseController.stInstance = 'R';
                EResponseController.stSubject = "RE: " + sEmail.getSubject();
                EResponseController.stBody = "\n\n\n" + "On " + sEmail.getTimestamp() + ", " + sEmail.getFromAddress() + " wrote:\n" + sEmail.getBody();
            } else if (newValue.equals("Forward")) {
                if (sEmail.getAttachment() == null || sEmail.getAttachment().isEmpty()) {
                    EResponseController.stSubject = "FW: " + sEmail.getSubject();
                    EResponseController.stInstance = 'F';
                    EResponseController.stBody = sEmail.getBody();
                    EResponseController.stAttach = new ArrayList<>();
                } else {
                    EResponseController.stSubject = "FW: " + sEmail.getSubject();
                    EResponseController.stInstance = 'F';
                    EResponseController.stBody = sEmail.getBody();
                    EResponseController.stAttach = sEmail.getAttachment();
                }
            }
            inflateEResponse(1);
            combo_respond.getSelectionModel().select(0);
        });

        if (Email_Type == 1) {
            timer();
        } else {
            timerTaskNewEmail.cancel();
        }

        List<Users> usersList = null;
        try {
            usersList = RequestHandler.listRequestHandler(RequestHandler.run("users/getALlUsers"), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Users c : usersList) {
            this.usersList.add(c);
        }
        selectUser.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isAdmin.equals("Admin")) {
                if (newValue == null) {
                    return;
                } else {
                    try {
                        RequestHandler.run("ticket/allocate?code=" + selectedEmail.getCode() + "&allocatorCode=" + user.getUserCode() + "&userCode=" + newValue.getUserCode() + "&status=" + 3).close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    loadEmailsStatic();

                }
            }
        });

    }

    public void populateRemarks(TabPane tabPane, Email selectedEmail) {
        tabPane.getTabs().clear();
        new NotesConstructor(tabPane, selectedEmail).generalConstructor(5);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);
        if (!anchor_remarks.getChildren().contains(tabPane)) // if anchorPane not contains tabPane then add tabPane
            anchor_remarks.getChildren().add(tabPane);
    }

    private void changeEmailType(int type, JFXButton btn) throws IOException {
        tickets.getStyleClass().remove("btnMenuBoxPressed");
        allMail.getStyleClass().remove("btnMenuBoxPressed");
        outbox.getStyleClass().remove("btnMenuBoxPressed");
        sentMail.getStyleClass().remove("btnMenuBoxPressed");

        btn.getStyleClass().add("btnMenuBoxPressed");

        if (type != Email_Type) {
            Email_Type = type;
            selectedEmail = null;
        }

        if (type != 1) {
            combo_respond.setDisable(false);
        }

        if (type == 1)
            vbox_filter.setVisible(true);
        else
            vbox_filter.setVisible(false);

        //To set styling to individual cell
        list_emails.setCellFactory(new Callback<ListView<Email>, ListCell<Email>>() {
            @Override
            public ListCell<Email> call(ListView<Email> param) {
                ListCell<Email> cell = new ListCell<Email>() {
                    @Override
                    protected void updateItem(Email item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            Platform.runLater(() -> setText(item.toString()));
//                          This is so that the new emails would be displayed. Otherwise the formatting doesnt apply
                            if (item.getCode() == list_emails.getItems().get(list_emails.getItems().size() - 1).getCode()) {
                                switch (Email_Type) {
                                    case 1:
                                        ticketNumberLatest = ticketLastNumberApi;
                                        break;
                                    case 2:
                                        generalNumberLatest = generalLastNumberApi;
                                        break;
                                }
                            }

                            boolean newEmail = false;

                            switch (Email_Type) {
                                case 1: {
                                    if (item.getTicketNo() > ticketNumberLatest) {
                                        if (!getStyleClass().contains("unreadEmail")) {
                                            getStyleClass().add("unreadEmail");
                                            newEmail = true;
                                            break;
                                        }
                                    } else {
                                        getStyleClass().remove("unreadEmail");
                                        break;
                                    }
                                }
                                case 2: {
                                    if (item.getCode() > generalNumberLatest) {
                                        if (!getStyleClass().contains("unreadEmail")) {
                                            getStyleClass().add("unreadEmail");
                                            newEmail = true;
                                            break;
                                        }
                                    } else {
                                        getStyleClass().remove("unreadEmail");
                                        break;
                                    }
                                }
                            }
                            if (newEmail == false && Email_Type == 1) {
                                if (item.getStatus().equals("UNLOCKED")) {
                                    if (!getStyleClass().contains("unlockedEmail")) {
                                        getStyleClass().add("unlockedEmail");
                                    }
                                } else {
                                    getStyleClass().remove("unlockedEmail");
                                }
                                if (item.getStatus().equals("LOCKED")) {
                                    if (!getStyleClass().contains("lockedEmail")) {
                                        getStyleClass().add("lockedEmail");
                                    }
                                } else {
                                    getStyleClass().remove("lockedEmail");
                                }
                                if (item.getStatus().equals("SOLVED")){
                                    if (!getStyleClass().contains("solvedEmail")) {
                                        getStyleClass().add("solvedEmail");
                                    }
                                } else {
                                    getStyleClass().remove("solvedEmail");
                                }
                                if (item.getStatus().equals("ALLOCATED")){
                                    if (!getStyleClass().contains("allocatedEmail")) {
                                        getStyleClass().add("allocatedEmail");
                                    }
                                } else {
                                    getStyleClass().remove("allocatedEmail");
                                }
                                if (item.getStatus().equals("RESOLVED")){
                                    if (!getStyleClass().contains("resolvedEmail")) {
                                        getStyleClass().add("resolvedEmail");
                                    }
                                } else {
                                    getStyleClass().remove("resolvedEmail");
                                }
                            } else {    //If email type other than tickets is selected no styling should be shown
                                getStyleClass().remove("unlockedEmail");

                            }
                        } else {
                            Platform.runLater(() -> setText(""));
                            getStyleClass().remove("lockedEmail");
                        }
                    }
                };
                return cell;
            }
        });

        //Right click menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem archiveItem = new MenuItem("Move to Archive");
        archiveItem.setOnAction(t -> {
            Email email = list_emails.getSelectionModel().getSelectedItem();
            List<History> historyList = getSelectEmailHistory(email);
            String statusTime = getStatusTime(historyList);
            History history = getHistory(historyList, statusTime, email);
            String userName = "";
            if (history != null) {
                userName = history.getUsers().getFullName();
            }
            try {
                if (email.getStatus() != null) {
                    if (email.getStatus().equals("LOCKED")) {     //If Email is locked
                        if (userName.equals(user.getFullName())) {      //If Email is locked by YOU
                            RequestHandler.run("ticket/archive/" + email.getCode()).close();
                            try {
                                loadEmails();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {                        //If Email is locked but NOT by you
                            Toast.makeText((Stage) btn.getScene().getWindow(), "You are not allowed to archive this email.");
                        }
                    } else {
                        if (Email_Type == 1) {
                            RequestHandler.run("ticket/archive/" + email.getCode()).close();
                            loadEmails();
                        }
                    }
                } else {    //If Email is not locked
                    if (Email_Type == 2) {
                        RequestHandler.run("general/archive/" + email.getCode()).close();
                    } else {
                        RequestHandler.run("email/archive/" + email.getCode()).close();
                    }

                    try {
                        loadEmails();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        contextMenu.getItems().add(archiveItem);
        if (Email_Type == 2) {
            MenuItem createTicket = new MenuItem("Create Ticket");
            createTicket.setOnAction(t -> {
                Email selectedItem = list_emails.getSelectionModel().getSelectedItem();
                try {
                    RequestHandler.run("general/ticket/" + selectedItem.getCode()).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    loadEmails();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            contextMenu.getItems().add(createTicket);
        }

        if (Email_Type == 3) { //resend email
            MenuItem sendAgain = new MenuItem("Send Again");
            sendAgain.setOnAction(t -> {
                Email selectedItem = list_emails.getSelectionModel().getSelectedItem();
                try {
                    RequestHandler.run("email/sendAgain/" + selectedItem.getCode()).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    loadEmails();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
            contextMenu.getItems().add(sendAgain);
        }
        list_emails.setContextMenu(contextMenu);
        list_emails.setOnContextMenuRequested(event -> event.consume());

        //Display Emails
        loadEmails();
    }

    //help


    private List<Email> checkIfEmailsExist() throws IOException {

        List<Email> emails = null;
        switch (Email_Type) {
            case 1:     //Tickets
                Email.isEmailTypeSent = false;
                emails = RequestHandler.listRequestHandler(RequestHandler.run("ticket/emails?filter=" + Filters.readFromFile() + "&order=" + Filters.ascDesc + "&orderBy=" + Filters.sortBy), Email.class);
                break;
            case 2:     //General
                Email.isEmailTypeSent = false;
                emails = RequestHandler.listRequestHandler(RequestHandler.run("general/emails"), Email.class);

                break;
            case 3:     //Outbox
                Email.isEmailTypeSent = true;
                emails = RequestHandler.listRequestHandler(RequestHandler.run("email/view/0"), Email.class);
                break;
            case 4:     //Sent
                Email.isEmailTypeSent = true;
                emails = RequestHandler.listRequestHandler(RequestHandler.run("email/view/1"), Email.class);

                break;
        }
        if (emails == null) {
            emails = new ArrayList<>();
            Email nEm = new Email();
            nEm.setSubject("Emails Found");
            nEm.setCode(0);
            nEm.setAttachment(new ArrayList<>());
            emails.add(nEm);

            list_emails.setDisable(true);
            enableDisable(1);
        } else {
            list_emails.setDisable(false);
        }

        return emails;
    }

    private void inflateArchive() {
        inflateWindow("Archive", "Archive/archive.fxml");
    }

    private void inflateSearch() {
        inflateWindow("Search", "GlobalSearching/SearchEmail.fxml");
    }

    //OPENING RESPONSE STAGE
    private void inflateEResponse(int i) {
        EResponseController.choice = i;
        String title = "";
        switch (i) {
            case 1:
                title = "New Email";
                break;
            case 2:
                title = "New Ticket";
                break;
        }
        inflateWindow(title, "EResponse/EResponse.fxml");
    }

    //OPENING THE FILTER
    private void inflateFilters() {
        inflateWindow("Filters", "Search/filter.fxml");
    }

    private void inflateWindow(String title, String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Parent root1 = fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle(title);
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.sizeToScene();
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void instantiateEmail() {
        EResponseController.stTo = new ArrayList<>();
        EResponseController.stSubject = "";
        EResponseController.stBody = "";
        EResponseController.stInstance = 'N'; //N for New.
    }

    public FilteredList<Email> filteredList(ObservableList<Email> dataObj) {
        FilteredList<Email> filteredList = new FilteredList<>(dataObj, s -> true);
        return filteredList;
    }

    int ticketNo;


    //Track Down which load Email is Running
    public void loadEmails() throws IOException {
        //making list filterable
//        Email emailNo = (Email) RequestHandler.objectRequestHandler(RequestHandler.run("ticket/getMaxTicketNo"), Email.class);
//        if (emailNo == null) {
//            return;
//        }
//        ticketNo = emailNo.getTicketNo();

        ticketNo = Integer.parseInt(RequestHandler.run("ticket/getMaxTicketNo").body().string());
        FilteredList<Email> filteredList;
        if (Filters.readFromFile().isLockedByMe()) {
            ObservableList<Email> dataObj = FXCollections.observableArrayList(checkIfEmailsExist());
            ObservableList<Email> emails = FXCollections.observableArrayList();

            for (Email email : dataObj) {
                List<String> timestamps = new ArrayList<>();
                List<History> historyList = getSelectEmailHistory(email);
                for (History history : historyList) {
                    timestamps.add(history.getTime());
                }
                Collections.sort(timestamps);
                Collections.reverse(timestamps);
                List<History> histories = new ArrayList<>();
                for (History history : historyList) {
                    if (history.getTime().equals(timestamps.get(0)) && email.getStatus().equals(history.getStatus())) {

                        histories.add(history);
                    }
                }
                for (History history : histories) {
                    if (history.getUsers().getUserCode() == user.getUserCode()) {
                        emails.add(email);
                    }
                }
            }
            filteredList = filteredList(emails);
        } else {
            ObservableList<Email> dataObj = FXCollections.observableArrayList(checkIfEmailsExist());
            filteredList = filteredList(dataObj);
        }


        Email temp = selectedEmail;
        list_emails.setItems(null);
        selectedEmail = temp;   //Because when list_emails is emptied selected email becomes null

        search_txt.textProperty().addListener((observable, oldValue, newValue) -> setSearch(filteredList));

        setSearch(filteredList);
        list_emails.setItems(filteredList);

        if (selectedEmail == null) {
            enableDisable(1);
            imgLoader.setVisible(false);
            return;
        }

        //Reselect email from list
        int index = -1;
        boolean isFound = false;

        for (Email email : list_emails.getItems()) {
            index++;
            if (email.getCode() == selectedEmail.getCode()) {
                isFound = true;
                break;
            }
        }
        if (isFound) {
            list_emails.getSelectionModel().select(index);
            populateDetails(selectedEmail);
        } else {
            enableDisable(1);
        }

        //When Email is marked as solved
        if (this.solvIndex != -1) {
            list_emails.getSelectionModel().select(this.solvIndex);
            this.solvIndex = -1;
        }

        imgLoader.setVisible(false);
    }

    public static void loadEmailsStatic() {      //Load Emails from other controller

        imgLoader.setVisible(true);
        if (Email_Type == 1) {
            Platform.runLater(() -> tickets.fire());
        } else if (Email_Type == 2) {
            Platform.runLater(() -> allMail.fire());
        }
    }

    public void onLock(ActionEvent actionEvent) throws IOException {
        imgLoader.setVisible(true);
        System.out.println("onLock Call");
        RequestHandler.run("ticket/lock?code=" + selectedEmail.getCode() + "&userCode=" + user.getUserCode() + "&status=" + 1).close();
        loadEmailsStatic();

    }


    public int solvIndex = -1;  //To select the next email in the list after solve
    private ESetting eSetting;

    public void onSolv(ActionEvent actionEvent) throws IOException {
        solvIndex = -1;
        Email email = selectedEmail;
        try {
            eSetting = (ESetting) RequestHandler.objectRequestHandler(RequestHandler.run("settings/getSettings"), ESetting.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        solvIndex = list_emails.getSelectionModel().getSelectedIndex();
        if (eSetting.getSolveCheck() == 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to mark this as solved?\n" +
                    "This action cannot be taken back. No response will be issued.",
                    ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {

                List<History> historyList = getSelectEmailHistory(email);
                String statusTime = getStatusTime(historyList);

                History history = getHistory(historyList, statusTime, email);

                if (history.getStatus().equals("RESOLVED") && isAdmin.equals("Admin") && selectedEmail.getIsAllocatedBy() == user.getUserCode()) {
                    RequestHandler.run("ticket/solve?code=" + selectedEmail.getCode() + "&userCode=" + history.getUsers().getUserCode() + "&status=" + 2 + "&checkSend=" + true + "&solvedBody=" + "").close();
                } else if (history.getUsers().getUserCode() == user.getUserCode() && history.getStatus().equals("LOCKED")) {
                    RequestHandler.run("ticket/solve?code=" + selectedEmail.getCode() + "&userCode=" + user.getUserCode() + "&status=" + 2 + "&checkSend=" + true + "&solvedBody=" + "").close();
                }


                loadEmailsStatic();
            } else {
                return;
            }
        } else {
            inflateWindow("Confirmation", "SolvedDialog/SolvedDialog.fxml");
        }
    }

    public void onUnlock(ActionEvent actionEvent) throws IOException {
        imgLoader.setVisible(true);
        Email email = selectedEmail;
        List<History> historyList = getSelectEmailHistory(email);
        String statusTime = getStatusTime(historyList);

        History history = getHistory(historyList, statusTime, email);
//        user code
        if (email.getIsAllocatedBy() == user.getUserCode()) {
            RequestHandler.run("ticket/unAllocate?code=" + email.getCode() + "&userCode=" + user.getUserCode() + "&status=" + 0).close();
        } else if (user.getUserCode() == history.getUsers().getUserCode()) {
            RequestHandler.run("ticket/unlock?code=" + email.getCode() + "&userCode=" + user.getUserCode() + "&status=" + 0).close();
        }

        loadEmailsStatic();
    }

    static JFXButton allMail = new JFXButton("General"),
            tickets = new JFXButton("Tickets"),
            outbox = new JFXButton("Outbox"),
            sentMail = new JFXButton("Sent");

    private void populateCategoryBoxes() {
        prepBtn(tickets);

        tickets.setOnAction(event -> {
            try {
                changeEmailType(1, tickets);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        prepBtn(allMail);
        allMail.setOnAction(event -> {
            try {
                changeEmailType(2, allMail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        prepBtn(outbox);
        outbox.setOnAction(event -> {
            try {
                changeEmailType(3, outbox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        prepBtn(sentMail);
        sentMail.setOnAction(event -> {

            try {
                changeEmailType(4, sentMail);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        category_box.getChildren().addAll(tickets, allMail, outbox, sentMail);

        switch (Email_Type) {
            case 1:
                tickets.fire();
                break;
            case 2:
                allMail.fire();
                break;
            case 3:
                sentMail.fire();
                break;
            case 4:
                outbox.fire();
                break;
        }
    }

    //    String from;
    List<String> from;
    public static String path;
    public static final String temp = FileHelper.readFilePath();

    public static History getHistory(List<History> historyList, String timestamps, Email email) {
        History hist = null;
        if (Email_Type == 1) {
            for (History history : historyList) {
                if (history.getTime().equals(timestamps) && email.getStatus().equals(history.getStatus())) {
                    hist = history;
                }
            }
        }
        return hist;
    }

    public static String getStatusTime(List<History> historyList) {
        String statusTime = "";
        List<String> timestamps = new ArrayList<>();
        if (Email_Type == 1) {
            if (!historyList.isEmpty() && historyList != null) {
                for (History history : historyList) {
                    timestamps.add(history.getTime());
                }
                Collections.sort(timestamps);
                Collections.reverse(timestamps);
                statusTime = timestamps.get(0);
            }
        } else {

        }

        return statusTime;
    }

    public static List<History> getSelectEmailHistory(Email email) {
        List<History> history = null;
        try {
            history = (List<History>) RequestHandler.listRequestHandler(RequestHandler.run("history/view/" + email.getCode()), History.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    public String unLockedBy(List<History> historyList, String time, Email email) {
        String statusName = "";
        if (Email_Type == 1) {
            for (History history : historyList) {
                if (history.getTime().equals(time) && email.getStatus().equals(history.getStatus())) {
                    statusName = history.getStatus();
                }
            }
        }
        return statusName;
    }

    public static TimerTask timerTaskNewEmail;

    public void timer() {
        Timer timer = new Timer();
        if (timer != null) {
            synchronized (timer) {
                if (timerTaskNewEmail != null) {
                    timerTaskNewEmail.cancel();
                    timerTaskNewEmail = null;
                }
                timerTaskNewEmail = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if (Email_Type == 1) {
                                if (ticketNo != 0) {
                                    if (RequestHandler.checkUpdate("ticket/check/" + ticketNo)) {
                                        List<Email> emails = RequestHandler.listRequestHandler(RequestHandler.run("ticket/getNewEmails/" + ticketNo), Email.class);
                                        trayHelper th = new trayHelper();
                                        for (Email email : emails){
                                            th.displayNotification("New Email", "Email Received From: " + email.getFromAddress() +"\nSubject: "+email.getSubject());
                                        }

                                        Collections.reverse(emails);
                                        ObservableList<Email> dataObj = FXCollections.observableArrayList(emails);

                                        for (Email email : list_emailsF.getItems()) {

                                            dataObj.add(email);
                                        }
                                        list_emails.setItems(filteredList(dataObj));
                                        ticketNo = list_emails.getItems().get(0).getTicketNo();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.schedule(timerTaskNewEmail, 3000, 1000);
            }
        }


    }

    TimerTask timerTask;

    private void checkStatus() {
        Timer timer = new Timer();
        if (timer != null) {
            synchronized (timer) {
                if (timerTask != null) {
                    timerTask.cancel();
                    timerTask = null;
                }
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (Email_Type == 1) {
                            if (selectedEmail != null) {
                                try {
                                    if (RequestHandler.checkUpdate("ticket/statusCheck?code=" + selectedEmail.getCode() + "&status=" + selectedEmail.getStatus())) {
                                        Email email = (Email) RequestHandler.objectRequestHandler(RequestHandler.run("ticket/updatedStatus/" + selectedEmail.getCode()), Email.class);
                                        loadEmailsStatic();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                timer.schedule(timerTask, 3000, 1000);
            }
        }
    }


    public void populateDetails(Email email) {
        imgLoader.setVisible(true);
        new Thread(() -> Platform.runLater(() -> {

            try {
                if (Email_Type == 1) {
                    label_ticket.setText(String.valueOf(email.getTicketNo()));
                } else {
                    label_ticket.setText(String.valueOf(email.getCode()));
                }

            } catch (NullPointerException e) {
                return;
            }
            if (Email_Type == 1) {
                checkStatus();
            }

            String allocatedUserName = "";
            if (email.getIsAllocatedBy() != 0) {
                try {
                    allocatedUserName = String.valueOf(RequestHandler.run("users/getUserName/" + email.getIsAllocatedBy()).body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            List<History> historyList = getSelectEmailHistory(email);

            String statusTime = getStatusTime(historyList);
            String displayTime = convertFormat(getStatusTime(historyList));

            History history = getHistory(historyList, statusTime, email);

            String userName = "";
            String status = "";
            int userId = 0;
            if (history != null) {
                userName = history.getUsers().getFullName();
                status = history.getStatus();
                userId = history.getUsers().getUserCode();
            }

            String timestamp = email.getTimestamp().toString();
            label_time.setText(timestamp);

            vbox_from.getChildren().clear();  //Clearing
            vbox_from.setSpacing(2.0);
            vbox_cc.getChildren().clear();    //Both VBoxes
            vbox_cc.setSpacing(2.0);
            label_from.setText("From:");
            if (Email_Type == 1) {
                selectUser.getItems().clear();
                if (email.getIsAllocatedBy() == 0) {
                    selectUser.setPromptText("Allocate");
                } else {
                    selectUser.setPromptText("Allocated");
                }

                selectUser.setDisable(false);

                selectUser.getItems().addAll(usersList);
                from = email.getFromAddress();
                String statusName = unLockedBy(historyList, statusTime, email);
                if (statusName.equals("LOCKED") || statusName.equals("ALLOCATED") || statusName.equals("RESOLVED")) {
                    title_locked.setText("Locked By: ");
                    /* ADDED */
                    if (email.getIsAllocatedBy() == 0) {
                        label_locked.setText(userName);
                    } else {
                        label_locked.setText(userName + " ( " + allocatedUserName + " )");
                    }
                } else {
                    title_locked.setText("UnLocked By: ");
                    label_locked.setText("");
                }

                // 6 is for manually created
                if (email.getUsers().getUserCode() != 6) {
                    label_created.setVisible(true);
                    title_created.setVisible(true);
                    label_created.setText(email.getUsers().getFullName());
                } else {
                    label_created.setVisible(false);
                    title_created.setVisible(false);
                }

            } else if (Email_Type == 2) {
                from = email.getFromAddress();
                label_created.setVisible(false);
                title_created.setVisible(false);
                label_locked.setVisible(false);
                title_locked.setVisible(false);
                tittle_solveTime.setText("");
                label_solveTime.setText("");
            } else if (Email_Type == 4 || Email_Type == 3) {
                label_from.setText("To:");
                from = email.getToAddress();
                label_created.setVisible(false);
                title_created.setVisible(false);
                title_locked.setVisible(true);
                label_locked.setVisible(true);
                title_locked.setText("Sent By User: ");
                label_locked.setText(email.getUsers().getFullName());
                tittle_solveTime.setText("");
                label_solveTime.setText("");
            }

            if (!from.isEmpty()) {
                for (String f : from) {
                    try {
                        if (!f.equals("")) {
                            Label label = new Label(f);
                            label.setPadding(new Insets(2, 2, 2, 5));
                            label.getStyleClass().add("moduleDetails");
                            vbox_from.getChildren().add(label);
                        }
                    } catch (NullPointerException ex) {
                    }
                }
            }
            if (!email.getCcAddress().isEmpty()) {
                for (String c : email.getCcAddress()) {
                    try {
                        if (!c.equals("")) {
                            Label label = new Label(c);
                            label.setPadding(new Insets(2, 5, 2, 5));
                            label.getStyleClass().add("moduleDetails");
                            vbox_cc.getChildren().add(label);
                        }
                    } catch (NullPointerException ex) {
                        //Because null is saved
                    }
                }
            }

            txt_subject.setText(email.getSubject());

            anchor_details.setVisible(true);


            //----Attachments
            combo_attach.getItems().clear();

            if (email.getAttachment() == null || email.getAttachment().isEmpty()) {
                combo_attach.setPromptText("No Attachments");
                combo_attach.setDisable(true);
            } else if (!email.getAttachment().isEmpty()) {
                combo_attach.setPromptText("Open Attachment");

                combo_attach.setDisable(false);
                List<FileDev> attFiles = new ArrayList<>();
                List<String> fileNamesString = new ArrayList<>();
                for (String c : email.getAttachment()) {
                    FileDev file = new FileDev(c);
                    if (!c.equals("")) {
                        path = file.getAbsolutePath();
                        attFiles.add(new FileDev(temp + "\\" + file.getName()));
                        fileNamesString.add(file.getName());
                    }
                }
                if (!fileNamesString.isEmpty()) {

                    StringBuilder sb = new StringBuilder();

                    int i = 0;
                    while (i < fileNamesString.size() - 1) {
                        sb.append(fileNamesString.get(i));
                        sb.append(",");
                        i++;
                    }
                    sb.append(fileNamesString.get(i));
                    String fileNames = sb.toString(); // only Names of file with comma
                    int index = path.lastIndexOf('\\');
                    String actualPath = path.substring(0, index) + "\\"; //actual path without fileName
                    actualPath = actualPath.replace("\\", "/"); //replace slash sign for proper path
                    String finalActualPath = actualPath;
                    new Thread(() -> Platform.runLater(() -> {
                        try {
                            ZipFileUtility zipFileUtility = new ZipFileUtility();
                            System.out.println("name : "+ fileNames + " path : "+ finalActualPath + " temp : "+ temp);
                            zipFileUtility.unzip(RequestHandler.downloadZipFile("ticket/download/" , fileNames   ,"?path="+ finalActualPath, temp + "temp.zip"), temp);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    })).start();
                    combo_attach.getItems().addAll(attFiles);
                }
            }

            //----Ebody
            anchor_body.getChildren().clear();

            WebView eBody = new WebView();

            eBody.getEngine().loadContent(email.getBody());

            eBody.getEngine().setUserStyleSheetLocation("data:,body { font: 15px Calibri; }");
            eBody.setPrefSize(anchor_body.getWidth(), anchor_body.getHeight());
            anchor_body.getChildren().add(eBody);
            AnchorPane.setLeftAnchor(eBody, 0.0);
            AnchorPane.setRightAnchor(eBody, 0.0);
            AnchorPane.setTopAnchor(eBody, 0.0);
            AnchorPane.setBottomAnchor(eBody, 0.0);
            if (!anchor_body.isVisible()) {
                anchor_body.setVisible(true);
            }

            if (Email_Type != 1) {  //If Email Type is General or Sent
                btn_lock.setVisible(false);
                btn_unlock.setVisible(false);
                btn_solv.setVisible(false);
                btn_resolve.setVisible(false);
                selectUser.setVisible(false);
                imgLoader.setVisible(false);
                return;
            } else {
                btn_lock.setVisible(true);
                btn_unlock.setVisible(true);
                btn_solv.setVisible(true);
                btn_resolve.setVisible(true);
                selectUser.setVisible(true);
                imgLoader.setVisible(false);
            }
            if (isAdmin.equals("Admin")) {
                selectUser.setVisible(true);  // combo select user visible for allocation
            } else {
                selectUser.setVisible(false);
            }

            //Locked/Solved Label
            //Buttons
            if (email.getStatus().equals("SOLVED")) {    //If Email is solved disable all buttons
                String display = "";

                if (userName.equals("")) {
                    display = "";
                } else {
                    display = userName;
                }
                label_locked.setText(display);
                label_solveTime.setText(displayTime);
                enableDisable(2);
            } else if (email.getIsAllocatedBy() != 0) {
                if (!status.equals("RESOLVED") && isAdmin.equals("Admin") && email.getIsAllocatedBy() == user.getUserCode()) { //email is allocated but not resolve it can be unlocked by same admin
                    label_solveTime.setText(displayTime);
                    enableDisable(10);
                } else if (status.equals("RESOLVED") && isAdmin.equals("Admin") && email.getIsAllocatedBy() == user.getUserCode()) {  //  email is allocated and resolved admin can solve
                    label_solveTime.setText(displayTime);
                    enableDisable(8);
                } else if (status.equals("RESOLVED")) { // email is allocated and resolve by user resolved disable and other button is invisible
                    label_solveTime.setText(displayTime);
                    enableDisable(7);
                } else if (userId == user.getUserCode() && status.equals("ALLOCATED")) {  // email is allocated and user is same and the resolve button is visible
                    label_solveTime.setText(displayTime);
                    enableDisable(6);
                } else if (userId != user.getUserCode() && status.equals("ALLOCATED")) {  // email is allocated and user is same and the resolve button is invisible
                    tittle_solveTime.setText("Allocate Time: ");
                    label_solveTime.setText(displayTime);
                    enableDisable(4);
                } else {
                    tittle_solveTime.setText("Locked Time: ");
                    enableDisable(4); // email is allocated
                }
            } else {    //If Email is not solved
                if (email.getStatus().equals("LOCKED")) {     //If Email is locked
//                    change name
                    if (userId == user.getUserCode()) {      //If Email is locked by YOU // it will modify when user start
                        label_solveTime.setText(displayTime);
                        enableDisable(3);
                    } else {
                        tittle_solveTime.setText("Locked Time: ");
                        label_solveTime.setText(displayTime);//If Email is locked but NOT by you
                        enableDisable(4);
                    }
                } else {                            //If Email is not locked
                    if (isAdmin.equals("Admin")) { // email is locked by admin
                        enableDisable(9);
                    } else {
                        enableDisable(5); // email is locked by user
                    }
                }
            }

            imgLoader.setVisible(false);
        })).start();
    }



    private static WritableImage convertToJavaFXImage(byte[] raw, final double width, final double height) {
        WritableImage image = new WritableImage(Integer.parseInt(String.valueOf(width)), Integer.parseInt(String.valueOf(height)));
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            Logger.getLogger(EmailDashController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }

    private JFXComboBox sortBy, ascDesc;
    private JFXCheckBox solved, unSolved, locked, unLocked, lockedByMe, hideReminders, archived, isResolved;

    private int filterChoice = 0; //1 == tickets 2 == general

    private void populateFilters() {
        vbox_filter.setSpacing(10);

        Filters filter = Filters.readFromFile();

        //Sort By (ComboBox)
        HBox sort = new HBox();
        sortBy = new JFXComboBox();
        setUpCombo(sortBy, "Sorted By", new String[]{"Tickets", "From", "Subject"});
        sortBy.setOnAction(event -> saveFilters());
        sortBy.getSelectionModel().select(filter.getSortBy());

        ascDesc = new JFXComboBox();
        setUpCombo(ascDesc, "Asc/Desc", new String[]{"Asc", "Desc"});
        ascDesc.setOnAction(event -> saveFilters());
        ascDesc.getSelectionModel().select(filter.getAscDesc());
        sort.getChildren().addAll(sortBy, ascDesc);
        vbox_filter.getChildren().add(sort);

        //Checkboxes
        solved = new JFXCheckBox("Solved");
        solved.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        setUpCheck(solved);
        solved.setSelected(filter.isSolved());

        unSolved = new JFXCheckBox("Unsolved");
        unSolved.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        setUpCheck(unSolved);
        unSolved.setSelected(filter.isUnsolved());

        locked = new JFXCheckBox("Locked");
        locked.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        setUpCheck(locked);
        locked.setSelected(filter.isLocked());

        unLocked = new JFXCheckBox("Unlocked");
        setUpCheck(unLocked);
        unLocked.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        unLocked.setSelected(filter.isUnlocked());

        lockedByMe = new JFXCheckBox("Locked By Me");
        setUpCheck(lockedByMe);
        lockedByMe.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        lockedByMe.setSelected(filter.isLockedByMe());

        hideReminders = new JFXCheckBox("Hide Reminders");
        setUpCheck(hideReminders);
        hideReminders.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        hideReminders.setSelected(filter.isHideReminders());

        archived = new JFXCheckBox("Archived");
        setUpCheck(archived);
        archived.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        archived.setSelected(filter.isArchived());

        isResolved = new JFXCheckBox("Pending Emails");
        setUpCheck(isResolved);
        isResolved.selectedProperty().addListener((observable, oldValue, newValue) -> saveFilters());
        isResolved.setSelected(filter.isResolve());
    }

    private void populateMenuBar() {
        Menu newMenu = new Menu("New");
        MenuItem newEmail = new MenuItem("New Email");
        newEmail.setOnAction(event -> {
            instantiateEmail();
            inflateEResponse(1);
        });
        newMenu.getItems().add(newEmail);
        MenuItem newTicket = new MenuItem("New Ticket");
        newTicket.setOnAction(event -> {
            instantiateEmail();
            inflateEResponse(2);
        });
        newMenu.getItems().add(newTicket);
        menu_bar.getMenus().add(newMenu);

        Menu edit = new Menu("Edit");
        MenuItem reload = new MenuItem("Refresh");

        reload.setOnAction(event -> {
            try {
                loadEmails();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        edit.getItems().add(reload);

        MenuItem filter = new MenuItem("Filters");
        filter.setOnAction(event -> inflateFilters());
        edit.getItems().add(filter);

        MenuItem archive = new MenuItem("Move to Archive");
        archive.setOnAction(event -> inflateArchive());
        edit.getItems().add(archive);

        MenuItem search = new MenuItem("Email Search");
        search.setOnAction(event -> inflateSearch());
        edit.getItems().add(search);

        menu_bar.getMenus().add(edit);
    }

    private void prepBtn(JFXButton btn) {
        btn.setMinHeight(50);
        btn.setMinWidth(60);
        btn.getStyleClass().add("btnMenuBox");
        btn.setAlignment(Pos.CENTER_LEFT);
    }



    private void saveFilters() {
//        imgLoader.setVisible(true);
        try {
            Filters filter = new Filters();
            filter.setSortBy(sortBy.getSelectionModel().getSelectedItem().toString());
            filter.setAscDesc(ascDesc.getSelectionModel().getSelectedItem().toString());
            filter.setSolved(solved.isSelected());
            filter.setUnsolved(unSolved.isSelected());
            filter.setLocked(locked.isSelected());
            filter.setUnlocked(unLocked.isSelected());
            filter.setLockedByMe(lockedByMe.isSelected());
            filter.setHideReminders(hideReminders.isSelected());
            filter.setArchived(archived.isSelected());
            filter.setResolve(isResolved.isSelected());
            filter.writeToFile();

            loadEmailsStatic();
        } catch (NullPointerException e) {
            System.out.println("x--x");
        }
    }

    private void setSearch(FilteredList<Email> filteredList) {
        enableDisable(1);
        String filter = search_txt.getText();
        if (filter == null || filter.length() == 0) {
            filteredList.setPredicate(s -> true);
        } else {
            filteredList.setPredicate(s -> s.toString().toUpperCase().contains(filter.toUpperCase()));
        }
        label_count.setText("Displaying " + filteredList.size() + " Emails");
    }

    private void setUpCheck(JFXCheckBox combo) {
        combo.getStyleClass().add("check_box_style");
        VBox.setMargin(combo, new Insets(5, 5, 0, 10));
        vbox_filter.getChildren().add(combo);
    }

    private void setUpCombo(JFXComboBox combo, String prompt, String[] options) {
        combo.setPromptText(prompt);
        combo.setMinWidth((vbox_filter.getPrefWidth() * 0.4) - 5);
        combo.getItems().addAll(options);
        combo.getStyleClass().add("check_box_style");
        HBox.setMargin(combo, new Insets(0, 5, 0, 5));
    }

    public void enableDisable(int i) {

        if (i == 1) {   //Disable Everything
            anchor_body.setVisible(false);
            anchor_details.setVisible(false);

            imgLoader.setVisible(false);
        } else if (i == 2) {    //If email is solved
            title_locked.setText("Solved By: ");
            tittle_solveTime.setText("Solved Time: ");
            btn_lock.setDisable(true);
            btn_lock.setVisible(true);
            btn_unlock.setDisable(true);
            btn_unlock.setVisible(false);
            btn_solv.setDisable(true);
            btn_resolve.setVisible(false);
            selectUser.setVisible(false);
            combo_respond.setDisable(true);
        } else if (i == 3) {    //If email is locked by you
            tittle_solveTime.setText("Locked Time: ");
            btn_lock.setVisible(false);
            btn_unlock.setVisible(true);
            btn_unlock.setDisable(false);
            btn_solv.setDisable(false);     //Only someone who has locked the email can solve it.
            btn_resolve.setVisible(false); // new
            selectUser.setDisable(true);
            combo_respond.setDisable(false);
        } else if (i == 4) {    //If email is locked but not by you

            btn_unlock.setVisible(false);
            btn_lock.setVisible(true);
            btn_lock.setDisable(true);
            btn_solv.setDisable(true);
            btn_resolve.setVisible(false);
            selectUser.setVisible(false); // new
            combo_respond.setDisable(true);
        } else if (i == 5) {    //If email is not locked
            label_solveTime.setText("");
            tittle_solveTime.setText("");
            btn_lock.setDisable(false);
            btn_lock.setVisible(true);
            btn_unlock.setVisible(false);
            btn_solv.setDisable(true);
            btn_resolve.setVisible(false); // new
            combo_respond.setDisable(true);
        } else if (i == 6) {    //Resolve button display for same user
            tittle_solveTime.setText("Allocate Time: ");
            btn_lock.setDisable(false);
            btn_lock.setVisible(false);
            btn_unlock.setVisible(false);
            btn_solv.setVisible(false);
            btn_solv.setDisable(true);
            selectUser.setVisible(false);
            btn_resolve.setVisible(true);
            btn_resolve.setDisable(false);
            combo_respond.setDisable(true);
        } else if (i == 7) {    // email is resolved the user end expect admin the resolved button is disable
            tittle_solveTime.setText("Resolved Time: ");
            btn_lock.setDisable(false);
            btn_lock.setVisible(false);
            btn_unlock.setVisible(false);
            btn_solv.setDisable(false);
            btn_solv.setVisible(false);
            selectUser.setVisible(false);
            btn_resolve.setVisible(true);
            btn_resolve.setDisable(true);
            combo_respond.setDisable(true);
        } else if (i == 8) {    // email solved button enable for admin
            tittle_solveTime.setText("Resolved Time: ");
            btn_lock.setDisable(false);
            btn_lock.setVisible(false);
            btn_unlock.setVisible(false);
            btn_solv.setDisable(false);
            btn_solv.setVisible(true);
            selectUser.setVisible(false);
            btn_resolve.setVisible(false);
            btn_resolve.setDisable(true);
            combo_respond.setDisable(true);
        } else if (i == 9) { // email is not locked and display for admin select user and lock button
            label_solveTime.setText("");
            tittle_solveTime.setText("");
            btn_lock.setDisable(false);
            btn_lock.setVisible(true);
            btn_unlock.setVisible(false);
            btn_solv.setDisable(true);
            selectUser.setDisable(false);
            btn_resolve.setVisible(false); // new
            combo_respond.setDisable(true);
        } else if (i == 10) { //if email is locked and it can be unlocked by same admin
            tittle_solveTime.setText("Allocate Time: ");
            btn_lock.setVisible(false);
            btn_unlock.setVisible(true);
            btn_unlock.setDisable(false);
            btn_solv.setVisible(true);
            btn_solv.setDisable(true);     //
            btn_resolve.setVisible(false); // new
            combo_respond.setDisable(false);
        }
    }

    public void onResolve(ActionEvent actionEvent) throws IOException {
        Email email = selectedEmail;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to mark this as Resolve?\n" +
                "This action cannot be taken back.",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            RequestHandler.run("ticket/resolve?code=" + email.getCode() + "&userCode=" + user.getUserCode() + "&status=" + 4).close();
            loadEmailsStatic();
        } else {
            return;
        }
    }

}
