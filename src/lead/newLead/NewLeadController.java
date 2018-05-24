package lead.newLead;

import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private JFXTextField txt_name;
    @FXML
    private JFXTextField txt_price;
    @FXML
    private JFXComboBox combo_status;
    @FXML
    private JFXComboBox combo_type;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_save;
    @FXML
    private Label txt_heading;
    @FXML
    private JFXDatePicker started_date;
    @FXML
    private JFXButton btn_back;
    
    public static char stInstance;
    
    private Lead lead;
    private mySqlConn sql;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        lead = new Lead();
        
        Image image = new Image(this.getClass().getResourceAsStream("/res/img/left-arrow.png"));
        btn_back.setGraphic(new ImageView(image));
        btn_back.setAlignment(Pos.CENTER_LEFT);
        btn_back.setTooltip(new Tooltip("Back to Products"));
        btn_back.setOnAction(event -> {
            try {
                ProductDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("product/view/product_view.fxml")));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        if (stInstance == 'N') {
            btn_save.setText("Add");
            lead = new Lead();
//            lead.setCode(sql.getNewProductCode());
        } else if (stInstance == 'U') {
            btn_save.setText("Update");
            lead = LeadViewController.staticLead;
            populateDetails(lead);
            txt_heading.setText("Update Product");
        }
    }
    
    private void populateDetails(Lead lead) {
    
    }
    
}
