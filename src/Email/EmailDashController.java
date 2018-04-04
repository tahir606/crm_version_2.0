package Email;

import Email.EResponse.EResponseController;
import JCode.*;
import JSockets.JClient;
import JSockets.JServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dashboard.dController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import objects.Email;
import objects.Users;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EmailDashController implements Initializable {

    @FXML
    private AnchorPane anchor_body;
    @FXML
    private AnchorPane anchor_details;
    @FXML
    private Label label_ticket;
    @FXML
    private Label label_time;
    @FXML
    private Label label_locked;
    @FXML
    private Label label_from;
    @FXML
    private Label title_locked;
    @FXML
    private TextArea txt_subject;
    @FXML
    private JFXButton btn_lock;
    @FXML
    private JFXButton btn_solv;
    @FXML
    private JFXButton btn_unlock;
    @FXML
    private JFXComboBox<String> combo_respond;
    @FXML
    private BorderPane border_email;
    @FXML
    private VBox category_box;
    @FXML
    private VBox vbox_from;
    @FXML
    private VBox vbox_cc;
    @FXML
    private VBox vbox_bcc;
    @FXML
    private JFXComboBox<FileDev> combo_attach;
    @FXML
    private HBox menu_email;
    @FXML
    private MenuBar menu_bar;

    @FXML
    private JFXButton menu_reload;

    @FXML
//    private StyledListView<Email> list_emails;
    private ListView<Email> list_emails;

    private mySqlConn sql;
    private fileHelper fHelper;
    private Users user;
    private emailControl eCon;

    private static ImageView imgLoader = dController.img_load;
    public static char ReplyForward;         //Where R is for reply and F is for Forward
    public static String efrom, subject, body;

    private static Email selectedEmail = null;

    public static ListView<Email> list_emailsF;
    private static AnchorPane anchor_detailsF;
    private static AnchorPane anchor_bodyF;

    public static String[] EMAILS_LIST;
    public static int Email_Type = 1;

    public EmailDashController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoader.setVisible(true);

        list_emailsF = list_emails;
        anchor_detailsF = anchor_details;
        anchor_bodyF = anchor_body;

        anchor_details.setVisible(false);

        sql = new mySqlConn();
        fHelper = new fileHelper();
        eCon = new emailControl();

        user = fHelper.ReadUserDetails();

        populateCategoryBoxes();

        populateMenuBar();

        //Populating List
        //Creates the changes in the Details Section
        list_emails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedEmail = newValue;
            populateDetails(selectedEmail);
        });

        //Attaching listener to attaching combo box
        combo_attach.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;
            try {
                combo_attach.valueProperty().set(null);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(newValue);
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

            efrom = sEmail.getFromAddress()[0].toString();
            subject = "RE: " + sEmail.getSubject();

            if (newValue.equals("Reply")) {
                ReplyForward = 'R';
                body = "\n\n\n" + "On " + sEmail.getTimeFormatted() + ", " + sEmail.getFromAddress
                        ()[0].toString() + " wrote:\n" + sEmail.getBody();
            } else if (newValue.equals("Forward")) {
                ReplyForward = 'F';
                body = sEmail.getBody();
            }
            inflateEResponse(1);
            combo_respond.getSelectionModel().select(0);
        });
