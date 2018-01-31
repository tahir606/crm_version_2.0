package Email;

import JCode.*;
import JSockets.JClient;
import JSockets.JServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import dashboard.dController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private VBox vbox_from;
    @FXML
    private VBox vbox_cc;

    @FXML
    private JFXComboBox<FileDev> combo_attach;

    @FXML
    private HBox menu_email;

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

    private static volatile Email selectedEmail = null;
    public static volatile boolean reload = false;


    public static ListView<Email> list_emailsF;

    public EmailDashController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imgLoader.setVisible(true);

        list_emailsF = list_emails;

        anchor_details.setVisible(false);

        sql = new mySqlConn();
        fHelper = new fileHelper();
        eCon = new emailControl();

        user = fHelper.ReadUserDetails();

        loadEmails(); //Loading Emails into the list

        menu_email.setSpacing(10.0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //1) Populating Top Menu
                //  a) Creating and adding listeners to Buttons
                JFXButton email = new JFXButton();
                email.setMinSize(35, 30);
                email.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/newmail.png"))));
                email.getStyleClass().add("btnMenu");
                email.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    efrom = "";
                    subject = "";
                    body = "";
                    ReplyForward = 'N'; //N for New.

                    inflateEResponse();
                });

                JFXButton reload = new JFXButton();
                reload.setMinSize(35, 30);
                reload.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/refresh.png"))));
                reload.getStyleClass().add("btnMenu");
                reload.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (selectedEmail != null) {
                        loadEmails(selectedEmail);
                    } else {
                        loadEmails();
                    }
                });

                JFXButton filter = new JFXButton();
                filter.setMinSize(35, 30);
                filter.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/res/img/filter.png"))));
                filter.getStyleClass().add("btnMenu");
                filter.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> inflateFilters());


                Platform.runLater(() -> menu_email.getChildren().addAll(email, reload, filter));


            }
        }).start();


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
            } else if (newValue.equals("Forward")) {
                ReplyForward = 'F';
                body = sEmail.getBody();
            }
            inflateEResponse();
            combo_respond.getSelectionModel().select(0);
        });
//        imgLoader.setVisible(false);
    }

    //OPENING RESPONSE STAGE
    private void inflateEResponse() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EResponse/EResponse.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage2 = new Stage();
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.initStyle(StageStyle.UTILITY);
            stage2.setTitle("Respond");
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

    public void loadEmails() {
        new Thread(() -> Platform.runLater(() -> {
            list_emails.getItems().clear();
            list_emails.getItems().addAll(checkIfEmailsExist());
        })).start();
    }

    void loadEmails(Email e) {      //Loads an email and sets a particular email as selected
        new Thread(() -> {
            Platform.runLater(() -> {
                int index = -1;
                list_emails.getItems().clear();
                list_emails.getItems().addAll(checkIfEmailsExist());

                for (Email email : list_emails.getItems()) {
                    index++;
                    if (email.getEmailNo() == e.getEmailNo()) {
                        break;
                    }
                }
                list_emails.getSelectionModel().select(index);
            });

            imgLoader.setVisible(false);
        }).start();
    }

    public static void loadEmailsStatic() {      //Load Emails from other controller
        new Thread(() -> {
            imgLoader.setVisible(true);

            Email e = selectedEmail;

            mySqlConn sql = new mySqlConn();
            fileHelper helper = new fileHelper();

            List<Email> emails = sql.readAllEmails(helper.ReadFilter());

            Platform.runLater(() -> {
                int index = -1;
                list_emailsF.getItems().clear();
                list_emailsF.getItems().addAll(emails);

                if (selectedEmail == null) {
                    imgLoader.setVisible(false);
                    return;
                }

                for (Email email : list_emailsF.getItems()) {
                    index++;
                    if (email.getEmailNo() == e.getEmailNo()) {
                        break;
                    }
                }
                list_emailsF.getSelectionModel().select(index);
            });

            imgLoader.setVisible(false);
        }).start();
    }


    private List<Email> checkIfEmailsExist() {

        List<Email> emails = sql.readAllEmails(fHelper.ReadFilter());
        if (emails == null) {
            emails = new ArrayList<>();
            Email nEm = new Email();
            nEm.setSubject("Emails Found");
            nEm.setEmailNo(0);
            nEm.setFromAddress(new Address[]{new InternetAddress()});
            emails.add(nEm);

            list_emails.setDisable(true);
            anchor_details.setVisible(false);
            anchor_body.setVisible(false);

        } else {
            list_emails.setDisable(false);
        }

        return emails;
    }

