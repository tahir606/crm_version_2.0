package dashboard;

import JCode.emailControl;
import JCode.fileHelper;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import JSockets.JClient;
import JSockets.JServer;
import SplashScreen.SplashScreenThread;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import objects.ESetting;
import objects.Network;
import objects.Users;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class dController implements Initializable {
    
    @FXML
    private BorderPane border_pane;
    @FXML
    private ImageView img_loader;
    @FXML
    private VBox menu_pane;
    @FXML
    private Pane drawer_pane;
    
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
        //Setting border to the right side
//        drawer_pane.setStyle("-fx-border-width: 0 2 0 0; -fx-border-color: red black green yellow;");
        
        network = fHelper.getNetworkDetails();
        
        sql = new mySqlConn();
        
        eSetting = sql.getEmailSettings();
        user = fHelper.ReadUserDetails();
        rightsList = user.getuRightsList();
        
        SplashScreenThread.hideSplashScreen();
        
        DrawerPane(); //Populate Drawer
        
        changeSelection(homeBtn, "Home/Home.fxml", 1);
        
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
                        leadButton();
                        break;
                    }
                    case 4: {
                        productButton();
                        break;
                    }
                    case 5: {
                        settingsButton();
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
    private void buttonSubSettings(JFXButton button, String btnName) {
        button.setText(btnName.toUpperCase());
        Image image = new Image(getClass().getResourceAsStream("/res/img/" + btnName.toLowerCase() + ".png"));
        button.setMinSize(menu_pane.getPrefWidth(), 35);
        button.setMaxSize(menu_pane.getPrefWidth(), 35);
        button.getStyleClass().add("btn");
        
        button.setGraphic(new ImageView(image));
        button.setAlignment(Pos.CENTER_LEFT);
        Platform.runLater(() -> menu_pane.getChildren().add(button));
    }
    
    private void buttonSettings(String btnName, String path, int paneNo) {
        JFXButton button = new JFXButton();
        buttonSubSettings(button, btnName);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> changeSelection(button, path, paneNo));
    }
    
    JFXButton homeBtn = new JFXButton("Home");
    
    private void homeButton() {
        buttonSettings("Home", "Home/Home.fxml", 1);
    }
    
    private void mailButton() {
        buttonSettings("Emails", "Email/emailDash.fxml", 2);
    }
    
    private void clientButton() {
        buttonSettings("clients", "client/dashBase.fxml", 3);
    }
    
    private void leadButton() {
        buttonSettings("leads", "lead/lead_dash.fxml", 4);
    }
    
    private void productButton() {
        buttonSettings("products", "product/product_dash.fxml", 5);
    }
    
    private void settingsButton() {
        buttonSettings("settings", "settings/settings.fxml", 6);
    }
    
    private void logoutButton() {
        
        JFXButton logoutBtn = new JFXButton("Logout");
        buttonSubSettings(logoutBtn, "Logout");
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
        JFXButton powerBtn = new JFXButton("Power");
        buttonSubSettings(powerBtn, "Power");
        powerBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
    }
    
    //---------------------------EVENT HANDLERS---------------------------
    
    
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
    
    private void changeSelection(JFXButton btn, String path, int pane) {
        
        img_loader.setVisible(true);
        
        for (Node node : menu_pane.getChildren()) {
            node.getStyleClass().remove("btnMenuBoxPressed");
        }
        
        btn.getStyleClass().add("btnMenuBoxPressed");
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                
                if (currentPane == pane) {
                    img_loader.setVisible(false);
                    return;
                }
                
                Platform.runLater(() -> {
                    try {
                        border_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource(path)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentPane = pane;
                    img_loader.setVisible(false);
                });
            }
        }).start();
        
    }
    
    
}