//        imgLoader.setVisible(false);

        pullingEmails();

    }

    private void populateMenuBar() {
//        new Thread(() -> {
        //1) Populating Top Menu
        //  a) Creating and adding listeners to Buttons
//                JFXButton email = new JFXButton("New Email");
//                email.setMinSize(100, menu_email.getHeight());
//                email.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/newmail.png"))));
//                email.getStyleClass().add("btnMenu");
//                email.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
//                    efrom = "";
//                    subject = "";
//                    body = "";
//                    ReplyForward = 'N'; //N for New.
//
//                    inflateEResponse();
//                });

        Menu newMenu = new Menu("New");
//            newMenu.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/newmail.png"))));
        MenuItem newEmail = new MenuItem("New Email");
        newEmail.setOnAction(event -> {
            efrom = "";
            subject = "";
            body = "";
            ReplyForward = 'N'; //N for New.

            inflateEResponse(1);
        });
        newMenu.getItems().add(newEmail);
        MenuItem newTicket = new MenuItem("New Ticket");
        newTicket.setOnAction(event -> {
            efrom = "";
            subject = "";
            body = "";
            ReplyForward = 'N'; //N for New.

            inflateEResponse(2);
        });
        newMenu.getItems().add(newTicket);

        menu_bar.getMenus().add(newMenu);

//
        Menu edit = new Menu("Edit");

        MenuItem reload = new MenuItem("Refresh");
//            reload.setMinSize(100, menu_email.getHeight());
//            reload.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/refresh.png"))));
//            reload.getStyleClass().add("btnMenu");
//            reload.setOnAction(event -> loadEmails());
        reload.setOnAction(event -> loadEmails());

        edit.getItems().add(reload);

        MenuItem filter = new MenuItem("Filters");
//            filter.setMinSize(100, menu_email.getHeight());
//            filter.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/filter.png"))));
//            filter.getStyleClass().add("btnMenu");
//            filter.setOnAction(event -> inflateFilters());
        filter.setOnAction(event -> inflateFilters());

        edit.getItems().add(filter);

        MenuItem archive = new MenuItem("Move to Archive");
//            archive.setMinSize(1, menu_email.getHeight());
//            archive.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/archive.png"))));
//            archive.getStyleClass().add("btnMenu");
        archive.setOnAction(event -> inflateArchive());

        edit.getItems().add(archive);

        menu_bar.getMenus().add(edit);
//
//        }).start();

    }


    static JFXButton allMail = new JFXButton("General");
    static JFXButton tickets = new JFXButton("Tickets");
    static JFXButton sentMail = new JFXButton("Sent");

    private void populateCategoryBoxes() {

        tickets.setMinHeight(50);
        tickets.setMinWidth(60);
        tickets.getStyleClass().add("btnMenuBox");
        tickets.setAlignment(Pos.CENTER_LEFT);
        tickets.setOnAction(event -> changeEmailType(1, tickets));

        allMail.setMinHeight(50);
        allMail.setMinWidth(60);
        allMail.getStyleClass().add("btnMenuBox");
        allMail.setAlignment(Pos.CENTER_LEFT);
        allMail.setOnAction(event -> changeEmailType(2, allMail));

        sentMail.setMinHeight(50);
        sentMail.setMinWidth(60);
        sentMail.setAlignment(Pos.CENTER_LEFT);
        sentMail.getStyleClass().add("btnMenuBox");
        sentMail.setOnAction(event -> changeEmailType(3, sentMail));

        category_box.getChildren().addAll(tickets, allMail, sentMail);

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
        }
    }

    private void changeEmailType(int type, JFXButton btn) {

        tickets.getStyleClass().remove("btnMenuBoxPressed");
        allMail.getStyleClass().remove("btnMenuBoxPressed");
        sentMail.getStyleClass().remove("btnMenuBoxPressed");

        btn.getStyleClass().add("btnMenuBoxPressed");

        if (type != Email_Type) {
            Email_Type = type;
            selectedEmail = null;
        }

        if (type != 1) {
            combo_respond.setDisable(false);
        }

        loadEmails();

        list_emails.setCellFactory(new Callback<ListView<Email>, ListCell<Email>>() {
            @Override
            public ListCell<Email> call(ListView<Email> param) {

                ListCell<Email> cell = new ListCell<Email>() {
                    @Override
                    protected void updateItem(Email item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toString());
                            if (item.getLockd() == 0){
                                if (!getStyleClass().contains("unlockedEmail")) {
                                    getStyleClass().add("unlockedEmail");
                                }

                            } else {
                                getStyleClass().remove("unlockedEmail");
                            }
                        } else
                            setText("");
                    }
                };

                return cell;
            }
        });

        //Right click menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Move to Archive");
        editItem.setOnAction(t -> {
            Email selectedItem = list_emails.getSelectionModel().getSelectedItem();
            sql.ArchiveEmail(Email_Type, " EMNO = " + selectedItem.getEmailNo());
            loadEmails();
        });
        contextMenu.getItems().add(editItem);
        if (Email_Type == 2) {
            MenuItem createTicket = new MenuItem("Create Ticket");
            createTicket.setOnAction(t -> {
                Email selectedItem = list_emails.getSelectionModel().getSelectedItem();
                System.out.println(selectedItem);
                sql.insertEmailManual(selectedItem);
                sql.ArchiveEmail(Email_Type, " EMNO = " + selectedItem.getEmailNo());
                loadEmails();
            });
            contextMenu.getItems().add(createTicket);
        }
        list_emails.setContextMenu(contextMenu);
        list_emails.setOnContextMenuRequested(event -> event.consume());
    }

    //OPENING RESPONSE STAGE
    private void inflateEResponse(int i) {
        try {
            EResponseController.choice = i;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EResponse/EResponse.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.setTitle("New Email");
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPENING THE FILTER
    private void inflateFilters() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Search/filter.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.initStyle(StageStyle.UTILITY);
            stage2.setTitle("Filters");
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //OPENING THE Archive
    private void inflateArchive() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Archive/archive.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.initStyle(StageStyle.UTILITY);
            stage2.setTitle("Archive");
            stage2.setScene(new Scene(root1));
            trayHelper tray = new trayHelper();
            tray.createIcon(stage2);
            Platform.setImplicitExit(true);
            stage2.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEmails() {
        Email temp = selectedEmail;
        list_emails.getItems().clear();
        selectedEmail = temp;
        list_emails.getItems().addAll(checkIfEmailsExist());

        if (selectedEmail == null) {
            anchor_body.setVisible(false);
            anchor_details.setVisible(false);
            imgLoader.setVisible(false);
            return;
        }

        int index = -1;
        for (Email email : list_emails.getItems()) {
            index++;
            if (email.getEmailNo() == selectedEmail.getEmailNo()) {
                break;
            }
        }
        list_emails.getSelectionModel().select(index);
    }

    public static void loadEmailsStatic() {      //Load Emails from other controller

        imgLoader.setVisible(true);

        if (Email_Type == 1) {
            Platform.runLater(() -> tickets.fire());
        } else if (Email_Type == 2) {
            Platform.runLater(() -> allMail.fire());
        }

        imgLoader.setVisible(false);
    }


    private List<Email> checkIfEmailsExist() {

        List<Email> emails = null;

        switch (Email_Type) {
            case 1:     //Tickets
                emails = sql.readAllEmails(fHelper.ReadFilter());
                break;
            case 2:     //General
                emails = sql.readAllEmailsGeneral(" WHERE FREZE = 0");
                break;
            case 3:     //Sent
                emails = sql.readAllEmailsSent(null);
                break;
        }

        if (emails == null) {
            emails = new ArrayList<>();
            Email nEm = new Email();
            nEm.setSubject("Emails Found");
            nEm.setEmailNo(0);
            nEm.setFromAddress(new Address[]{new InternetAddress()});
            nEm.setAttch("");
            emails.add(nEm);

            list_emails.setDisable(true);
            anchor_details.setVisible(false);
            anchor_body.setVisible(false);

        } else {
            list_emails.setDisable(false);
        }

        return emails;
    }

    Address[] from, cc;

    private void populateDetails(Email email) {
        imgLoader.setVisible(true);
        new Thread(() -> Platform.runLater(() -> {
            try {
                label_ticket.setText(String.valueOf(email.getEmailNo()));
            } catch (NullPointerException e) {
                return;
            }

            label_time.setText(email.getTimeFormatted());

            //----Emails
            vbox_from.getChildren().clear();  //Clearing
            vbox_from.setSpacing(2.0);
            vbox_cc.getChildren().clear();    //Both VBoxes
            vbox_cc.setSpacing(2.0);

            if (Email_Type == 1) {
                label_from.setText("From:");
                from = email.getFromAddress();
                title_locked.setText("Locked By: ");
                label_locked.setText(email.getLockedByName());
            } else if (Email_Type == 2) {
                label_from.setText("From:");
                from = email.getFromAddress();
            } else if (Email_Type == 3) {
                label_from.setText("To:");
                from = email.getToAddress();
                title_locked.setText("Sent By User: ");
                label_locked.setText(email.getUser());
            }

            cc = email.getCcAddress();
            for (Address f : from) {
                try {
                    Label label = new Label(f.toString());
                    label.setPadding(new Insets(2, 2, 2, 5));
                    vbox_from.getChildren().add(label);
                } catch (NullPointerException ex) {
                    //Because null is saved
                }
            }
            if (cc != null) {
                for (Address c : cc) {
                    try {
                        Label label = new Label(c.toString());
                        label.setPadding(new Insets(2, 5, 2, 5));
                        vbox_cc.getChildren().add(label);
                    } catch (NullPointerException ex) {
                        //Because null is saved
                    }
                }
            }

            txt_subject.setText(email.getSubject());
            anchor_details.setVisible(true);

            //----Attachments
            combo_attach.getItems().clear();

            if (email.getAttch() == null || email.getAttch().equals("")) {
                System.out.println("No attachments");
                combo_attach.setPromptText("No Attachments");
                combo_attach.setDisable(true);
            } else if (!email.getAttch().equals("")) {
                System.out.println("Some attachments");
                combo_attach.setPromptText("Open Attachment");
                combo_attach.setDisable(false);
                List<FileDev> attFiles = new ArrayList<>();
                for (String c : email.getAttch().split("\\^")) {
                    FileDev file = new FileDev(c);
                    attFiles.add(file);
                }
                combo_attach.getItems().addAll(attFiles);

            }
//            else {
//                combo_attach.setPromptText("No Attachments");
//                combo_attach.setDisable(true);
//            }

            //----Ebody
            anchor_body.getChildren().clear();
            TextArea eBody = new TextArea(email.getBody());
            eBody.setWrapText(true);
            eBody.setPrefSize(anchor_body.getWidth(), anchor_body.getHeight());
            anchor_body.getChildren().add(eBody);
            AnchorPane.setLeftAnchor(eBody, 0.0);
            AnchorPane.setRightAnchor(eBody, 0.0);
            AnchorPane.setTopAnchor(eBody, 0.0);
            AnchorPane.setBottomAnchor(eBody, 0.0);
            eBody.setEditable(false);
            if (!anchor_body.isVisible()) {
                anchor_body.setVisible(true);
            }

            if (Email_Type != 1) {  //If Email Type is General or Sent
                btn_lock.setVisible(false);
                btn_unlock.setVisible(false);
                btn_solv.setVisible(false);

                imgLoader.setVisible(false);
                return;
            } else {
                btn_lock.setVisible(true);
                btn_unlock.setVisible(true);
                btn_solv.setVisible(true);
            }

            //Buttons
            if (email.getSolvFlag() == 'S') {    //If Email is solved disable all buttons

                title_locked.setText("Solved By: ");
                label_locked.setText(email.getLockedByName());

                btn_lock.setDisable(true);
                btn_lock.setVisible(true);
                btn_unlock.setDisable(true);
                btn_unlock.setVisible(false);
                btn_solv.setDisable(true);

                combo_respond.setDisable(true);
            } else {    //If Email is not solved
                if (email.getLockd() != '\0') {     //If Email is locked
                    if (email.getLockd() == user.getUCODE()) {      //If Email is locked by YOU
                        btn_lock.setVisible(false);
                        btn_unlock.setVisible(true);
                        btn_unlock.setDisable(false);
                        btn_solv.setDisable(false); //Only someone who has locked the email can solve it.

                        combo_respond.setDisable(false);
                    } else {                        //If Email is locked but NOT by you
                        btn_unlock.setVisible(false);
                        btn_lock.setVisible(true);
                        btn_lock.setDisable(true);
                        btn_solv.setDisable(true);

                        combo_respond.setDisable(true);
                    }
                } else {                            //If Email is not locked
                    btn_lock.setDisable(false);
                    btn_lock.setVisible(true);
                    btn_unlock.setVisible(false);
                    btn_solv.setDisable(true);

                    combo_respond.setDisable(true);
                }
            }

            imgLoader.setVisible(false);
        })).start();
    }

    public void onLock(ActionEvent actionEvent) {
        imgLoader.setVisible(true);
        sql.lockEmail(selectedEmail, 1);
        loadEmailsStatic();
        reloadInstances();
    }

    @FXML
    public void unLock(ActionEvent actionEvent) {
        imgLoader.setVisible(true);
        Email email = selectedEmail;
        sql.lockEmail(email, 0);
        loadEmailsStatic();
        reloadInstances();
    }

    @FXML
    public void onSolv(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to mark this as solved?\n" +
                "This action cannot be taken back. A response will be issued to all concerned Email IDs",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            sql.solvEmail(selectedEmail, "S", user); // S for solved
            loadEmailsStatic();
            reloadInstances();
        } else {
            return;
        }
    }

    private void reloadInstances() {

        // comment this line
        new Thread(() -> {
            if (dController.isServer == true) {
                JServer.broadcastMessages("R");
            } else
                JClient.sendMessage("R");   //Function was made so that if ever this feature is not needed i can just
        }).start();

    }

    //Pulling all email IDs from database
    public void pullingEmails() {
        new Thread(() -> EMAILS_LIST = sql.getAllEmailIDs(null)).start();

    }
}
