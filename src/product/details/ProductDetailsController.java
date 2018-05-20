package product.details;

import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.ProductModule;
import objects.ProductProperty;
import product.ProductDashController;
import product.newProduct.NewProductController;
import product.view.ProductViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProductDetailsController implements Initializable {
    
    @FXML
    private Label txt_pname;
    @FXML
    private Label txt_price;
    @FXML
    private Label txt_startedOn;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_back;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private VBox vbox_modules;
    
    private ProductProperty product;
    public static ProductModule selectedModule;
    
    private mySqlConn sql;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        sql = new mySqlConn();
        
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
        
        btn_edit.setOnAction(event -> {
            NewProductController.stInstance = 'U';
            try {
                ProductDashController.main_paneF.setCenter(
                        FXMLLoader.load(
                                getClass().getClassLoader().getResource("product/newProduct/new_product.fxml")));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        product = ProductViewController.staticProduct;
        txt_pname.setText(product.getName());
        txt_price.setText(String.valueOf(product.getPrice()));
        txt_desc.setText(product.getDesc());
        txt_startedOn.setText(product.getFormattedDate());
        
        vbox_modules.setSpacing(10);
        init();
        
    }
    
    private void init() {
        ProductProperty prod = sql.getProductModuleStates(product);
        populateModules(prod);
    }
    
    private void populateModules(ProductProperty product) {
        vbox_modules.getChildren().clear();
        for (ProductModule module : product.getProductModules()) {
            HBox box = new HBox();
            box.setSpacing(10);
            box.setPadding(new Insets(5, 5, 5, 5));
            Label name = new Label();
            name.setText(module.getName());
            name.setMinWidth(60);
            
            TextArea desc = new TextArea();
            desc.setMaxWidth(200);
            desc.setMaxHeight(60);
            desc.setWrapText(true);
            desc.setEditable(false);
            desc.setText(module.getDesc());
            
            JFXButton btnState = new JFXButton();
            if (module.getState() == '\0')
                module.setState(0);
            
            switch (module.getState()) {
                case 0: {
                    btnState.getStyleClass().removeAll();
                    btnState.setStyle("-fx-background-color: #feffe0;");
                    break;
                }
                case 1: {
                    btnState.getStyleClass().removeAll();
                    btnState.getStyleClass().add("unlockedEmail");
                    break;
                }
            }
            
            btnState.setText((module.getState() == 0 ? "LOCK" : "UNLOCK"));
            btnState.setMinWidth(60);
            btnState.setOnAction(event -> {
                selectedModule = module;
                switch (module.getState()) {
                    case 0: {
                        sql.lockModule(module);
                        break;
                    }
                    case 1: {
                        CommonTasks.inflateDialog("Confirmation?", this.getClass().getResource("unlock_dialog.fxml"));
                        break;
                    }
                }
                init();
            });
            
            box.getChildren().addAll(name, desc, btnState);
            box.getStyleClass().add("moduleDetails");
            vbox_modules.getChildren().add(box);
        }
    }
}
