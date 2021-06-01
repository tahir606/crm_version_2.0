package product.details;

import ApiHandler.RequestHandler;
import Home.HomeSplitController;
import JCode.CommonTasks;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.ModuleLocking;
import objects.ProductModule;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UnlockDialogController implements Initializable {

    ProductModule module;
ModuleLocking moduleLocking;
    @FXML
    private TextArea txt_desc;
    @FXML
    private JFXButton btn_unlock;

    private mySqlConn sql;

    public static char fromPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();

        switch (fromPane) {
            case 'P': {
                module = ProductDetailsController.selectedModule;
                moduleLocking=module.getPmModuleLockingList().get(0);
                break;
            }
            case 'H': {
                module = HomeSplitController.sModule;
                moduleLocking=module.getPmModuleLockingList().get(0);
                break;
            }
        }

        txt_desc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() <= 0)
                btn_unlock.setDisable(true);
            else
                btn_unlock.setDisable(false);
        });

        btn_unlock.setOnAction(event -> {
            String desc = txt_desc.getText().toString();
            moduleLocking.setDescription(desc);
            moduleLocking.setUnLockedTime(CommonTasks.getCurrentTimeStamp());
            String responseMessage = "";
            try {
                responseMessage = RequestHandler.basicRequestHandler(RequestHandler.postOfReturnResponse("moduleLocking/updateModuleLocking", RequestHandler.writeJSON(moduleLocking)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (fromPane) {
                case 'P': {
                    ProductDetailsController.init(this.getClass().getResource("unlock_dialog.fxml"));
                    break;
                }
                case 'H': {
                    HomeSplitController.setUpPreviousPanels();
                    break;
                }
            }

            Stage stage = (Stage) btn_unlock.getScene().getWindow();
            stage.close();
        });
    }
}
