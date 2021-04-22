package Home;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.mysql.mySqlConn;
import dashboard.dController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.ProductModule;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    
    @FXML
    private AnchorPane main_anchor;
    @FXML
    private HBox hbox_options;
    @FXML
    private Label txt_fname;
    @FXML
    private Label txt_solved;
    @FXML
    private Label txt_unsolved;
    @FXML
    private Label txt_unlocked;
    @FXML
    private VBox vbox_modules;
    
    private Users user;
    private FileHelper fHelper;
    private mySqlConn sql;
    private ArrayList<ProductModule> lockedModules;
    
    public HomeController() {
    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        main_anchor.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        
        fHelper = new FileHelper();
//        sql = new mySqlConn();
        
//        user = fHelper.ReadUserDetails();
        user =FileHelper.ReadUserApiDetails();
        

//        user = sql.getNoOfSolvedEmails(user);
        
//        lockedModules = sql.getLockedModules();
        
        txt_fname.setText(user.getFullName());
        try {
            txt_solved.setText(String.valueOf(Integer.parseInt(RequestHandler.run("ticket/CountOfSolvedOrLockedEmails?status=" + 3 + "&userCode=" + user.getUserCode()).body().string())));
            txt_unlocked.setText(String.valueOf(Integer.parseInt(RequestHandler.run("ticket/CountOfUnLockedEmails").body().string())));
            txt_unsolved.setText(String.valueOf(Integer.parseInt(RequestHandler.run("ticket/CountOfUnSolvedEmails").body().string())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        populatLockedModules();

        dController.img_load.setVisible(false);
    }
    
    private void populatLockedModules() {
        vbox_modules.setSpacing(8);
        
        for (ProductModule module : lockedModules) {
            HBox hbox = new HBox();
            hbox.setSpacing(5);
            
            Label moduleLabel = new Label(module.getName()),
                    productLabel = new Label(module.getProductName()),
                    lockedByLabel = new Label(module.getLockedByName()),
                    lockedTimeLabel = new Label(CommonTasks.getTimeFormatted(module.getLockedTime()));
            
            moduleLabel.setMinWidth(80);
            moduleLabel.setMaxWidth(80);
            productLabel.setMinWidth(80);
            productLabel.setMaxWidth(80);
            lockedByLabel.setMinWidth(80);
            lockedByLabel.setMaxWidth(80);
            lockedTimeLabel.setMinWidth(140);
            lockedTimeLabel.setMaxWidth(140);
            
            hbox.getChildren().addAll(moduleLabel, productLabel, lockedByLabel, lockedTimeLabel);
            hbox.getStyleClass().add("moduleDetails");
            
            vbox_modules.getChildren().add(hbox);
        }
    }
}
