import JCode.FileHelper;
import JCode.trayHelper;
import SplashScreen.SplashScreenThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Users;
import settings.network.networkSetController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static settings.admin.adminController.checkIfUserIsLoggedInn;

public class Main extends Application {

    private FileHelper fHelper;
    private static trayHelper tray;

    static SplashScreenThread splash;

    @Override
    public void start(Stage primaryStage) throws Exception {

        fHelper = new FileHelper();
        tray = new trayHelper();

        fHelper.checkFolders();

        Users userLoggedIn= FileHelper.ReadUserApiDetails();
        if (FileHelper.getNetworkDetails() == null) {
            networkSetController.fromMain = true;
            Parent root = FXMLLoader.load(getClass().getResource("settings/network/networkSet.fxml"));
            primaryStage.setTitle("Network Settings- BITS-CRM");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            tray.createIcon(primaryStage);
            primaryStage.show();
        }
        else if (userLoggedIn == null) {
            loginPage(primaryStage);
        } else {
            //If user has been marked logged out, display login screen
            if (!checkIfUserIsLoggedInn(userLoggedIn)) {
                loginPage(primaryStage);
                return;
            }
            Parent root = FXMLLoader.load(getClass().getResource("dashboard/dashboard.fxml"));
            primaryStage.setTitle("Dashboard - BITS-CRM");

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


    public static boolean pingHost() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(FileHelper.getNetworkDetails().getHost(), FileHelper.getNetworkDetails().getPort()), 2000);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
    public static void main(String[] args) {
        new SplashScreenThread().showSplashScreen();
        launch(args);
    }


}
