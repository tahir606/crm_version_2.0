package product.newProduct;

import JCode.Toast;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.ClientProperty;
import objects.ProductProperty;

import java.net.URL;
import java.util.ResourceBundle;

public class NewProductController implements Initializable {

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

    public static char stInstance;

    private ProductProperty product;
    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        product = new ProductProperty();
    }

    public void saveChanges(ActionEvent actionEvent) {
        String name = txt_name.getText(),
                price = txt_price.getText(),
                desc = txt_desc.getText(),
                started = String.valueOf(started_date.getValue());

        if (name.equals("") || desc.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required Fields Are Empty");
            return;
        } else {
            String msg = "";
            switch (stInstance) {
                case 'N': {
                    msg = "Are you sure you want to add Product?";
                    break;
                }
                case 'U': {
                    msg = "Are you sure you want to update Product?";
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

                product.setName(name);
                product.setPrice(Integer.parseInt(price));
                product.setDesc(desc);
                if (started.equals("null"))
                    product.setStartedtimeStmp(null);
                else
                    product.setStartedtimeStmp(started);
                product.setFreeze(false);

                switch (stInstance) {
                    case 'N': {
                        sql.insertProduct(product);
                        break;
                    }
                    case 'U': {
//                        sql.updateP(product);
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
