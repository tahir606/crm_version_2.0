package SplashScreen;

import JCode.trayHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
//import org.scenicview.ScenicView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenThread implements Initializable {
    
    @FXML
    AnchorPane rootAnchor;
    
    private static final int shadowSize = 50;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    //---------------SplashScreen
    static Parent root1;
    
    public void showSplashScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SplashScreen.fxml"));
            root1 = (Parent) fxmlLoader.load();
            
            Platform.runLater(() -> {
                Stage stage2 = new Stage();
//                    stage2.setAlwaysOnTop(true);
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.initStyle(StageStyle.TRANSPARENT);
                stage2.setTitle("Splash Screen");
                stage2.setScene(new Scene(root1));
                trayHelper tray = new trayHelper();
                tray.createIcon(stage2);
                Platform.setImplicitExit(true);
                stage2.show();
            });
    
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static public void hideSplashScreen() {
        root1.getScene().getWindow().hide();
    }
    
}
