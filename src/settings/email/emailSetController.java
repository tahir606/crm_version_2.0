package settings.email;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import objects.Domain;
import objects.ESetting;
import objects.Keyword;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class emailSetController implements Initializable {

    @FXML
    private JFXTextField txt_host, txt_email, txt_pass, txt_fspath, txt_genReply;
    @FXML
    private JFXCheckBox check_auto;
    @FXML
    private JFXButton btn_auto;
    @FXML
    private JFXCheckBox check_disclaimer;
    @FXML
    private JFXButton btn_disclaimer, bnt_save, btn_choose;
    @FXML
    private JFXButton btn_genHelp;
    @FXML
    private JFXCheckBox check_solvResp;
    @FXML
    private JFXButton btn_solvResp;
    @FXML
    private JFXButton btnGmail;
    @FXML
    private VBox vbox_menu;
    @FXML
    private BorderPane main_pane;
    @FXML
    private JFXListView<Domain> white_list;
    @FXML
    private JFXListView<Domain> black_list;
    @FXML
    private JFXListView<Keyword> blacklist_keyword;
    @FXML
    private JFXTextField txt_saveKeyword;

    private static ESetting eSetting;
    private mySqlConn sql;
    private FileHelper fHelper;

    private static String autoText, discText, solvRespText;

    static int EmailSet_type = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();
        fHelper = new FileHelper();

        try {
            eSetting = (ESetting) RequestHandler.objectRequestHandler(RequestHandler.run("settings/getSettings"), ESetting.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        eSetting = sql.getEmailSettings();

//        autoText = eSetting.getAutotextt();
//        discText = eSetting.getDisctextt();
//        solvRespText = eSetting.getSolvRespTextt();

        autoText = eSetting.getAutoText();
        discText = eSetting.getDisclaimerText();
        solvRespText = eSetting.getSolveText();
        try {
            populateCategoryBoxes();
        } catch (NullPointerException e) {
            System.out.println("Reloaded Null Pointer Exception");
        }

        if (EmailSet_type == 1) {

            btnGmail();
            txt_host.setText(eSetting.getHost());
            txt_email.setText(eSetting.getEmail());
            txt_pass.setText(eSetting.getPassword());
            txt_fspath.setText(eSetting.getFilePath());
//            txt_host.setText(eSetting.getHostt());
//            txt_email.setText(eSetting.getEmailt());
//            txt_pass.setText(eSetting.getPasst());
//            txt_fspath.setText(eSetting.getFspatht());
//            if (eSetting.getGenerated_reply_emailt() != null) {
//                if (!eSetting.getGenerated_reply_emailt().equals(eSetting.getHostt()))
//                    txt_genReply.setText(eSetting.getGenerated_reply_emailt());
//            }
            if (eSetting.getGeneraEmail() != null) {
                if (!eSetting.getGeneraEmail().equals(eSetting.getHost()))
                    txt_genReply.setText(eSetting.getGeneraEmail());
            }
            boolean autoCheck, disclaimerCheck, solveCheck;
            if (eSetting.getAutoCheck() == 1) {
                autoCheck = true;
            } else {
                autoCheck = false;
            }
            if (eSetting.getDisclaimerCheck() == 1) {
                disclaimerCheck = true;
            } else {
                disclaimerCheck = false;
            }
            if (eSetting.getSolveCheck() == 1) {
                solveCheck = true;
            } else {
                solveCheck = false;
            }
//            check_auto.setSelected(eSetting.isAutot());
//            check_disclaimer.setSelected(eSetting.isDisct());
//            check_solvResp.setSelected(eSetting.isSolvt());
            check_auto.setSelected(autoCheck);
            check_disclaimer.setSelected(disclaimerCheck);
            check_solvResp.setSelected(solveCheck);

            btn_genHelp.setTooltip(new Tooltip("This email will be used to send all emails. " +
                    "\nIf these field is left empty main email will be used to send emails."));

            EmailSet_type = 0;
        } else if (EmailSet_type == 2) {
            init();
            EmailSet_type = 0;
        }

    }

    private void btnGmail() {
        btnGmail.setText("Gmail Settings");
        Image image = new Image(getClass().getResourceAsStream("/res/img/gmail.png"), 20, 15, false, false);
//        button.setPrefSize(menu_pane.getPrefWidth(), 40);
        btnGmail.getStyleClass().add("btn");
        btnGmail.setGraphic(new ImageView(image));
    }

    void init() {
        txt_saveKeyword.setText(eSetting.getReplacementKeyword());
        List<Domain> whiteList = null;
        List<Domain> blackList = null;
        try {
            whiteList = RequestHandler.listRequestHandler(RequestHandler.run("domains/getDomains/" + 1), Domain.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            blackList = RequestHandler.listRequestHandler(RequestHandler.run("domains/getDomains/" + 0), Domain.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<String> whiteList = sql.getWhiteBlackListDomains(1);
//        List<String> blackList = sql.getWhiteBlackListDomains(2);

//        List<String> blackListKeyword = sql.getBlackListKeyword();// get blackList keyword and display in lestView
        List<Keyword> blackListKeyword = null;
        try {
            blackListKeyword = RequestHandler.listRequestHandler(RequestHandler.run("keyword/getKeywords"), Keyword.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            white_list.getItems().clear();
            white_list.getItems().addAll(whiteList);
            black_list.getItems().clear();
            black_list.getItems().addAll(blackList);
            blacklist_keyword.getItems().clear();
            blacklist_keyword.getItems().addAll(blackListKeyword);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //Right click menu
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem blacklistItem = new MenuItem("BlackList Domain");
        blacklistItem.setOnAction(t -> {
            try {
                RequestHandler.post("domains/updateDomains", RequestHandler.writeJSON(white_list.getSelectionModel().getSelectedItem())).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            init();
        });
        contextMenu.getItems().add(blacklistItem);
        white_list.setContextMenu(contextMenu);
        white_list.setOnContextMenuRequested(event -> event.consume());

//      remove keyword from right click
        final ContextMenu replacedKeywordMenu = new ContextMenu();
        MenuItem replacedKeywordItem = new MenuItem("Remove Keyword");
        replacedKeywordItem.setOnAction(t -> {
//            sql.removeKeyword( blacklist_keyword.getSelectionModel().getSelectedItem());
            try {
                RequestHandler.run("keyword/removeKeyword/" + blacklist_keyword.getSelectionModel().getSelectedItem().getCode()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            init();
        });
        replacedKeywordMenu.getItems().add(replacedKeywordItem);
        blacklist_keyword.setContextMenu(replacedKeywordMenu);
        blacklist_keyword.setOnContextMenuRequested(event -> event.consume());

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
    void setSolvResp(ActionEvent actionEvent) {
        inflateTextArea(3);
    }

    @FXML
    void onChoose(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Folder");
        String path = chooser.showDialog(new Stage()).getAbsolutePath();
        txt_fspath.setText(path);
    }

    public void saveChanges(ActionEvent actionEvent) {

        String host, email, pass, fspath, genReplyEmail;

        boolean auto, disc, solv;

        host = txt_host.getText();
        email = txt_email.getText();
        pass = txt_pass.getText();
        fspath = txt_fspath.getText();
        genReplyEmail = txt_genReply.getText();
        auto = check_auto.isSelected();
        disc = check_disclaimer.isSelected();
        solv = check_solvResp.isSelected();

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
//            es.setAutot(auto);
//            es.setDisct(disc);
//            es.setSolvt(solv);
            int autoCheck, disclaimerCheck, solveCheck;
            if (auto) {
                autoCheck = 1;
            } else {
                autoCheck = 0;
            }
            if (disc) {
                disclaimerCheck = 1;
            } else {
                disclaimerCheck = 0;
            }
            if (solv) {
                solveCheck = 1;
            } else {
                solveCheck = 0;
            }
            es.setAutoCheck(autoCheck);
            es.setDisclaimerCheck(disclaimerCheck);
            es.setSolveCheck(solveCheck);
            if (autoText == null)
                autoText = "";
            if (discText == null)
                discText = "";
            if (solvRespText == null)
                solvRespText = "";

            es.setAutoText(autoText);
            es.setDisclaimerText(discText);
            es.setSolveText(solvRespText);

            if (genReplyEmail.equals(""))
                es.setGeneraEmail(email);
            else
                es.setGeneraEmail(genReplyEmail);


            es.setCode(eSetting.getCode());
            es.setReplacementKeyword(eSetting.getReplacementKeyword());

            try {
                RequestHandler.post("settings/updateSettings", RequestHandler.writeJSON(es)).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String title = (c == 1) ? "Auto Reply" : (c == 2 ? "Disclamier" : "Solved Response");
        stage.setTitle(title);
        AnchorPane pane = new AnchorPane();
        TextArea area = new TextArea();

        if (c == 1)         //Auto Reply
            area.setText(autoText);
        else if (c == 2)    //Disclaimer
            area.setText(discText);
        else if (c == 3)    //Solved Response
            area.setText(solvRespText);


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
            } else if (c == 3) {    //Solved Response
                solvRespText = area.getText();
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

            List<Domain> domains = new ArrayList<>();
            for (int i = 0; i < noOfFields; i++) {
                String t = ((JFXTextField) pane.getChildren().get(i)).getText();

                if (!t.equals("")) {
                    domains.add(new Domain(t, 1));
                }
            }
            try {
                RequestHandler.post("domains/addDomains", RequestHandler.writeJSONDomainList(domains)).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            init();

        });

        stage.show();

    }

    //      add blacklisted keywords
    public void addKeyword(ActionEvent actionEvent) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("Enter Black List Keyword");
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

            List<Keyword> keywords = new ArrayList<>();
            for (int i = 0; i < noOfFields; i++) {
                String t = ((JFXTextField) pane.getChildren().get(i)).getText();

                if (!t.equals("")) {
                    keywords.add(new Keyword(t));
                }
            }
            try {
                RequestHandler.post("keyword/addKeywords", RequestHandler.writeJSONKeywordList(keywords)).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            init();

        });

        stage.show();

    }

    //    add replacement keyword
    public void addReplacementKeyword(ActionEvent actionEvent) {
        String saveKeyword = txt_saveKeyword.getText();
        try {
            RequestHandler.run("settings/replacementKeyword?keyword=" + saveKeyword).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        sql.updateReplacementKeyword(saveKeyword); //this method update keyword which is save newKeyword in a text field
    }

    public void onGmail(ActionEvent actionEvent) {

        txt_host.setText("imap.gmail.com");
        txt_email.setText("");
        txt_pass.setText("");

    }

}
