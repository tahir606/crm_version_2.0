package Home;

import JCode.fileHelper;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import objects.ProductModule;
import objects.Users;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeSplitController implements Initializable {

    @FXML
    private AnchorPane main_anchor;
    @FXML
    private AnchorPane pane_one;
    @FXML
    private AnchorPane pane_three;
    @FXML
    private AnchorPane pane_two;
    @FXML
    private AnchorPane pane_four;

    private Users user;
    private fileHelper fHelper;
    private mySqlConn sql;
    private ArrayList<ProductModule> lockedModules;

    private String[] dashBoardDets;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAddButton(pane_one);
        setAddButton(pane_two);
        setAddButton(pane_three);
        setAddButton(pane_four);

        fHelper = new fileHelper();
        sql = new mySqlConn();

        user = fHelper.ReadUserDetails();
        user = sql.getNoOfSolvedEmails(user);

//        dashBoardDets = fHelper.readDashboardPanels();


    }

    private void setAddButton(AnchorPane pane) {
        JFXComboBox<String> paneList = new JFXComboBox<>();
        paneList.setPromptText("Choose panel");
        paneList.getItems().addAll(new String[]{"Profile", "Tickets", "Activities", "Modules", "Leads"});
        paneList.setOpacity(0.5);
        paneList.setPadding(new Insets(-3.5));
        paneList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setUpPane(newValue, pane);
        });
        pane.getChildren().add(paneList);
        AnchorPane.setRightAnchor(paneList, 4.0);
        AnchorPane.setTopAnchor(paneList, 2.0);
    }

    private void setUpPane(String choice, AnchorPane pane) {
        switch (choice) {
            case "Tickets": {
                inflateTickets(pane);
                break;
            }
        }
    }

    private void inflateTickets(AnchorPane pane) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 10.0);
        AnchorPane.setTopAnchor(vBox, 10.0);

        vBox.getChildren().addAll(inflateTraditionalHbox("Solved by you: ", String.valueOf(user.getSolved())),
                inflateTraditionalHbox("", ""),
                inflateTraditionalHbox("No. of Unlocked: ", String.valueOf(sql.getNoOfUnlocked())),
                inflateTraditionalHbox("No. of Unsolved: ", String.valueOf(sql.getNoOfUnsolved())));

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane);
    }

    private HBox inflateTraditionalHbox(String label, String data) {
        HBox hbox = new HBox();
        Label l1 = new Label(label);
        l1.getStyleClass().add("homeText");
        Label l2 = new Label(data);
        l2.getStyleClass().add("homeText");
        hbox.getChildren().addAll(l1, l2);
        return hbox;
    }

    private void inflateClearButton(AnchorPane pane) {
        JFXButton clear = new JFXButton("X");
        AnchorPane.setRightAnchor(clear, 1.0);
        AnchorPane.setTopAnchor(clear, 1.0);
        clear.setOnAction(event -> {
            pane.getChildren().clear();
            setAddButton(pane);
        });

        pane.getChildren().addAll(clear);
    }
}
