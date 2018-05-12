package product.view;

import JCode.mySqlConn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import objects.ProductProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductViewController implements Initializable {

    @FXML
    private TableView<ProductProperty> table_product;
    @FXML
    private TableColumn<ProductProperty, String> col_name;
    @FXML
    private TableColumn<ProductProperty, String> col_price;
    @FXML
    private TableColumn<ProductProperty, String> col_started_on;
    @FXML
    private TableColumn<ProductProperty, String> col_status;
    @FXML
    private TableColumn<ProductProperty, String> col_type;
    @FXML
    private AnchorPane toolbar_products;
    @FXML
    private Label txt_no;

//    List<ProductProperty> selectedProducts;

    public static ProductProperty staticProduct;

    private mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sql = new mySqlConn();

        toolbar_products.setVisible(false);

        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_started_on.setCellValueFactory(new PropertyValueFactory<>("startedtimeStmp"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));

        table_product.getItems().setAll(sql.getAllContactsProperty(null));
//
//        table_contact.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
//            selectedContacts = table_contact.getSelectionModel().getSelectedItems();
//
//            if (selectedContacts == null || selectedContacts.size() == 0) {
//                toolbar_contacts.setVisible(false);
//                return;
//            } else {
//                toolbar_contacts.setVisible(true);
//                txt_no.setText(String.valueOf(selectedContacts.size()));
//            }
//
//        });
//
//        table_contact.setRowFactory(tv -> {
//            TableRow<ContactProperty> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    staticContact = row.getItem();
//                    try {
//                        dashBaseController.main_paneF.setCenter(
//                                FXMLLoader.load(
//                                        getClass().getClassLoader().getResource
//                                                ("client/dash/contactView/contactDetails/contactDetails.fxml")));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            return row;
//        });

    }
}
