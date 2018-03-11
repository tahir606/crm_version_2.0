import SplashScreen.SplashScreenThread;
import com.sun.javafx.application.LauncherImpl;
import dashboard.dController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import JCode.*;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private fileHelper fHelper;
    private static trayHelper tray;

    static SplashScreenThread splash;

    @Override
    public void start(Stage primaryStage) throws Exception {

        fHelper = new fileHelper();
        tray = new trayHelper();

//        fHelper.makeFolders();

        System.out.println("Step 1");

        if (fHelper.getNetworkDetails() == null) {
            Parent root = FXMLLoader.load(getClass().getResource("settings/network/networkSet.fxml"));
            primaryStage.setTitle("Network Settings- BITS-CRM");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            tray.createIcon(primaryStage);
            primaryStage.show();
        } else if (fHelper.ReadUserDetails() == null) {
            Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
            primaryStage.setTitle("Login- BITS-CRM");
            primaryStage.setScene(new Scene(root, 900, 400));
//            primaryStage.setResizable(false);
//            tray.createTrayIcon(primaryStage);
            tray.createIcon(primaryStage);
            primaryStage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("dashboard/dashboard.fxml"));
            primaryStage.setTitle("Dashboard- BITS-CRM");
            primaryStage.setScene(new Scene(root, 1200, 500));
//            primaryStage.setMaximized(true);
//            primaryStage.setResizable(false);
            tray.createTrayIcon(primaryStage);
            tray.createIcon(primaryStage);
            primaryStage.show();
        }
    }


    public static void main(String[] args) {

        new SplashScreenThread().showSplashScreen();
        launch(args);
    }


}
