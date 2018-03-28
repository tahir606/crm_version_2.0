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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
    private MenuBar menu_clients;

    private ImageView img_loader = dController.img_load;
    private int currentPane = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inflatePane("contactView/contactView.fxml", 3);     //Inflating Dashboard

        populateMenuBar();
    }

    private void populateMenuBar() {

        Menu menuNew = new Menu("New");

        MenuItem newClient = new MenuItem("Edit Client");
        newClient.setOnAction(event -> inflatePane("newClient/newClient.fxml", 1));
        menuNew.getItems().addAll(newClient);

        MenuItem newContact = new MenuItem("New Contact");
        newContact.setOnAction(event -> inflatePane("newContact/newContact.fxml", 2));
        menuNew.getItems().addAll(newContact);

        menu_clients.getMenus().add(menuNew);

        Menu menuView = new Menu("View");

        MenuItem dashboard = new MenuItem("Dashboard");
        dashboard.setOnAction(event -> inflatePane("contactView/contactView.fxml", 3));
        menuView.getItems().add(dashboard);

        menu_clients.getMenus().add(menuView);
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
