package settings.network;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import JCode.Toast;
import JCode.trayHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objects.Network;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class networkSetController implements Initializable {
    @FXML
    private JFXComboBox<String> selectIp;


    @FXML
    private JFXButton btn_save;

    public static boolean fromMain;     //To check if network setting is from main or not

    FileHelper fHelper;
    trayHelper tHelper;

    //    private final static String DB_NAME = "bits_crm",
//            USER = "root",
//            PASS = "tahir123!@#";
    String ipSelected = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fHelper = new FileHelper();
        tHelper = new trayHelper();
        selectIp.getItems().addAll("Static Ip", "Local Ip");

        selectIp.valueProperty().addListener(((observable, oldValue, newValue) -> {

            ipSelected = newValue;

        }));
        Network network = FileHelper.getNetworkDetails();
        if (network != null) {
            if (network.getHost().equals("103.245.193.156")){
                selectIp.getSelectionModel().select(0);
            }else{
                selectIp.getSelectionModel().select(1);
            }
        }
    }

    public void saveChanges(ActionEvent actionEvent) throws IOException {

        if (selectIp.getSelectionModel().getSelectedItem() == null) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Fields cannot be null");
            return;
        }
        String ip = "";
        if (selectIp.getSelectionModel().getSelectedItem().equals("Static Ip")) {
            ip = "103.245.193.156";
        } else if (selectIp.getSelectionModel().getSelectedItem().equals("Local Ip")) {
            ip = "192.168.100.210";
        }
        String response = RequestHandler.basicRequestHandler(RequestHandler.checkIp("users/getALlUsers",ip));

        if (response.equals("OK")) {

            fHelper.WriteNetwork(new Network(ip));

            if (fromMain) {

                Stage stage = (Stage) btn_save.getScene().getWindow();
                stage.close();

                Stage primaryStage = new Stage();

                if (FileHelper.ReadUserApiDetails() == null) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../../login/login.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.setTitle("Login- BITS-CRM");
                    primaryStage.setScene(new Scene(root, 900, 400));
                    ;
                    tHelper.createIcon(primaryStage);
                    primaryStage.show();
                } else {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../../dashboard/dashboard.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    primaryStage.setTitle("Dashboard- BITS-CRM");
                    primaryStage.setScene(new Scene(root, 1200, 500));
                    tHelper.createTrayIcon(primaryStage);
                    tHelper.createIcon(primaryStage);
                    primaryStage.show();
                }
            } else {
                fHelper.WriteNetwork(new Network(ip));
            }
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Saved Successfully");
        } else
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Not able to connect to server!");
    }
}
