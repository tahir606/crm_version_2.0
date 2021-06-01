package product.newProduct;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
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
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.*;
import product.ProductDashController;
import product.view.ProductViewController;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @FXML
    private JFXButton btn_add_module;
    @FXML
    private VBox vbox_modules;

    public static char stInstance;

    private Product product;
    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();
        product = new Product();
        vbox_modules.setSpacing(10);

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

        image = new Image(this.getClass().getResourceAsStream("/res/img/plus-green.png"));
        btn_add_module.setGraphic(new ImageView(image));
        btn_add_module.setAlignment(Pos.CENTER_LEFT);
        btn_add_module.setTooltip(new Tooltip("Add Module"));
        btn_add_module.setOnAction(event -> add_module(new ProductModule()));

        if (stInstance == 'N') {
//            product = new Product();
//            product.setCode(sql.getNewProductCode());
            txt_heading.setText("New Product");
            btn_save.setText("Add");
            btn_add_module.fire();
        } else if (stInstance == 'U') {
            btn_save.setText("Update");
//            product = ProductViewController.staticProduct;
            populateDetails(ProductViewController.staticProduct);
            populateModules(ProductViewController.staticProduct);
            txt_heading.setText("Update Product");
        }
    }

    private void add_module(ProductModule productModule) {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        JFXTextField pmId = new JFXTextField();
        if (productModule.getPmID() != 0)
            pmId.setText(String.valueOf(productModule.getPmID()));
        pmId.setAccessibleText("pm");
        pmId.setPromptText("pm");
        pmId.setMaxWidth(10);
        pmId.setMaxHeight(20);
        pmId.getStyleClass().add("blackText");
        pmId.setDisable(true);
        JFXTextField psId = new JFXTextField();
        if (productModule.getPsID() != null)
            psId.setText(String.valueOf(productModule.getPsID()));
        psId.setAccessibleText("ps");
        psId.setPromptText("ps");
        psId.setMaxWidth(10);
        psId.setMaxHeight(20);
        psId.getStyleClass().add("blackText");
        psId.setDisable(true);
        JFXTextField txt_name = new JFXTextField();
        if (productModule.getName() != null)
            txt_name.setText(productModule.getName());
        txt_name.setAccessibleText("name");
        txt_name.setPromptText("Name");
        txt_name.setMaxWidth(100);
        txt_name.setMaxHeight(20);
        txt_name.getStyleClass().add("blackText");

        TextArea txt_desc = new TextArea();
        if (productModule.getDescription() != null)
            txt_desc.setText(productModule.getDescription());
        txt_desc.setAccessibleText("desc");
        txt_desc.setPromptText("Description");
        txt_desc.setMaxWidth(220);
        txt_desc.setMaxHeight(20);
        txt_desc.setWrapText(true);
        txt_desc.getStyleClass().add("blackText");

        JFXButton btn_delete = new JFXButton("X");
        btn_delete.setAccessibleText("delete");
        btn_delete.setOnAction(event1 -> vbox_modules.getChildren().remove(hBox));

        hBox.getChildren().addAll(psId, pmId, txt_name, txt_desc, btn_delete);

        vbox_modules.getChildren().add(hBox);
    }

    List<ProductModule> modules = new ArrayList<>();

    private boolean insertModules() {
//        List<ProductModule> modules = new ArrayList<>();
        Stage stage = (Stage) btn_add_module.getScene().getWindow();
        for (Node node : vbox_modules.getChildren()) {
            ProductModule module = new ProductModule();
            HBox box = (HBox) node;
            for (Node n : box.getChildren()) {
                if (n.getAccessibleText().equals("pm")) {
                    String pmId = ((JFXTextField) n).getText().toString();
                    if (pmId.equals("")) {

                    } else {
                        module.setPmID(Integer.parseInt(pmId));
                    }

                }
                if (n.getAccessibleText().equals("ps")) {
                    String psId = ((JFXTextField) n).getText().toString();
                    if (psId.equals("")) {

                    } else {
                        module.setPsID(Integer.parseInt(psId));
                    }
                }
                if (n.getAccessibleText().equals("name")) {
                    String name = ((JFXTextField) n).getText().toString();
                    if (name.equals("")) {
                        Toast.makeText(stage, "Name cannot be empty");
                        return false;
                    }
                    module.setName(name);
                } else if (n.getAccessibleText().equals("desc")) {
                    String desc = ((TextArea) n).getText().toString();
                    module.setDescription(desc);
                    if (module.equals("")) {
                        Toast.makeText(stage, "Description cannot be empty");
                        return false;
                    }
                }
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            module.setCreatedOn(String.valueOf(formatter.format(date)));
            module.setCreatedBy(FileHelper.ReadUserApiDetails().getUserCode());
//            module.setPsID(product.getPsID());
//            module.setPmID(product.getPsID());
            modules.add(module);
        }
//        product.setPdProductModule(modules);
        return true;
    }

    private void populateDetails(Product newValue) {

        if (newValue == null)
            return;
        else if (newValue.getName().equals(" + Create New"))
            txt_name.setText("");
        else
            txt_name.setText(newValue.getName());
        product.setPsID(newValue.getPsID());
        txt_name.setText(newValue.getName());
        txt_price.setText(String.valueOf(newValue.getPrice()));
        txt_desc.setText(newValue.getDescription());
        if (newValue.getStarted() != null)
            started_date.setValue(CommonTasks.createLocalDateForFilter(newValue.getStarted()));
        else
            started_date.setValue(null);

    }

    private void populateModules(Product staticProduct) {
        for (ProductModule module : staticProduct.getPdProductModule()) {
            add_module(module);
        }
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

            if (!insertModules())
                return;

            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, msg,
                    ButtonType.YES, ButtonType.NO);
            alert2.showAndWait();

            if (alert2.getResult() == ButtonType.YES) {

                product.setName(name);
                try {
                    product.setPrice(Integer.parseInt(price));
                } catch (NumberFormatException e) {
                    System.out.println("Number Format Exception");
                    product.setPrice('\0');
                }
                product.setDescription(desc);
                if (started.equals("null"))
                    product.setStarted(null);
                else
                    product.setStarted(started);
//                product.setFreeze(false);
                product.setCreatedBy(FileHelper.ReadUserApiDetails().getUserCode());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                product.setCreatedOn(String.valueOf(formatter.format(date)));
                switch (stInstance) {
                    case 'N':
                    case 'U': {
                        Product product2;
                        try {
                            product2 = (Product) RequestHandler.objectRequestHandler(RequestHandler.postOfReturnResponse("product/addProduct", RequestHandler.writeJSON(product)), Product.class);
                            if (product2 != null) {
                                if (modules.isEmpty()) {
                                    RequestHandler.basicRequestHandler(RequestHandler.run("productModule/delete/" + product2.getPsID())).equals("OK");
                                    return;
                                }
                                boolean status = true;
                                for (ProductModule productModule : modules) {
                                    productModule.setPsID(product2.getPsID());
                                    if (productModule.getPmID() == 0) {
                                        if (status) {
                                            RequestHandler.basicRequestHandler(RequestHandler.run("productModule/delete/" + product2.getPsID()));
                                            status = false;
                                        }
                                        RequestHandler.post("productModule/addProductModule", RequestHandler.writeJSON(productModule));
                                    } else {

                                        RequestHandler.post("productModule/updateProductModule", RequestHandler.writeJSON(productModule));
                                    }
                                    Toast.makeText((Stage) btn_save.getScene().getWindow(), "Successfully Done");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
