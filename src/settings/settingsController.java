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
    }

    private void populateMenu() {

        EventHandler myEmailEvent = event -> inflating("settings/email/email.fxml", 1);
        JFXButton emailSetting = new JFXButton("Email");
        Image image = new Image(getClass().getResourceAsStream("/res/img/at.png"));
        emailSetting.setPrefSize(100, menu_hbox.getHeight());
        emailSetting.setOnAction(myEmailEvent);
        emailSetting.setGraphic(new ImageView(image));
        emailSetting.getStyleClass().add("btnMenu");

        //---------------------------------------------------Split-----------

        EventHandler myAdminEvent = event -> inflating("settings/admin/admin.fxml", 2);
        JFXButton adminSetting = new JFXButton("User");
        Image imageA = new Image(getClass().getResourceAsStream("/res/img/users.png"));
        adminSetting.setPrefSize(100, menu_hbox.getHeight());
        adminSetting.setOnAction(myAdminEvent);
        adminSetting.setGraphic(new ImageView(imageA));
        adminSetting.getStyleClass().add("btnMenu");

        //---------------------------------------------------Split-----------
        networkSetController.fromMain = false;
        EventHandler myNetworkEvent = event -> inflating("settings/network/networkSet.fxml", 3);
        JFXButton networkSetting = new JFXButton("Network");
        Image imageN = new Image(getClass().getResourceAsStream("/res/img/network.png"));
        networkSetting.setPrefSize(100, menu_hbox.getHeight());
        networkSetting.setOnAction(myNetworkEvent);
        networkSetting.setGraphic(new ImageView(imageN));
        networkSetting.getStyleClass().add("btnMenu");

        menu_hbox.getChildren().addAll(emailSetting, adminSetting, networkSetting);

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
                            main_pane.setCenter(FXMLLoader.load(getClass().getClassLoader().getResource
                                    (pane)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        currentPane = p;
                        img_loader.setVisible(false);
                    });
            }
        }).start();
    }
}
