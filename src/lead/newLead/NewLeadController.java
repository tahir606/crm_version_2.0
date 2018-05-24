package lead.newLead;

import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lead.view.LeadViewController;
import objects.Lead;
import objects.ProductProperty;
import product.ProductDashController;
import product.view.ProductViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewLeadController implements Initializable {
    
    @FXML
    private JFXTextField txt_fname;
    @FXML
    private JFXTextField txt_lname;
    @FXML
    private JFXTextField txt_company;
    @FXML
    private JFXTextField txt_website;
    @FXML
    private JFXTextField txt_city;
    @FXML
    private JFXTextField txt_country;
    @FXML
    private JFXTextField txt_email;
    @FXML
    private JFXTextField txt_mobile;
    @FXML
    private TextArea txt_note;
    @FXML
    private JFXButton btn_back;
    @FXML
    private JFXButton btn_save;
    @FXML
    private Label txt_heading;
    
    public static char stInstance;
    
    private Lead lead;
    private mySqlConn sql;
    
    public static int noOfFields = 2;   //No of emails and phones
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Leads"));
        btn_back.setOnAction(event -> {
            try {
                ProductDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("lead/view/lead_view.fxml")));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        if (stInstance == 'N') {
            btn_save.setText("Add");
            lead = new Lead();
//            lead.setCode(sql.getNewProductCode());
            txt_heading.setText("New Lead");
        } else if (stInstance == 'U') {
            btn_save.setText("Update");
            lead = LeadViewController.staticLead;
            populateDetails(lead);
            txt_heading.setText("Update Lead");
        }
    }
    
    private void populateDetails(Lead lead) {
    
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
                
                lead.setFirstName(fname);
                lead.setLastName(lname);
                lead.setCompany(company);
                lead.setCity(city);
                lead.setCountry(country);
                lead.setDesc(desc);
                lead.setEmail(email);
                lead.setPhone(phone);
                lead.setWebsite(website);
                
                switch (stInstance) {
                    case 'N': {
                        sql.insertLead(lead);
                        break;
                    }
                    case 'U': {
                        sql.updateLead(lead);
                        break;
                    }
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
