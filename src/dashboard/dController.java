package dashboard;

import JCode.emailControl;
import JCode.fileHelper;
import JCode.mySqlConn;
import JCode.trayHelper;
import JSockets.JClient;
import JSockets.JServer;
import SplashScreen.SplashScreenThread;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.ESetting;
import objects.Email;
import objects.Network;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class dController implements Initializable {

    @FXML
    private BorderPane border_pane;
    @FXML
    private ImageView img_loader;
    @FXML
    private VBox menu_pane;

    public static ImageView img_load;

    private fileHelper fHelper;
    private mySqlConn sql;
    private static Users user;
    private static Network network;
    private static ESetting eSetting;
    private ArrayList<Users.uRights> rightsList;

    public static boolean isServer = false;

    trayHelper tHelper;

    private static int currentPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        img_load = img_loader;

        fHelper = new fileHelper();
        tHelper = new trayHelper();

        img_loader.setImage(
                new Image(getClass().getResourceAsStream("/res/img/loader.gif")));
        //Setting Loading Image to ImageView
        img_loader.setVisible(true);

        network = fHelper.getNetworkDetails();

        sql = new mySqlConn();

        eSetting = sql.getEmailSettings();
        user = fHelper.ReadUserDetails();
        rightsList = user.getuRightsList();

        SplashScreenThread.hideSplashScreen();

        DrawerPane(); //Populate Drawer

        inflateHomeonThread();

        if (user.isEmail()) {
            emailCtrl();
            isServer = true;
            new Thread(() -> new JServer()).start();
        } else {
            isServer = false;
            new Thread(() -> new JClient()).start();
        }

        Platform.setImplicitExit(false);
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
                        clientButton();
                        break;
                    }
                    case 3: {
                        productButton();
                        break;
                    }
                    case 4: {
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

    private void mailButton() {

        EventHandler myEmailsEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentPane == 2) {
                            img_loader.setVisible(false);
                            return;
                        }

                        Platform.runLater(() -> {
                            try {
                                border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Email/emailDash.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            currentPane = 2;
                            img_loader.setVisible(false);
                        });
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("Email");
        Image image = new Image(getClass().getResourceAsStream("/res/img/mail.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        button.getStyleClass().add("btn");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myEmailsEvent);
        button.setGraphic(new ImageView(image));
        button.setAlignment(Pos.CENTER_LEFT);
        Platform.runLater(() -> menu_pane.getChildren().add(button));

    }

    private void clientButton() {

        EventHandler myClientsEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);

                boolean connection = mySqlConn.pingHost(network.getHost(), network.getPort(), 2000);

                if (!connection) {
                    tHelper.displayNotification("Error", "Database Not Found!");
                    img_loader.setVisible(false);
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentPane == 4) {
                            img_loader.setVisible(false);
                            return;
                        }

                        Platform.runLater(() -> {
                            try {
                                border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("client/dashBase.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            currentPane = 4;
                            img_loader.setVisible(false);
                        });
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("Client");
        Image image = new Image(getClass().getResourceAsStream("/res/img/clients.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        button.getStyleClass().add("btn");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myClientsEvent);
        button.setGraphic(new ImageView(image));
        button.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> menu_pane.getChildren().add(button));
    }

    private void productButton() {

        EventHandler myEmailsEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentPane == 3) {
                            img_loader.setVisible(false);
                            return;
                        }

                        Platform.runLater(() -> {
                            try {
                                border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Email/emailDash.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            currentPane = 3;
                            img_loader.setVisible(false);
                        });
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("Products");
        Image image = new Image(getClass().getResourceAsStream("/res/img/product.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        button.getStyleClass().add("btn");
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myEmailsEvent);
        button.setGraphic(new ImageView(image));
        button.setAlignment(Pos.CENTER_LEFT);
        Platform.runLater(() -> menu_pane.getChildren().add(button));

    }

    private void adminButton() {

        EventHandler myAdminEvent = new EventHandler() {
            @Override
            public void handle(Event event) {
                img_loader.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (currentPane == 4) {
                            img_loader.setVisible(false);
                            return;
                        }

                        Platform.runLater(() -> {
                            try {
                                border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("settings/settings.fxml")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            currentPane = 4;
                            img_loader.setVisible(false);
                        });
                    }
                }).start();
            }
        };

        JFXButton button = new JFXButton("Setting");
        Image image = new Image(getClass().getResourceAsStream("/res/img/admin.png"));
        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, myAdminEvent);
        button.setGraphic(new ImageView(image));
        button.getStyleClass().add("btn");
        button.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> menu_pane.getChildren().add(button));
    }

    private void logoutButton() {

        Image image = new Image(getClass().getResourceAsStream("/res/img/logout.png"));

        JFXButton logoutBtn = new JFXButton("Logout");
        logoutBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        logoutBtn.setGraphic(new ImageView(image));
        logoutBtn.getStyleClass().add("btn");
        logoutBtn.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> menu_pane.getChildren().add(logoutBtn));


        logoutBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                img_loader.setVisible(true);

                sql.setLogin(user.getUCODE(), false);

                if (isServer == true) {
                    JServer.closeThread();
                } else {
                    try {
                        JClient.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

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

        JFXButton powerBtn = new JFXButton("Power");
        powerBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        powerBtn.setGraphic(new ImageView(image));
        powerBtn.getStyleClass().add("btn");
        powerBtn.setAlignment(Pos.CENTER_LEFT);

        Platform.runLater(() -> menu_pane.getChildren().add(powerBtn));

        powerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }

    //---------------------------EVENT HANDLERS---------------------------

    Pane home_pane = null;

    private void homeButton() {

        img_loader.setVisible(true);

        Image image = new Image(this.getClass().getResourceAsStream("/res/img/home.png"));

        JFXButton homeBtn = new JFXButton("Home");
        homeBtn.setPrefSize(menu_pane.getPrefWidth(), 40);
        homeBtn.setGraphic(new ImageView(image));
        homeBtn.getStyleClass().add("btn");
        homeBtn.setAlignment(Pos.CENTER_LEFT);
        Platform.runLater(() -> menu_pane.getChildren().add(homeBtn));

        homeBtn.setOnAction(event -> inflateHome());
    }

    private void inflateHome() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (currentPane == 1) {
                    img_loader.setVisible(false);
                    return;
                }

                Platform.runLater(() -> {
                    try {
                        border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Home/Home.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentPane = 1;
                    img_loader.setVisible(false);
                });
            }
        }).start();
    }

    private void inflateHomeonThread() {
        try {

            border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource("Home/Home.fxml")));
//            home_pane = FXMLLoader.load(getClass().getClassLoader().getResource("Home/Home.fxml"));
//            main_pane.getChildren().add(home_pane);
            currentPane = 1;
            img_loader.setVisible(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread emailThread;

    private void emailCtrl() {

        emailControl ec = new emailControl();

        emailThread = new Thread(() -> {
            while (true) {
                if (!mySqlConn.pingHost(network.getHost(), network.getPort(), 2000)) {          //MySQL Database Not Found!!
                    tHelper.displayNotification("Error!", "Database Not Found!\n" +
                            "Email Receiving has been stopped!\n" +
                            "Will try again in 10 seconds");
                    try {
                        Thread.sleep(10000);    //Wait for ten seconds before trying again
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                } else {
                    if (!emailControl.checkConnection()) {          //Internet Not Working!!
                        tHelper.displayNotification("Error!", "Internet Not Working!\n" +
                                "Will try again in 10 seconds!");
                        try {
                            Thread.sleep(10000);    //Wait for ten seconds before trying again
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    } else {
                        ec.RecieveEmail();
                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        });

        emailThread.start();

    }


}
