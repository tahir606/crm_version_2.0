import dashboard.dController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import JCode.*;

public class Main extends Application {

    private fileHelper fHelper;
    private static trayHelper tray;

    @Override
    public void start(Stage primaryStage) throws Exception {

        fHelper = new fileHelper();
        tray = new trayHelper();

        fHelper.makeFolders();

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
        launch(args);
    }
}
