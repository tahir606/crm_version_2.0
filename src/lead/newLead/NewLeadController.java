package lead.newLead;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lead.LeadDashController;
import lead.view.LeadViewController;
import objects.EmailList;
import objects.Lead;
import objects.PhoneList;
import objects.Source;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class NewLeadController implements Initializable {

    @FXML
    private JFXTextField txt_fname, txt_lname, txt_company, txt_website, txt_city, txt_country, txt_email, txt_mobile;
    @FXML
    private TextArea txt_note;
    @FXML
    private JFXButton btn_back, btn_save;
    @FXML
    private JFXComboBox<Source> combo_source;
    @FXML
    private HBox hbox_other;
    @FXML
    private Label txt_heading;

    public static char stInstance;

    private Lead lead;
    private mySqlConn sql;

    public static int noOfFields = 2;   //No of emails and phones

    private TextArea otherTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();
        lead = new Lead();
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Leads"));
        btn_back.setOnAction(event -> {
            try {
                LeadDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("lead/view/lead_view.fxml")));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        List<Source> sourceList = new ArrayList<>();
        try {
            sourceList = RequestHandler.listRequestHandler(RequestHandler.run("source/getAllSource"), Source.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        combo_source.getItems().addAll(sourceList);

        combo_source.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getName().equalsIgnoreCase("Other")) {
                otherTextArea = new TextArea();
                otherTextArea.setPromptText("Other Source");
                hbox_other.getChildren().add(otherTextArea);
            } else {
                hbox_other.getChildren().clear();
            }
        });


        if (stInstance == 'N') {
            btn_save.setText("Add");
//            lead = new Lead();
//            lead.setCode(sql.getNewLeadCode());
            populateLead(new Lead());
            txt_heading.setText("New Lead");
        } else if (stInstance == 'U') {
            btn_save.setText("Update");
            populateLead(LeadViewController.staticLead);
            txt_heading.setText("Update Lead");
        }
    }

    private void populateLead(Lead lead1) {
//        lead = LeadViewController.staticLead;
        lead.setLeadsId(lead1.getLeadsId());
        txt_fname.setText(lead1.getFirstName());
        txt_lname.setText(lead1.getLastName());
        txt_website.setText(lead1.getWebsite());
        txt_company.setText(lead1.getCompanyName());
        if (lead1.getLdEmailLists() != null) {
            if (lead1.getLdEmailLists().isEmpty()) {
                txt_email.setText("");
            } else {
                txt_email.setText(lead1.getLdEmailLists().get(0).getAddress());
            }
        }
        if (lead1.getLdPhoneLists() != null) {
            if (lead1.getLdPhoneLists().isEmpty()) {
                txt_mobile.setText("");
            } else {
                txt_mobile.setText(lead1.getLdPhoneLists().get(0).getNumber());
            }
        }
        txt_city.setText(lead1.getCity());
        txt_country.setText(lead1.getCountry());
        txt_note.setText(lead1.getNote());
        if (lead1.getsOther() == null) {
            combo_source.getSelectionModel().select(new Source("Other"));
        } else {
            combo_source.getSelectionModel().select(lead1.getSourceID() - 1);
            otherTextArea.setText(lead1.getsOther());
        }
    }

    public void saveChanges(ActionEvent actionEvent) {

        String fname = txt_fname.getText(),
                lname = txt_lname.getText(),
                email = txt_email.getText(),
                phone = txt_mobile.getText(),
                company = txt_company.getText(),
                city = txt_city.getText(),
                country = txt_country.getText(),
                desc = txt_note.getText(),
                website = txt_website.getText();

        int source = combo_source.getSelectionModel().getSelectedIndex();

        if (fname.equals("") || lname.equals("") || city.equals("") || country.equals("") || company.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        } else {
            String msg = "";
            switch (stInstance) {
                case 'N': {
                    msg = "Are you sure you want to add Lead?";
                    break;
                }
                case 'U': {
                    msg = "Are you sure you want to update Lead?";
                    break;
                }
                default: {
                    break;
                }
            }
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, msg,
                    ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();

            if (alert2.getResult() == ButtonType.YES) {

                fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);   //Make First Letter to Uppercase
                lname = lname.substring(0, 1).toUpperCase() + lname.substring(1);
//                List<EmailList> emailLists = new ArrayList<>();
//                List<PhoneList> phoneLists = new ArrayList<>();
                lead.setFirstName(fname);
                lead.setLastName(lname);
                lead.setCompanyName(company);
                lead.setCity(city);
                lead.setCountry(country);
                lead.setNote(desc);
//                emailLists.add(new EmailList(email));
//                lead.setLdEmailLists(emailLists);
//                phoneLists.add(new PhoneList(phone));
//                lead.setLdPhoneLists(phoneLists);
                lead.setWebsite(website);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                lead.setCreatedOn(String.valueOf(formatter.format(date)));
                int userId = FileHelper.ReadUserApiDetails().getUserCode();
                lead.setCreatedBy(userId);
                lead.setSourceID(combo_source.getSelectionModel().getSelectedIndex() + 1);
                if (combo_source.getSelectionModel().getSelectedItem().getName().equalsIgnoreCase("Other")) {
                    if (otherTextArea.getText().toString().trim().length() < 1) {
                        Toast.makeText((Stage) btn_save.getScene().getWindow(), "Other source text cannot be empty");
                        return;
                    } else {
                        lead.setsOther(otherTextArea.getText().toString());
                    }
                } else {
                    lead.setsOther(null);
                }

                switch (stInstance) {
                    case 'N':
                    case 'U': {
                        Lead lead1;
                        try {
                            lead1 = (Lead) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("leads/addLead", RequestHandler.writeJSON(lead)), Lead.class);
                            if (lead1 != null) {
                                RequestHandler.post("emailList/addEmail", RequestHandler.writeJSON(new EmailList(email, userId, 0, 0, lead1.getLeadsId())));
                                RequestHandler.post("phoneList/addPhone", RequestHandler.writeJSON(new PhoneList(phone, userId, 0, 0, lead1.getLeadsId())));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

//                        sql.insertLead(lead);
                        break;
                    }//                        sql.updateLead(lead);
                    default: {
                        break;
                    }
                }

            } else {
                return;
            }
        }
    }
}
