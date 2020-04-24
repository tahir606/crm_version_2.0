import JCode.mysql.mySqlConn;
import SplashScreen.SplashScreenThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import JCode.*;
import objects.Users;
import settings.network.networkSetController;

import java.io.IOException;

public class Main extends Application {

    private FileHelper fHelper;
    private mySqlConn mySql;
    private static trayHelper tray;

    static SplashScreenThread splash;

    @Override
    public void start(Stage primaryStage) throws Exception {

        fHelper = new FileHelper();
        tray = new trayHelper();

        fHelper.checkFolders();

        Users userLoggedIn = fHelper.ReadUserDetails();

        if (fHelper.getNetworkDetails() == null) {
            networkSetController.fromMain = true;
            Parent root = FXMLLoader.load(getClass().getResource("settings/network/networkSet.fxml"));
            primaryStage.setTitle("Network Settings- BITS-CRM");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            tray.createIcon(primaryStage);
            primaryStage.show();
        } else if (userLoggedIn == null) {
            loginPage(primaryStage);
        } else {
            //If user has been marked logged out, display login screen
            mySql = new mySqlConn();
            if (!mySql.checkIfUserIsLoggedIn(userLoggedIn)) {
                fHelper.DeleteUserDetails();
                loginPage(primaryStage);
                return;
            }
            Parent root = FXMLLoader.load(getClass().getResource("dashboard/dashboard.fxml"));
            primaryStage.setTitle("Dashboard- BITS-CRM");
            primaryStage.setScene(new Scene(root, 1400, 600));
            tray.createTrayIcon(primaryStage);
            tray.createIcon(primaryStage);
            primaryStage.show();
        }
    }

    private void loginPage(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        primaryStage.setTitle("Login- BITS-CRM");
        primaryStage.setScene(new Scene(root, 1400, 500));
        tray.createIcon(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        new SplashScreenThread().showSplashScreen();
        launch(args);
    }


}
