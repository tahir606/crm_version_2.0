package client.dash;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class dashBaseController implements Initializable {

    @FXML
    private VBox vbox_menu;
    @FXML
    private BorderPane main_pane;

    private static int dashType = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCategoryBoxes();
    }

    JFXButton contacts = new JFXButton("Contacts");
    JFXButton clients = new JFXButton("Clients");

    private void populateCategoryBoxes() {

        contacts.setMinHeight(50);
        contacts.setMinWidth(65);
        contacts.getStyleClass().add("btnMenuBox");
        contacts.setAlignment(Pos.CENTER);
        contacts.setOnAction(event -> changeSettingType(1, contacts));

        clients.setMinHeight(50);
        clients.setMinWidth(65);
        clients.getStyleClass().add("btnMenuBox");
        clients.setAlignment(Pos.CENTER);
        clients.setOnAction(event -> changeSettingType(2, clients));

        vbox_menu.getChildren().addAll(contacts, clients);

        contacts.fire();

    }

    private void changeSettingType(int i, JFXButton btn) {
        contacts.getStyleClass().remove("btnMenuBoxPressed");
        clients.getStyleClass().remove("btnMenuBoxPressed");

        btn.getStyleClass().add("btnMenuBoxPressed");

        dashType = i;

        switch (i) {
            case 1: {
                try {
                    main_pane.setCenter(
                            FXMLLoader.load(
                                    getClass().getClassLoader().getResource("client/dash/contactView/contactView.fxml")));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
//                try {
//                    main_pane.setCenter(
//                            FXMLLoader.load(
//                                    getClass().getClassLoader().getResource("settings/email/tickets.fxml")));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                break;
            }
        }

    }
}
