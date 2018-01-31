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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class settingsController implements Initializable {

    @FXML
    private HBox menu_hbox;

    @FXML
    private AnchorPane anchor_holder;

    private ImageView img_loader = dController.img_load;
    private int currentPane = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateMenu();
    }

    private void populateMenu() {

        EventHandler myEmailEvent = event -> {
            inflating("email/email.fxml", 1);
        };

        JFXButton emailSetting = new JFXButton("Email Settings");
        Image image = new Image(getClass().getResourceAsStream("/res/img/at.png"));
        emailSetting.setPrefSize(120, menu_hbox.getHeight() - 2);
        emailSetting.setOnAction(myEmailEvent);
        emailSetting.setGraphic(new ImageView(image));
        emailSetting.getStyleClass().add("btnMenu");

        //---------------------------------------------------Split-----------

        EventHandler myAdminEvent = event -> {
            inflating("admin/admin.fxml", 2);
        };

        JFXButton adminSetting = new JFXButton("User Settings");
        Image imageA = new Image(getClass().getResourceAsStream("/res/img/users.png"));
        adminSetting.setPrefSize(120, menu_hbox.getHeight() - 2);
        adminSetting.setOnAction(myAdminEvent);
        adminSetting.setGraphic(new ImageView(imageA));
        adminSetting.getStyleClass().add("btnMenu");

        menu_hbox.getChildren().addAll(emailSetting, adminSetting);

    }

    private Pane admin_pane;

    private void inflating(String pane, int p) {
        img_loader.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (currentPane == p) {
                        img_loader.setVisible(false);
                        return;
                    } else {
                        Platform.runLater(() -> anchor_holder.getChildren().clear());
                    }

                    admin_pane = FXMLLoader.load(getClass().getResource(pane));
                    Platform.runLater(() -> {
                        anchor_holder.getChildren().add(admin_pane);
                        currentPane = p;
                        img_loader.setVisible(false);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
