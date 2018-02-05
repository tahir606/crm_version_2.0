package Home;

import JCode.fileHelper;
import JCode.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import objects.Users;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    AnchorPane main_anchor;

    @FXML
    HBox hbox_options;

    @FXML
    private Label txt_fname;

    @FXML
    private Label txt_solved;

    private Users user;
    private fileHelper fHelper;
    private mySqlConn sql;

    public HomeController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        main_anchor.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        fHelper = new fileHelper();
        sql = new mySqlConn();
        user = fHelper.ReadUserDetails();
        user = sql.getUserDetails(user);

        txt_fname.setText(user.getFNAME());
        txt_solved.setText(String.valueOf(user.getSolved()));


//        hbox_options.setAlignment(Pos.BASELINE_RIGHT);
//        JFXButton button = new JFXButton("Change Password");
//        button.setButtonType(JFXButton.ButtonType.RAISED);
//
//        hbox_options.getChildren().add(button);
    }
}
