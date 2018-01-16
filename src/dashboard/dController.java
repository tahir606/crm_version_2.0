package dashboard;

import JCode.emailControl;
import JCode.fileHelper;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.ESetting;
import objects.Users;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class dController implements Initializable {

    @FXML
    private AnchorPane fx_anchor;

    @FXML
    private Pane main_pane;

    @FXML
    private VBox menu_pane;

    @FXML
    private ImageView img_loader;

    public static ImageView img_load;

    private fileHelper fHelper;
    private mySqlConn sql;
    private static Users user;
    private ArrayList<Users.uRights> rightsList;

    trayHelper tHelper = new trayHelper();

    private static int currentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_load = img_loader;

        fHelper = new fileHelper();
        sql = new mySqlConn();

        user = fHelper.ReadUserDetails();
        rightsList = user.getuRightsList();

        img_loader.setImage(
                new Image(getClass().getResourceAsStream("/res/img/loader.gif")));
        //Setting Loading Image to ImageView
        img_loader.setVisible(true);

        DrawerPane(); //Populate Drawer

        inflateHomeonThread();

        if (user.isEmail())
            emailCtrl();

        Platform.setImplicitExit(false);
    }

    Thread emailThread;

    private void emailCtrl() {

        ESetting eSetting = fHelper.ReadESettings();

        if (eSetting == null) {
            eSetting = sql.getEmailSettings();
            fHelper.WriteESettings(eSetting);
        }

        emailControl ec = new emailControl();

        emailThread = new Thread(() -> {
            while (true) {
                ec.RecieveEmail();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        emailThread.start();

    }

    private void DrawerPane() {

        new Thread(() -> {

            //Is an awarded right for everyone
            homeButton();

            for (Users.uRights r : rightsList) {
                switch (r.getRCODE()) {
                    case 1: {
                        mailButton();
                        break;
                    }
                    case 2: {
                        adminButton();
                        break;
                    }
                }
            }

            logoutButton();
            powerButton();

            img_loader.setVisible(false);
        }).start();
    }

    //---------------------------BUTTONS-------------------------


    Pane email_pane = null;

    private void mailButton() {

        EventHandler myEmailsEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (currentPane == 2) {
                                img_loader.setVisible(false);
                                return;
                            } else if (currentPane == 1) {
                                Platform.runLater(() -> main_pane.getChildren().remove(home_pane));
                            } else if (currentPane == 3) {
                                Platform.runLater(() -> main_pane.getChildren().remove(settings_pane));
                            }

                            email_pane = FXMLLoader.load(getClass().getClassLoader().getResource("Email/emailDash.fxml"));
                            Platform.runLater(() -> {
                                main_pane.getChildren().add(email_pane);
                                currentPane = 2;
                                img_loader.setVisible(false);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("");
        Image image = new Image(getClass().getResourceAsStream("/res/img/mail.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
//        button.getStyleClass().add("mailButton");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myEmailsEvent);
        button.setGraphic(new ImageView(image));
        menu_pane.getChildren().add(button);

    }

    Pane settings_pane = null;

    private void adminButton() {

        EventHandler myAdminEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (currentPane == 3) {
                                img_loader.setVisible(false);
                                return;
                            } else if (currentPane == 1) {
                                Platform.runLater(() -> main_pane.getChildren().remove(home_pane));
                            } else if (currentPane == 2) {
                                Platform.runLater(() -> main_pane.getChildren().remove(email_pane));
                            }

                            settings_pane = FXMLLoader.load(getClass().getClassLoader().getResource("settings/settings.fxml"));
                            Platform.runLater(() -> {
                                main_pane.getChildren().add(settings_pane);
                                currentPane = 3;
                                img_loader.setVisible(false);
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("");
        Image image = new Image(getClass().getResourceAsStream("/res/img/admin.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myAdminEvent);
        button.setGraphic(new ImageView(image));
        menu_pane.getChildren().add(button);
    }

    private void logoutButton() {

        Image image = new Image(getClass().getResourceAsStream("/res/img/logout.png"));

        JFXButton logoutBtn = new JFXButton("");
        logoutBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        logoutBtn.setGraphic(new ImageView(image));
        menu_pane.getChildren().add(logoutBtn);

        logoutBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                img_loader.setVisible(true);
                Stage dash = (Stage) menu_pane.getScene().getWindow();
                dash.close();

                fHelper.DeleteUserDetails();
                fHelper.DeleteESettings();

                if (emailThread != null)
                    emailThread.interrupt();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login/login.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Login- BITS");
                stage.setScene(new Scene(root1));
                stage.setResizable(false);
                tHelper.createTrayIcon(stage);
                tHelper.createIcon(stage);
                stage.show();
            }
        });
    }

    private void powerButton() {

        Image image = new Image(getClass().getResourceAsStream("/res/img/power-off.png"));

        JFXButton powerBtn = new JFXButton("");
        powerBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        powerBtn.setGraphic(new ImageView(image));
        menu_pane.getChildren().add(powerBtn);

        powerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    //---------------------------EVENT HANDLERS---------------------------

    Pane home_pane = null;

    private void homeButton() {

        img_loader.setVisible(true);

        Image image = new Image(this.getClass().getResourceAsStream("/res/img/home.png"));

        JFXButton homeBtn = new JFXButton("");
        homeBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        homeBtn.setGraphic(new ImageView(image));
        menu_pane.getChildren().add(homeBtn);

        homeBtn.setOnAction(event -> inflateHome());
    }

    private void inflateHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (currentPane == 1) {
                        img_loader.setVisible(false);
                        return;
                    } else if (currentPane == 2) {
                        Platform.runLater(() -> main_pane.getChildren().remove(email_pane));
                    } else if (currentPane == 3) {
                        Platform.runLater(() -> main_pane.getChildren().remove(settings_pane));
                    }

                    home_pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home/Home.fxml"));
                    Platform.runLater(() -> {
                        main_pane.getChildren().add(home_pane);
                        currentPane = 1;
                        img_loader.setVisible(false);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void inflateHomeonThread() {
        try {

            home_pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home/Home.fxml"));
            main_pane.getChildren().add(home_pane);
            currentPane = 1;
            img_loader.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
