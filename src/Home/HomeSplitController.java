package Home;

import JCode.CommonTasks;
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
    private int noOfPanels = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAddButton(pane_one, 1);
        setAddButton(pane_two, 2);
        setAddButton(pane_three, 3);
        setAddButton(pane_four, 4);

        fHelper = new fileHelper();
        sql = new mySqlConn();

        user = fHelper.ReadUserDetails();
        user = sql.getUserDetails(user);
        user = sql.getNoOfSolvedEmails(user);

        dashBoardDets = new String[noOfPanels];

        setUpPreviousPanels();
    }

    private void setUpPreviousPanels() {
        dashBoardDets = fHelper.readDashboardPanels();

        String p1 = dashBoardDets[0],
                p2 = dashBoardDets[1],
                p3 = dashBoardDets[2],
                p4 = dashBoardDets[3];
        setUpPane(p1, pane_one, 1);
        setUpPane(p2, pane_two, 2);
        setUpPane(p3, pane_three, 3);
        setUpPane(p4, pane_four, 4);

    }

    private void setAddButton(AnchorPane pane, int panel) {
        JFXComboBox<String> paneList = new JFXComboBox<>();
        paneList.setPromptText("Choose panel");
        paneList.getItems().addAll(new String[]{"Profile", "Tickets", "Activities", "Modules", "Leads"});
        paneList.setOpacity(0.5);
        paneList.setPadding(new Insets(-3.5));
        paneList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setUpPane(newValue, pane, panel);
        });
        pane.getChildren().add(paneList);
        AnchorPane.setRightAnchor(paneList, 4.0);
        AnchorPane.setTopAnchor(paneList, 2.0);
    }

    private void setUpPane(String choice, AnchorPane pane, int panel) {
        if (choice.equals("null"))
            return;

        switch (choice) {
            case "Profile": {
                inflateProfile(pane, panel);
                break;
            }
            case "Tickets": {
                inflateTickets(pane, panel);
                break;
            }
            case "Modules": {
                inflateModules(pane, panel);
                break;
            }
        }

        dashBoardDets[panel - 1] = choice;
        fHelper.writeDashboardPanels(dashBoardDets);
    }

    private void inflateActivities(AnchorPane pane, int panel) {

    }

    private void inflateModules(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

        lockedModules = sql.getLockedModules();

        HBox hBox = new HBox();
        hBox.getChildren().addAll(inflateTraditionalLabel("Module","headingText", 90),
                inflateTraditionalLabel("Product","headingText", 90),
                inflateTraditionalLabel("Locked By","headingText", 90),
                inflateTraditionalLabel("Locked On","headingText", 160));

        vBox.getChildren().addAll(hBox);

        for (ProductModule module : lockedModules) {
            HBox hbox = new HBox();
            hbox.setSpacing(5);

            Label moduleLabel = new Label(module.getName()),
                    productLabel = new Label(module.getProductName()),
                    lockedByLabel = new Label(module.getLockedByName()),
                    lockedTimeLabel = new Label(CommonTasks.getTimeFormatted(module.getLockedTime()));

            moduleLabel.getStyleClass().add("dataText");
            productLabel.getStyleClass().add("dataText");
            lockedByLabel.getStyleClass().add("dataText");
            lockedTimeLabel.getStyleClass().add("dataText");

            moduleLabel.setMinWidth(90);
            moduleLabel.setMaxWidth(90);
            productLabel.setMinWidth(90);
            productLabel.setMaxWidth(90);
            lockedByLabel.setMinWidth(90);
            lockedByLabel.setMaxWidth(90);
            lockedTimeLabel.setMinWidth(160);
            lockedTimeLabel.setMaxWidth(160);

            hbox.getChildren().addAll(moduleLabel, productLabel, lockedByLabel, lockedTimeLabel);
            hbox.getStyleClass().add("moduleDetails");

            vBox.getChildren().add(hbox);
        }

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }

    private void inflateProfile(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

        vBox.getChildren().addAll(inflateTraditionalHbox("Profile", "", "headingText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("Name: ", String.valueOf(user.getFNAME()), "dataText"),
                inflateTraditionalHbox("Email: ", String.valueOf(user.getEmail()), "dataText"));

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }

    private void inflateTickets(AnchorPane pane, int panel) {
        VBox vBox = new VBox();

        AnchorPane.setLeftAnchor(vBox, 20.0);
        AnchorPane.setTopAnchor(vBox, 20.0);

        vBox.getChildren().addAll(inflateTraditionalHbox("Tickets", "", "headingText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("Solved by you: ", String.valueOf(user.getSolved()), "dataText"),
                inflateTraditionalHbox("", "", ""),
                inflateTraditionalHbox("No. of Unlocked: ", String.valueOf(sql.getNoOfUnlocked()), "dataText"),
                inflateTraditionalHbox("No. of Unsolved: ", String.valueOf(sql.getNoOfUnsolved()), "dataText"));

        pane.getChildren().clear();
        pane.getChildren().addAll(vBox);

        inflateClearButton(pane, panel);
    }


    private HBox inflateTraditionalHbox(String label, String data, String style) {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(inflateTraditionalLabel(label, style),
                inflateTraditionalLabel(data, style));
        return hbox;
    }

    private Label inflateTraditionalLabel(String data, String style) {
        Label label = new Label(data);
        label.getStyleClass().add(style);
        return label;
    }

    private Label inflateTraditionalLabel(String data, String style, int width) {
        Label label = inflateTraditionalLabel(data, style);
        label.setPrefWidth(width);
        label.setMinWidth(width);
        label.setMaxWidth(width);
        return label;
    }

    private void inflateClearButton(AnchorPane pane, int panel) {
        JFXButton clear = new JFXButton("X");
        AnchorPane.setRightAnchor(clear, 1.0);
        AnchorPane.setTopAnchor(clear, 1.0);
        clear.setOnAction(event -> {
            pane.getChildren().clear();

            dashBoardDets[panel - 1] = "null";
            fHelper.writeDashboardPanels(dashBoardDets);

            setAddButton(pane, panel);
        });

        pane.getChildren().addAll(clear);
    }
}