//    private static List<Email> checkIfEmailsExistStatic() {
//        List<Email> emails = sql.readAllEmails(fHelper.ReadFilter());
//        if (emails == null) {
//            emails = new ArrayList<>();
//            Email nEm = new Email();
//            nEm.setSubject("Emails Found");
//            nEm.setEmailNo(0);
//            nEm.setFromAddress(new Address[]{new InternetAddress()});
//            emails.add(nEm);
//
//            list_emails.setDisable(true);
//            anchor_details.setVisible(false);
//            anchor_body.setVisible(false);
//
//        } else {
//            list_emails.setDisable(false);
//        }
//
//        return emails;
//    }


    Address[] from, cc;

    private void populateDetails(Email email) {
        imgLoader.setVisible(true);
        new Thread(() -> Platform.runLater(() -> {
            try {
                label_ticket.setText(String.valueOf(email.getEmailNo()));
            } catch (NullPointerException e) {
                return;
            }

//                        DateFormat dFormat = DateFormat.getInstance();
//                        Date date = null;
//                        try {
//                            date = dFormat.parse(email.getTimestamp());
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
            title_locked.setText("Locked By: ");
            label_time.setText(email.getTimestamp());

            //----Emails
            vbox_from.getChildren().clear();  //Clearing
            vbox_from.setSpacing(2.0);
            vbox_cc.getChildren().clear();    //Both VBoxes
            vbox_cc.setSpacing(2.0);

            from = email.getFromAddress();
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

            label_locked.setText(email.getLockedByName());

            txt_subject.setText(email.getSubject());
            anchor_details.setVisible(true);

            //----Attachments
            combo_attach.getItems().clear();

            if (!email.getAttch().equals("")) {
                combo_attach.setDisable(false);
                combo_attach.setPromptText("Open Attachment");
                List<FileDev> attFiles = new ArrayList<>();
                for (String c : email.getAttch().split("\\^")) {
                    FileDev file = new FileDev(c);
                    attFiles.add(file);
                }
                combo_attach.getItems().addAll(attFiles);

            } else {
                combo_attach.setPromptText("No Attachments");
                combo_attach.setDisable(true);
            }

            //----Ebody
            anchor_body.getChildren().clear();
            TextArea eBody = new TextArea(email.getBody());
            eBody.setWrapText(true);
            eBody.setPrefSize(anchor_body.getWidth(), anchor_body.getHeight());
            anchor_body.getChildren().add(eBody);
            eBody.setEditable(false);
            if (!anchor_body.isVisible()) {
                anchor_body.setVisible(true);
            }

            //Buttons
            if (email.getSolvFlag() == 'S') {    //If Email is solved disable all buttons
//                            System.out.println("If Email is solved disable all buttons");

                title_locked.setText("Solved By: ");
                label_locked.setText(email.getLockedByName());

                btn_lock.setDisable(true);
                btn_lock.setVisible(true);
                btn_unlock.setDisable(true);
                btn_unlock.setVisible(false);
                btn_solv.setDisable(true);

                combo_respond.setDisable(true);
            } else {    //If Email is not solved
//                            System.out.println("If Email is not solved");
                if (email.getLockd() != '\0') {     //If Email is locked
//                                System.out.println("If Email is locked");
                    if (email.getLockd() == user.getUCODE()) {      //If Email is locked by YOU
//                                    System.out.println("If Email is locked by YOU");
                        btn_lock.setVisible(false);
                        btn_unlock.setVisible(true);
                        btn_unlock.setDisable(false);
                        btn_solv.setDisable(false); //Only someone who has locked the email can solve it.

                        combo_respond.setDisable(false);
                    } else {                        //If Email is locked but NOT by you
//                                    System.out.println("If Email is locked but NOT by you");
                        btn_unlock.setVisible(false);
                        btn_lock.setVisible(true);
                        btn_lock.setDisable(true);
                        btn_solv.setDisable(true);

                        combo_respond.setDisable(true);
                    }
                } else {                            //If Email is not locked
//                                System.out.println("If Email is not locked");
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
        new Thread(() -> {
            sql.lockEmail(selectedEmail, 1);
            loadEmails(selectedEmail);
            reloadInstances();
        }).start();
    }

    @FXML
    public void unLock(ActionEvent actionEvent) {
        imgLoader.setVisible(true);
        new Thread(() -> {
            Email email = selectedEmail;
            sql.lockEmail(email, 0);
            loadEmails(email);
            reloadInstances();
        }).start();
    }

    @FXML
    public void onSolv(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to mark this as solved?\n" +
                "This action cannot be taken back.",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            sql.solvEmail(selectedEmail, "S", user); // S for solved
            loadEmails(selectedEmail);
            reloadInstances();
        } else {
            return;
        }
    }

    private void reloadInstances() {
        if (dController.isServer == true) {
            JServer.broadcastMessages("R");
        } else
            JClient.sendMessage("R");   //Function was made so that if ever this feature is not needed i can just
    }                                     // comment this line

//    public static boolean isReload() {
//        return reload;
//    }
//
//    public void setReload(boolean reload) {
//        this.reload = reload;
//        if (reload == true) {
//            if (selectedEmail != null)
//                loadEmails(selectedEmail);
//            else
//                loadEmails();
//        }
//    }
}
