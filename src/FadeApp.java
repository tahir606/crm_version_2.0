import JCode.fileHelper;
import JCode.trayHelper;
import SplashScreen.SplashScreenThread;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.w3c.dom.Document;

import java.io.IOException;

/**
 * Example of displaying a splash page for a standalone JavaFX application
 */
public class FadeApp extends Application {
    private Pane splashLayout;
    private ProgressBar loadProgress;
    private Label progressText;
    private WebView webView;
    private Stage primaryStage;
    private static final int SPLASH_WIDTH = 676;
    private static final int SPLASH_HEIGHT = 227;


    private fileHelper fHelper;
    private static trayHelper tray;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image("http://fxexperience.com/wp-content/uploads/2010/06/logo.png"));
        loadProgress = new ProgressBar();
        loadProgress.setPrefWidth(SPLASH_WIDTH - 20);
        progressText = new Label("Loading hobbits with pie . . .");
        splashLayout = new VBox();
        splashLayout.getChildren().addAll(splash, loadProgress, progressText);
        progressText.setAlignment(Pos.CENTER);
        splashLayout.setStyle("-fx-padding: 5; -fx-background-color: cornsilk; -fx-border-width:5; -fx-border-color: linear-gradient(to bottom, chocolate, derive(chocolate, 50%));");
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(final Stage initStage) throws Exception {
        showSplash(initStage);
        showMainStage();
    }

    private void showMainStage() {
        fHelper = new fileHelper();
        tray = new trayHelper();

        primaryStage = new Stage();

//        fHelper.makeFolders();
//
//        Thread.sleep(5000);

        if (fHelper.getNetworkDetails() == null) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("settings/network/networkSet.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Network Settings- BITS-CRM");
            primaryStage.setScene(new Scene(root, 600, 400));
            primaryStage.setResizable(false);
            tray.createIcon(primaryStage);
            primaryStage.show();
        } else if (fHelper.ReadUserDetails() == null) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Login- BITS-CRM");
            primaryStage.setScene(new Scene(root, 900, 400));
//            primaryStage.setResizable(false);
//            tray.createTrayIcon(primaryStage);
            tray.createIcon(primaryStage);
            primaryStage.show();
        } else {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("dashboard/dashboard.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Dashboard- BITS-CRM");
            primaryStage.setScene(new Scene(root, 1200, 500));
//            primaryStage.setMaximized(true);
//            primaryStage.setResizable(false);
            tray.createTrayIcon(primaryStage);
            tray.createIcon(primaryStage);
            primaryStage.show();
        }
    }

    private void showSplash(Stage initStage) {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.show();
    }
}