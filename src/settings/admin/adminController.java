package settings.admin;

import ApiHandler.RequestHandler;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dashboard.dController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.RightChart;
import objects.RightsList;
import objects.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    @FXML
    private JFXTextField txt_uname, txt_fname, txt_email, txt_password;
    @FXML
    private Label label_message;
    @FXML
    private VBox vbox_rights;
    @FXML
    private JFXCheckBox check_freeze;
    @FXML
    private JFXCheckBox check_email;
    @FXML
    private JFXComboBox<Users> combo_users;
    @FXML
    private JFXButton btn_save, btn_archive, btn_log_out_user;

    private mySqlConn sql;
    private FileHelper fileHelper;
    private ImageView imm_load = dController.img_load;

    private List<Users> usersList = null;

    private List<RightsList> rightsList = new ArrayList<>();
    private Users userSel;

    private int nUcode = 0;
    private int UcodeisEmail = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        sql = new mySqlConn();

        init();

        vbox_rights.setSpacing(10);
        vbox_rights.setPadding(new Insets(5, 10, 5, 2));


        btn_log_out_user.setVisible(false);
        btn_log_out_user.setOnAction(event -> {
            if (fileHelper == null)
                fileHelper = new FileHelper();

            if (userSel.getUserCode() == FileHelper.ReadUserApiDetails().getUserCode()) {
                Toast.makeText((Stage) btn_save.getScene().getWindow(), "You can't log yourself out.");
                return;
            }
            try {
                RequestHandler.objectRequestHandler(RequestHandler.run("users/updateUser/" + userSel.getUserCode() + "?isLog=" + 0), Users.class); // log out user
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "User will be logged out the next time he/she opens KiT");
            populateDetails(userSel);

        });
    }

    private void init() {
        combo_users.getItems().clear();
        try {
            try {
                usersList = RequestHandler.listRequestHandler(RequestHandler.run("users/getALlUsers"), Users.class);
            } catch (IOException e) {
                e.printStackTrace();
            }

            rightsList = RequestHandler.listRequestHandler(RequestHandler.run("rights_list/getRightList"), RightsList.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        nUcode = usersList.get(usersList.size() - 1).getUserCode() + 1;    //Get Ucode for new User
        UcodeisEmail = getIsEmail((ArrayList<Users>) usersList);        //Check if this user is receiving email
        Users u = new Users();
        u.setUserCode(nUcode);
        u.setFullName(" + Create New");
        u.setUserRight("New");
        combo_users.getItems().add(u);

        combo_users.getItems().addAll(usersList);

        comboListener();
        combo_users.getSelectionModel().select(0);
    }

    private void comboListener() {
        combo_users.valueProperty().addListener((observable, oldValue, newValue) -> {
            populateDetails(newValue);
            userSel = newValue;
        });
    }

    public static boolean checkIfUserIsLoggedInn(Users userLoggedIn) {
        if (userLoggedIn == null) {
            return false;
        }
        Users users = null;
        try {
            users = (Users) RequestHandler.objectRequestHandler(RequestHandler.run("users/getUser/" + userLoggedIn.getUserCode()), Users.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (users == null) {
            return false;
        }
        if (users.getIsLog() == 1)
            return true;
        return false;
    }

    private void populateDetails(Users newValue) {
        if (newValue == null) {
            return;
        }
        boolean isLoggedIn = checkIfUserIsLoggedInn(newValue);
        if (isLoggedIn) {
            label_message.setVisible(true);
            btn_log_out_user.setVisible(true);
        } else {
            label_message.setVisible(false);
            btn_log_out_user.setVisible(false);
        }

        vbox_rights.getChildren().clear();

        JFXCheckBox f = new JFXCheckBox("Award All Rights");

        if (newValue == null)
            return;

        if (newValue.getUserRight().equals("Admin")) {
            f.setSelected(true);
        }

        vbox_rights.getChildren().add(f);

        List<RightChart> rightCharts = new ArrayList<>();
        try {
            rightCharts = RequestHandler.listRequestHandler(RequestHandler.run("rights_chart/getChartList/" + newValue.getUserCode()), RightChart.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (RightsList ur : rightsList) {
            JFXCheckBox cb = new JFXCheckBox(ur.getName());
            if (newValue.getUserRight().equals("Admin")) {
                cb.setSelected(false);
            } else {
                for (RightChart u : rightCharts) {
                    if (u.getRightsCode() == ur.getRightsCode()) {
                        cb.setSelected(true);
                    }
                }
            }
            vbox_rights.getChildren().add(cb);
        }
        if (newValue.getUserName() == null) {
            txt_uname.setText("");
        } else {
            txt_uname.setText(newValue.getUserName());
        }

        txt_fname.setText(newValue.getFullName());
        if (newValue.getFullName().equals(" + Create New")) {
            txt_fname.setText("");
        }
        if (newValue.getEmail() == null) {
            txt_email.setText("");
        } else {
            txt_email.setText(newValue.getEmail());
        }

        if (newValue.getPassword() == null) {
            txt_password.setText("");
        } else {
            txt_password.setText(newValue.getPassword());
        }


        if (UcodeisEmail == 0) {
            check_email.setDisable(false);
        } else {
            if (UcodeisEmail != newValue.getUserCode()) {
                check_email.setDisable(true);
            } else {
                check_email.setDisable(false);
            }
        }
        boolean isEmail, isFreeze;
        if (newValue.getIsEmail() == 0) {
            isEmail = false;
        } else {
            isEmail = true;
        }
        if (newValue.getFreeze() == 0) {
            isFreeze = false;
        } else {
            isFreeze = true;
        }
        check_email.setSelected(isEmail);
        check_freeze.setSelected(isFreeze);

    }


    private int getIsEmail(ArrayList<Users> users) {
        for (Users u : users) {
            if (u.getIsEmail() == 1) {
                return u.getUserCode();
            }
        }
        return 0;
    }

    @FXML
    void saveChanges(ActionEvent event) {

        System.out.println("Updating user: " + userSel.getUserCode());

        String uname, fname, email, password;
        int isEmail, isFreeze;


        uname = txt_uname.getText();
        fname = txt_fname.getText();
        email = txt_email.getText();
        password = txt_password.getText();
        if (check_email.isSelected()) {
            isEmail = 1;
        } else {
            isEmail = 0;
        }
        if (check_freeze.isSelected()) {
            isFreeze = 1;
        } else {
            isFreeze = 0;
        }
        System.out.println(password);
        if (uname.equals("") || fname.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Required fields are empty");
            return;
        }
        ArrayList<Integer> rights = new ArrayList<>();

        int index = -1;
        for (Node node : vbox_rights.getChildren()) {
            JFXCheckBox jf = (JFXCheckBox) node;
            index++;
            if (index == 0) {
                if (jf.isSelected()) {
                    userSel.setUserRight("Admin");
                    break;
                } else {
                    userSel.setUserRight("Not Admin");
                }
                continue;
            }
            if (jf.isSelected())
                rights.add(index);
        }

        if (rights.size() == 0 && !userSel.getUserRight().equals("Admin")) {
            Toast.makeText((Stage) btn_save.getScene().getWindow(), "You need to allot at least one right.");
            return;
        }

        userSel.setUserName(uname);
        userSel.setFullName(fname);
        userSel.setEmail(email);
        userSel.setPassword(password);
        userSel.setIsEmail(isEmail);
        userSel.setFreeze(isFreeze);

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to save changes?",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();


        if (alert2.getResult() == ButtonType.YES) {
            if (userSel.getUserCode() == nUcode) {
                try {

                    RequestHandler.post("users/addUser", RequestHandler.writeJSON(userSel)).close();                //New
                    RequestHandler.post("rights_chart/insertChartList/" + userSel.getUserCode(), RequestHandler.writeJSONIntegerList(rights)).close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    RequestHandler.post("users/addUser", RequestHandler.writeJSON(userSel)).close();                //       odl
                    RequestHandler.post("rights_chart/insertChartList/" + userSel.getUserCode(), RequestHandler.writeJSONIntegerList(rights)).close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            Toast.makeText((Stage) btn_save.getScene().getWindow(), "Your changes will be made after you re-login");

            init();
        } else {
            return;
        }

    }

    public void deleteUser(ActionEvent actionEvent) {
        System.out.println("Delete User : " + userSel.getUserName());
        if (userSel.getUserCode() == nUcode) {
            return;
        }

        Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete User? Freezing it " +
                "may be a better choice.",
                ButtonType.YES, ButtonType.NO);
        alert2.showAndWait();

        if (alert2.getResult() == ButtonType.YES) {
            try {

                RequestHandler.run("users/delete/" + userSel.getUserCode()).close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            init();
        } else {
            return;
        }

    }

}
