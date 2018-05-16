package product.newProduct;

import JCode.CommonTasks;
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
import javafx.stage.Stage;
import objects.ProductProperty;
import product.ProductDashController;
import product.view.ProductViewController;

import java.io.IOException;
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
    @FXML
    private JFXButton btn_back;

    public static char stInstance;

    private ProductProperty product;
    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();
        product = new ProductProperty();

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
            product = new ProductProperty();
            txt_heading.setText("New Product");
            btn_save.setText("Add");
        } else if (stInstance == 'U') {
            btn_save.setText("Update");
            product = ProductViewController.staticProduct;
            populateDetails(product);
            txt_heading.setText("Update Product");
        }
    }

//    private void init() {
//
//        product = new ProductProperty();
//        product.setName(" + Create New");
//        product.setPrice('\0');
//        product.setDesc("");
//        product.set
//
//    }

    private void populateDetails(ProductProperty newValue) {
        if (newValue == null)
            return;
        else if (newValue.getName().equals(" + Create New"))
            txt_name.setText("");
        else
            txt_name.setText(newValue.getName());

        txt_name.setText(product.getName());
        txt_price.setText(String.valueOf(product.getPrice()));
        txt_desc.setText(product.getDesc());
        if (newValue.getStartedtimeStmp() != null)
            started_date.setValue(CommonTasks.createLocalDate(newValue.getStartedtimeStmp()));
        else
            started_date.setValue(null);

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
                        sql.updateProduct(product);
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
