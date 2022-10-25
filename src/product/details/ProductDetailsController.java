package product.details;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import gui.NotesConstructor;
import gui.TasksConstructor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.ModuleLocking;
import objects.Product;
import objects.ProductModule;
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
    private VBox notes_list;
    @FXML
    private VBox vbox_main;
    @FXML
    private VBox open_activities_list;
    @FXML
    private VBox closed_activities_list;
    @FXML
    private VBox vbox_modules;
    private static VBox vbox_modulesS;

    private static Product product;
    public static ProductModule selectedModule;
    private static mySqlConn sql;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vbox_modulesS = vbox_modules;
//        sql = new mySqlConn();

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

        populateDetails();
//        init(this.getClass().getResource("unlock_dialog.fxml"));
        populateModules(this.getClass().getResource("unlock_dialog.fxml"));
    }

    public static void init(URL path) {
//        ProductProperty prod = sql.getProductModuleStates(product);
//        populateModules(prod, path);

    }

    private void populateDetails() {
        product = ProductViewController.staticProduct;
        txt_pname.setText(product.getName());
        txt_price.setText(String.valueOf(product.getPrice()));
        txt_desc.setText(product.getDescription());
        txt_startedOn.setText(product.getStarted());

        vbox_modules.setSpacing(10);

        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(600);
        new NotesConstructor(tabPane, product).generalConstructor(4);
        new TasksConstructor(tabPane, product).generalConstructor(4);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        AnchorPane.setBottomAnchor(tabPane, 0.0);
        AnchorPane.setTopAnchor(tabPane, 0.0);
        AnchorPane.setRightAnchor(tabPane, 0.0);
        AnchorPane.setLeftAnchor(tabPane, 0.0);

        vbox_main.getChildren().add(tabPane);
    }

    static int state = 0;

    private static void populateModules(URL path) {
        vbox_modulesS.getChildren().clear();
        for (ProductModule module : product.getPdProductModule()) {
            HBox box = new HBox();
            box.setSpacing(10);
            box.setPadding(new Insets(5, 5, 5, 5));
            Label name = new Label();
            name.setText(module.getName());
            name.setMinWidth(60);
            name.setMaxWidth(60);

            TextArea desc = new TextArea();
            desc.setMaxWidth(200);
            desc.setMaxHeight(60);
            desc.setWrapText(true);
            desc.setEditable(false);
            desc.setText(module.getDescription());
            desc.getStyleClass().add("scroll-view");
            ModuleLocking moduleLocking1 = new ModuleLocking();
            JFXButton btnState = new JFXButton();

            if (module.getPmModuleLockingList().isEmpty()) {
                moduleLocking1.setState(0);
            } else if (module.getPmModuleLockingList().get(0).getCreatedBy() != FileHelper.ReadUserApiDetails().getUserCode()) {
                moduleLocking1.setState(2);
            } else if (module.getPmModuleLockingList().get(0).getState() == 0) {
                moduleLocking1.setState(0);
            } else if (module.getPmModuleLockingList().get(0).getState() == 1) {
                moduleLocking1.setState(1);

            }

            switch (moduleLocking1.getState()) {
                case 0: {
                    btnState.getStyleClass().removeAll();
                    btnState.setStyle("-fx-background-color: #feffe0;");
                    btnState.setDisable(false);
                    break;
                }
                case 1: {
                    btnState.getStyleClass().removeAll();
                    btnState.getStyleClass().add("unlockedEmail");
                    btnState.setDisable(false);
                    break;
                }
                case 2: {   //When module is not locked by you
                    btnState.getStyleClass().removeAll();
                    btnState.getStyleClass().add("unlockedEmail");
                    btnState.setDisable(true);
                    break;
                }
            }

            btnState.setText((state == 0 ? "LOCK" : "UNLOCK"));
            btnState.setMinWidth(60);
            btnState.setOnAction(event -> {
                selectedModule = module;
                switch (moduleLocking1.getState()) {
                    case 0: {
                        ModuleLocking moduleLocking = new ModuleLocking(module.getPmID(), FileHelper.ReadUserApiDetails().getUserCode(), CommonTasks.getCurrentTimeStamp(), 1, module.getDescription());
                        String responseMessage = "";
                        try {
                            responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("moduleLocking/addModuleLocking", RequestHandler.writeJSON(moduleLocking)));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        init(path);
                        break;
                    }
                    case 1: {
                        UnlockDialogController.fromPane = 'P';
                        CommonTasks.inflateDialog("Confirmation?", path);
                        break;
                    }
                    case 2: {
                        break;
                    }
                }
            });

            box.getChildren().addAll(name, desc, btnState);
            box.getStyleClass().add("moduleDetails");
            vbox_modulesS.getChildren().add(box);
        }
    }
}
