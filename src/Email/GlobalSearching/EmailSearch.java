package Email.GlobalSearching;

import ApiHandler.RequestHandler;
import Email.EmailDashController;
import JCode.FileDev;
import ZipFile.ZipFileUtility;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import objects.Email;
import objects.History;

import javax.mail.Address;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Email.EmailDashController.*;

public class EmailSearch implements Initializable {
    @FXML
    private JFXTextField search_email;
    @FXML
    private AnchorPane anchor_body, anchor_details,anchor_fix;
    @FXML
    private Label label_ticket, label_time, label_locked, label_created, title_created, title_locked, label_from, label_error;
    @FXML
    private TextArea txt_subject;
    @FXML
    private VBox vbox_from, vbox_cc;
    @FXML
    private JFXComboBox<FileDev> combo_attach;

    EmailDashController emailDashController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchEmail();
        //Attaching listener to attaching combo box
        combo_attach.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null)
                return;

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(newValue);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        anchor_fix.setMinWidth(800);
        anchor_fix.setMinHeight(500);
        anchor_fix.setMinSize(800,500);

    }
    // This method search email which is search by user
    public void searchEmail() {
        //Search OnClick
        search_email.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                label_error.setVisible(true);
                String search = null;
                search=search_email.getText();
                if(search.isEmpty() ){
                    anchor_details.setVisible(false);
                    anchor_body.setVisible(false);
                    label_error.setText("Enter Ticket No");
                    return;
                }
                try {

                    int ticketNo = Integer.parseInt(search);
                    Email emails= (Email) RequestHandler.objectRequestHandler(RequestHandler.run("ticket/email/"+ticketNo), Email.class);

//                    Email emails = sql.readSearchEmail(ticketNo);//Search Email through Query
                    if (emails == null) {
                        anchor_details.setVisible(false);
                        anchor_body.setVisible(false);
                        label_error.setText("Ticket Not Found");
                    }else {
                        label_error.setVisible(false);
                        populateDetails(emails);
                    }
                } catch (NumberFormatException ex) {
                    anchor_details.setVisible(false);
                    anchor_body.setVisible(false);
                    label_error.setText("Invalid Ticket No");
                }catch (IOException e){
                    e.printStackTrace();
                }

            }
        });
    }

    Address[]  cc;
//    String from;
    List<String> from;
