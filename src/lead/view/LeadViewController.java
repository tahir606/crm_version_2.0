package lead.view;

import JCode.mysql.mySqlConn;
import javafx.fxml.Initializable;
import objects.Lead;

import java.net.URL;
import java.util.ResourceBundle;

public class LeadViewController implements Initializable {
    
    public static Lead staticLead;
    private mySqlConn sql;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
