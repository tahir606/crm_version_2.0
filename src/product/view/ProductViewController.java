package product.view;

import ApiHandler.RequestHandler;
import JCode.mysql.mySqlConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import objects.Product;
import product.ProductDashController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @FXML
    private TableView<Product> table_product;
    @FXML
    private TableColumn<Product, String> col_name;
    @FXML
    private TableColumn<Product, String> col_price;
    @FXML
    private TableColumn<Product, String> col_started_on;
    @FXML
    private TableColumn<Product, String> col_status;
    @FXML
    private TableColumn<Product, String> col_type;
    @FXML
    private AnchorPane toolbar_products;
    @FXML
    private Label txt_no;

//    List<ProductProperty> selectedProducts;

    public static Product staticProduct;

    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();

        toolbar_products.setVisible(false);

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_started_on.setCellValueFactory(new PropertyValueFactory<>("started"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        List<Product> productList=new ArrayList<>();
        try {
            productList= RequestHandler.listRequestHandler(RequestHandler.run("product/getAllProducts"), Product.class);
            table_product.getItems().setAll(productList);
//            table_product.getItems().setAll(sql.getAllLeads(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        table_product.getItems().setAll(sql.getAllProducts(null));
        
        table_product.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    staticProduct = row.getItem();
                    try {
                        ProductDashController.main_paneF.setCenter(
                                FXMLLoader.load(
                                        getClass().getClassLoader().getResource
                                                ("product/details/product_details.fxml")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

    }
}
