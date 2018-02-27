package settings.email;

import JCode.Toast;
import JCode.fileHelper;
import JCode.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import objects.ESetting;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class emailSetController implements Initializable {

    @FXML
    private JFXTextField txt_host;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_pass;
    @FXML
    private JFXTextField txt_fspath;
    @FXML
    private JFXCheckBox check_auto;
    @FXML
    private JFXButton btn_auto;
    @FXML
    private JFXCheckBox check_disclaimer;
    @FXML
    private JFXButton btn_disclaimer;
    @FXML
    private JFXButton bnt_save;
    @FXML
    private JFXButton btn_choose;
    @FXML
    private VBox vbox_menu;
    @FXML
    private BorderPane main_pane;
    @FXML
    private JFXListView<String> white_list;

    private static ESetting eSetting;

    private mySqlConn sql;
    private fileHelper fHelper;

    private static String autoText, discText;

    static int EmailSet_type = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        fHelper = new fileHelper();

        eSetting = sql.getEmailSettings();
        System.out.println(eSetting);

        autoText = fHelper.ReadAutoDisc(1).getAutotext();
        discText = fHelper.ReadAutoDisc(2).getAutotext();

        try {
            populateCategoryBoxes();
        } catch (NullPointerException e) {
            System.out.println("Reloaded Null Pointer Exception");
        }

        if (EmailSet_type == 1) {
            System.out.println("tr: " + eSetting.getHost());
            txt_host.setText(eSetting.getHost());
            txt_email.setText(eSetting.getEmail());
            txt_pass.setText(eSetting.getPass());
            txt_fspath.setText(eSetting.getFspath());
            check_auto.setSelected(eSetting.isAuto());
            check_disclaimer.setSelected(eSetting.isDisc());
        } else if (EmailSet_type == 2) {
            init();
        }

    }

    void init() {
        List<String> whiteList = sql.getWhiteListDomains();
        try {
            white_list.getItems().clear();
            white_list.getItems().addAll(whiteList);
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    JFXButton general = new JFXButton("General");
    JFXButton tickets = new JFXButton("Tickets");

    private void populateCategoryBoxes() {

        general.setMinHeight(50);
        general.setMinWidth(60);
        general.getStyleClass().add("btnMenuBox");
        general.setAlignment(Pos.CENTER_LEFT);
        general.setOnAction(event -> changeSettingType(1, general));

        tickets.setMinHeight(50);
        tickets.setMinWidth(60);
        tickets.getStyleClass().add("btnMenuBox");
        tickets.setAlignment(Pos.CENTER_LEFT);
        tickets.setOnAction(event -> changeSettingType(2, tickets));

        vbox_menu.getChildren().addAll(general, tickets);

//        switch (EmailSet_type) {
//            case 1:
//                general.fire();
//                break;
//            case 2:
//                tickets.fire();
//                break;
//        }
    }

    private void changeSettingType(int i, JFXButton btn) {
        tickets.getStyleClass().remove("btnMenuBoxPressed");
        general.getStyleClass().remove("btnMenuBoxPressed");

        btn.getStyleClass().add("btnMenuBoxPressed");

        EmailSet_type = i;

        switch (i) {
            case 1: {
                try {
                    main_pane.setCenter(
                            FXMLLoader.load(
                                    getClass().getClassLoader().getResource("settings/email/general.fxml")));

//                    autoText = eSetting.getAutotext();
//                    discText = eSetting.getDisctext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 2: {
                try {
                    main_pane.setCenter(
                            FXMLLoader.load(
                                    getClass().getClassLoader().getResource("settings/email/tickets.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }

    @FXML
    void setAuto(ActionEvent event) {
        inflateTextArea(1);
    }

    @FXML
    void setDisclaimer(ActionEvent event) {
        inflateTextArea(2);
    }

    @FXML
    void onChoose(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder");
        String path = chooser.showDialog(new Stage()).getAbsolutePath();
        txt_fspath.setText(path);
    }

    public void saveChanges(ActionEvent actionEvent) {

        String host, email, pass, fspath;

        boolean auto, disc;

        host = txt_host.getText();
        email = txt_email.getText();
        pass = txt_pass.getText();
        fspath = txt_fspath.getText();
        auto = check_auto.isSelected();
        disc = check_disclaimer.isSelected();

        if (host.equals("") || pass.equals("") || email.equals("") || fspath.equals("")) {
            Toast.makeText((Stage) bnt_save.getScene().getWindow(), "Required fields are empty");
            return;
        }

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save changes? " +
                " Your previous settings will be deleted.",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.YES) {
            ESetting es = new ESetting(host, email, pass, fspath);
            es.setAuto(auto);
            es.setDisc(disc);
            if (autoText == null)
                autoText = "";
            if (discText == null)
                discText = "";
            es.setAutotext(autoText);
            es.setDisctext(discText);

            sql.saveEmailSettings(es);

            new Thread(() -> Platform.runLater(() -> Toast.makeText((Stage) btn_auto.getScene().getWindow(), "Restart the application for the changes" +
                    " to be made!"))).start();

        } else {
            return;
        }

    }

    private void inflateTextArea(int c) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        String title = (c == 1) ? "Auto Reply" : "Disclamier";
        stage.setTitle(title);
        AnchorPane pane = new AnchorPane();
        TextArea area = new TextArea();

        if (c == 1)         //Auto Reply
            area.setText(autoText);
        else if (c == 2)     //Disclaimer
            area.setText(discText);


        area.setMinSize(400, 400);
        pane.getChildren().add(area);
        stage.setScene(new Scene(pane, 400, 400));
        trayHelper tray = new trayHelper();
        tray.createIcon(stage);
        Platform.setImplicitExit(true);

        stage.setOnHiding(event -> {
            if (c == 1) {        //Auto Reply
                autoText = area.getText();
            } else if (c == 2) {    //Disclaimer
                discText = area.getText();
            }
        });

        stage.show();
    }

    int noOfFields = 10;

    public void addtoWhite(ActionEvent actionEvent) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Enter Domains");
        VBox pane = new VBox();
        pane.setMinWidth(200);
        pane.setMinHeight(230);
        for (int i = 0; i < noOfFields; i++) {
            JFXTextField txt_data = new JFXTextField();
            txt_data.setMinWidth(pane.getWidth());
            txt_data.setPadding(new Insets(2, 2, 2, 2));
            txt_data.setFocusColor(Paint.valueOf("#006e0e"));
            pane.getChildren().add(txt_data);
        }

        stage.setScene(new Scene(pane));
        trayHelper tray = new trayHelper();
        tray.createIcon(stage);
        Platform.setImplicitExit(true);

        stage.setOnHiding(event -> {

            String array[] = new String[noOfFields];

            for (int i = 0; i < noOfFields; i++) {
                String t = ((JFXTextField) pane.getChildren().get(i)).getText();
                array[i] = t;
            }

            sql.insertDomainsWhitelist(array);
            init();

        });

        stage.show();

    }

}