// This method display the details of search
    public void populateDetails(Email email) {
        new Thread(() -> Platform.runLater(() -> {
            try {
                label_ticket.setText(String.valueOf(email.getTicketNo()));
            } catch (NullPointerException e) {
                return;
            }
            List<History> historyList= getSelectEmailHistory(email);
            String statusTime = getStatusTime(historyList);

            History history = getHistory(historyList, statusTime, email);
            String userName = "";
            if (history != null) {
                userName = history.getUsers().getFullName();
            }


            label_time.setText(email.getTimestamp());

            vbox_from.getChildren().clear();  //Clearing
            vbox_from.setSpacing(2.0);
            vbox_cc.getChildren().clear();    //Both VBoxes
            vbox_cc.setSpacing(2.0);

            label_from.setText("From:");
            if (emailDashController.Email_Type == 1) {
                from = email.getFromAddress();
                title_locked.setText("Locked By: ");
                if (userName.equals("")) {
                    label_locked.setText("");
                } else {
                    label_locked.setText(userName + " " + statusTime);
                }

                if (email.getUsers().getUserCode() !=22) {
                    label_created.setVisible(true);
                    title_created.setVisible(true);
                    label_created.setText(email.getUsers().getFullName());
                } else {
                    label_created.setVisible(false);
                    title_created.setVisible(false);
                }

            } else if (emailDashController.Email_Type == 2) {
                from = email.getFromAddress();
            } else if (emailDashController.Email_Type == 4) {
                label_from.setText("To:");
                from = email.getToAddress();
                title_locked.setText("Sent By User: ");
                label_locked.setText(String.valueOf(email.getUserCode()));
            }
//            cc = email.getCcAddress();
            if (from.isEmpty()) {
//                for (Address f : from) {
                    try {
                        Label label = new Label(from.get(0));
                        label.setPadding(new Insets(2, 2, 2, 5));
                        label.getStyleClass().add("moduleDetails");
                        vbox_from.getChildren().add(label);
                    } catch (NullPointerException ex) {
                        //Because null is saved
                    }
//                }
            }
            if (!email.getCcAddress().isEmpty()) {
//                if (email.getCcAddresse() != null) {
                for (String c : email.getCcAddress()) {
                    try {
                        if (!c.equals("")) {
                            Label label = new Label(c);
                            label.setPadding(new Insets(2, 5, 2, 5));
                            label.getStyleClass().add("moduleDetails");
                            vbox_cc.getChildren().add(label);
                        }
                    } catch (NullPointerException ex) {
                        //Because null is saved
                    }
                }
            }


            txt_subject.setText(email.getSubject());

            anchor_details.setVisible(true);
            //----Attachments
            combo_attach.getItems().clear();
            if (email.getAttachment() == null || email.getAttachment().isEmpty()) {
                combo_attach.setPromptText("No Attachments");
                combo_attach.setDisable(true);
            } else if (!email.getAttachment().isEmpty()) {
                combo_attach.setPromptText("Open Attachment");
                combo_attach.setDisable(false);
                List<FileDev> attFiles = new ArrayList<>();
                List<String> fileNamesString = new ArrayList<>();
                for (String c : email.getAttachment()) {
                    FileDev file = new FileDev(c);
                    if (!c.equals("")) {
                        EmailDashController.path = file.getAbsolutePath();
                        attFiles.add(new FileDev(EmailDashController.temp + "\\" + file.getName()));
                        fileNamesString.add(file.getName());
                    }
                }
//            if (email.getAttachment() == null || email.getAttachment().isEmpty()) {
//                combo_attach.setPromptText("No Attachments");
//                combo_attach.setDisable(true);
//            } else if (!email.getAttachment().isEmpty()) {
//                combo_attach.setPromptText("Open Attachment");
//                combo_attach.setDisable(false);
//                List<FileDev> attFiles = new ArrayList<>();
//                List<String> fileNamesString = new ArrayList<>();
//                for (String c : email.getAttachment().split("\\^")) {
//                    FileDev file = new FileDev(c);
//                    if (!c.equals("")) {
//                        EmailDashController.path = file.getAbsolutePath();
//                        attFiles.add(new FileDev(EmailDashController.temp + "\\" + file.getName()));
//                        fileNamesString.add(file.getName());
//                    }
//                }
                String comma = ",";

                StringBuilder sb = new StringBuilder();

                int i = 0;
                while (i < fileNamesString.size() - 1) {
                    sb.append(fileNamesString.get(i));
                    sb.append(comma);
                    i++;
                }
                sb.append(fileNamesString.get(i));
                String fileNames = sb.toString(); // only Names of file with comma
                int index = EmailDashController.path.lastIndexOf('\\');
                String actualPath = EmailDashController.path.substring(0, index) + "\\"; //actual path without fileName
                actualPath = actualPath.replace("\\", "/"); //replace slash sign for proper path
                try {
                    ZipFileUtility zipFileUtility = new ZipFileUtility();
                    zipFileUtility.unzip(RequestHandler.downloadZipFile("ticket/download/" , fileNames,    "?path="+actualPath, EmailDashController.temp + "temp.zip"), EmailDashController.temp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                combo_attach.getItems().addAll(attFiles);

            }
            //----Ebody
            anchor_body.getChildren().clear();

//            TextArea eBody = new TextArea(email.getBody());
            WebView eBody = new WebView();

            eBody.getEngine().loadContent(email.getBody());

            eBody.getEngine().setUserStyleSheetLocation("data:,body { font: 15px Calibri; }");
//            eBody.setWrapText(true);
            eBody.setPrefSize(anchor_body.getWidth(), anchor_body.getHeight());
            anchor_body.getChildren().add(eBody);
            AnchorPane.setLeftAnchor(eBody, 0.0);
            AnchorPane.setRightAnchor(eBody, 0.0);
            AnchorPane.setTopAnchor(eBody, 0.0);
            AnchorPane.setBottomAnchor(eBody, 0.0);
            //            eBody.setEditable(false);
            if (!anchor_body.isVisible()) {
                anchor_body.setVisible(true);
            }
//            eBody.setEditable(false);
            if (!anchor_body.isVisible()) {
                anchor_body.setVisible(true);
            }
        })).start();
    }

}
