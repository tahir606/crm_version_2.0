package client;

import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import dashboard.dController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class clientDashController implements Initializable {

    @FXML
    private BorderPane main_pane;
    @FXML
    private Label txt_clients;
    @FXML
    private HBox menu_client;
    @FXML
    private AnchorPane pane_client;
    @FXML
    private AnchorPane anchor_holder;

    private ImageView img_loader = dController.img_load;
    private int currentPane = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newClientButton();
    }

    private void newClientButton() {
        JFXButton newClient = new JFXButton("Edit Client");
        Image imageA = new Image(getClass().getResourceAsStream("/res/img/add.png"));
        newClient.setPrefSize(110, menu_client.getHeight() - 4);
        newClient.setOnAction(event -> inflatePane("newClient/newClient.fxml", 1));
        newClient.setGraphic(new ImageView(imageA));
        newClient.getStyleClass().add("btnMenu");

        menu_client.getChildren().addAll(newClient);
    }

    private void viewClients() {

    }

    private Pane admin_pane;

    private void inflatePane(String pane, int p) {
        img_loader.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                if (currentPane == p) {
                    img_loader.setVisible(false);
                    return;
                } else {
                    Platform.runLater(() -> pane_client.getChildren().clear());
                }

                Platform.runLater(() -> {
                    try {
                        main_pane.setCenter(FXMLLoader.load(getClass().getResource(pane)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                        pane_client.getChildren().add(admin_pane);
                    currentPane = p;
                    img_loader.setVisible(false);
                });
            }
        }).start();
    }
}
