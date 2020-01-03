package settings;

import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import dashboard.dController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import settings.network.networkSetController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private HBox menu_hbox;
    @FXML
    private BorderPane main_pane;

    private ImageView img_loader = dController.img_load;
    private int currentPane = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateMenu();
        img_loader.setVisible(false);
    }

    private void populateMenu() {


        buttonSettings("Email", "settings/email/email.fxml", 1);

        buttonSettings("Users", "settings/admin/admin.fxml", 2);

        networkSetController.fromMain = false;
        buttonSettings("Network", "settings/network/networkSet.fxml", 3);

        buttonSettings("Notifications", "settings/notify/notifySet.fxml", 4);

//        menu_hbox.getChildren().addAll(emailSetting, adminSetting, networkSetting);

        main_pane.setCenter(new Label("Select Setting Type"));

    }

    private Pane admin_pane;

    private void inflating(String pane, int p) {
        img_loader.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (currentPane == p) {
                    img_loader.setVisible(false);
                    return;
                }

                Platform.runLater(() -> {
                    try {
                        main_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource(pane)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    currentPane = p;
                    img_loader.setVisible(false);
                });
            }
        }).start();
    }

    //---------------------------BUTTONS-------------------------

    private void buttonSettings(String buttonName, String path, int paneNo) {
        EventHandler myEmailEvent = event -> inflating(path, paneNo);
        JFXButton button = new JFXButton(buttonName);
        Image image = new Image(getClass().getResourceAsStream("/res/img/" + buttonName.toLowerCase() +".png"));
        button.setGraphic(new ImageView(image));
        button.setPrefSize(120, menu_hbox.getHeight());
        button.setOnAction(myEmailEvent);
        button.getStyleClass().add("btnMenu");
        menu_hbox.getChildren().add(button);
    }
}
