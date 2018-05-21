package product.details;

import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.ProductModule;

import java.net.URL;
import java.util.ResourceBundle;

public class UnlockDialogController implements Initializable {
    
    ProductModule module = ProductDetailsController.selectedModule;
    
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_unlock;
    
    private mySqlConn sql;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        
        btn_unlock.setDisable(true);
        
        txt_desc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= 0)
                btn_unlock.setDisable(true);
            else
                btn_unlock.setDisable(false);
        });
        
        btn_unlock.setOnAction(event -> {
            String desc = txt_desc.getText().toString();
            sql.unlockModule(module, desc);
            
            ProductDetailsController.init(this.getClass().getResource("unlock_dialog.fxml"));
            Stage stage = (Stage) btn_unlock.getScene().getWindow();
            stage.close();
        });
    }
}
