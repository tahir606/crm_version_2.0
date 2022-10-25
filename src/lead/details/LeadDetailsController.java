package lead.details;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import gui.EventsConstructor;
import gui.NotesConstructor;
import gui.TasksConstructor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lead.LeadDashController;
import lead.newLead.NewLeadController;
import lead.view.LeadViewController;
import objects.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LeadDetailsController implements Initializable {
    @FXML
    private Label txt_fname, txt_company, txt_website, txt_email, txt_mobile, txt_sourceText;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_back, btn_edit, btn_convert2client;
    @FXML
    private VBox vbox_main;

    private mySqlConn sql;

    private Lead lead;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        sql = new mySqlConn();

        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Leads"));
        btn_back.setOnAction(event -> returnToHomePage());

        lead = LeadViewController.staticLead;
        populateDetails();

        btn_convert2client.setOnAction(event -> {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to convert this lead to client?" +
                    "\nAll Tasks & Events will be archived.",
                    ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();

            if (alert2.getResult() == ButtonType.YES) {
                Client client = new Client();
                client.setName(lead.getCompanyName());
                client.setOwner(lead.getFullName().toString());
                client.setWebsite(lead.getWebsite());
                client.setCity(lead.getCity());
                client.setCountry(lead.getCountry());
                client.setFromLead(lead.getLeadsId());
                int userId= FileHelper.ReadUserApiDetails().getUserCode();
                client.setCreatedBy(userId);

                Client client1 = null;
                try {
                    client1 = (Client) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("client/addClient", RequestHandler.writeJSON(client)), Client.class);
                    if (client1 != null) {
                        for (EmailList emailList : lead.getLdEmailLists()) {
                            RequestHandler.post("emailList/updateEmailList", RequestHandler.writeJSON((new EmailList(emailList.getEmailID(), emailList.getAddress(), userId, client1.getClientID()))));
                        }
                        for (PhoneList phoneList : lead.getLdPhoneLists()) {
                            RequestHandler.post("phoneList/updatePhoneList", RequestHandler.writeJSON(new PhoneList(phoneList.getPhoneID(), phoneList.getNumber(), userId, client1.getClientID())));
                        }

                        for (Note note : lead.getLdNoteList()) {
                            RequestHandler.post("note/updateNoteLeadList", RequestHandler.writeJSON(new Note(note.getNoteCode(), client1.getClientID())));
                        }

                        RequestHandler.run("leads/updateLead/" + lead.getLeadsId());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                if (sql.insertFromLead(client))
//                    sql.markLeadAsClient(lead);

                returnToHomePage();
            } else {
                return;
            }
        });

    }

    private void returnToHomePage() {
        try {
            LeadDashController.main_paneF.setCenter(
                    FXMLLoader.load(
                            getClass().getClassLoader().getResource("lead/view/lead_view.fxml")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateDetails() {
        txt_fname.setText(lead.getFullName());
        txt_company.setText(lead.getCompanyName());
        txt_website.setText(lead.getWebsite());
        if (!lead.getLdEmailLists().isEmpty()) {
            txt_email.setText(lead.getLdEmailLists().get(0).getAddress());
        }
        if (!lead.getLdPhoneLists().isEmpty()) {
            txt_mobile.setText(lead.getLdPhoneLists().get(0).getNumber());
        }

        populateSource();

        txt_desc.setText(lead.getNote());

//        btn_email.setOnAction(event -> {
//            EResponseController.stTo = contact.getEmail();
//            EResponseController.stInstance = 'N';
//            inflateEResponse(1);
//        });

        btn_edit.setOnAction(event -> {
            NewLeadController.stInstance = 'U';
            try {
                LeadDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("lead/newLead/new_lead.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(600);
        int CHOICE = 3;
        new NotesConstructor(tabPane, lead).generalConstructor(CHOICE);
        new TasksConstructor(tabPane, lead).generalConstructor(CHOICE);
        new EventsConstructor(tabPane, lead).generalConstructor(CHOICE);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);

        vbox_main.getChildren().add(tabPane);

    }

    private void populateSource() {
//        new Thread(() -> {
//        List<String> sources = sql.getAllSources();

        if (lead.getSourceID() == 0)
            txt_sourceText.setText(lead.getsOther());
        else {
            txt_sourceText.setText(lead.getSource().getName());
        }


//        }).start();
    }
}
