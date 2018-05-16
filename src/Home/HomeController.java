package Home;

import JCode.fileHelper;
import JCode.mysql.mySqlConn;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    @FXML
    private Label txt_unsolved;
    @FXML
    private Label txt_unlocked;

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
        user = sql.getNoOfSolvedEmails(user);

        txt_fname.setText(user.getFNAME());
        txt_solved.setText(String.valueOf(user.getSolved()));
        txt_unlocked.setText(String.valueOf(sql.getNoOfUnlocked()));
        txt_unsolved.setText(String.valueOf(sql.getNoOfUnsolved()));


    }
}
