package reports;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsDashController implements Initializable {

    @FXML
    private VBox vbox_reports;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateReports();


    }

    private void populateReports() {
        JFXButton rep = new JFXButton();
        createButton("Tickets Solved by User");
        rep.setOnAction(event -> {

        });

        vbox_reports.getChildren().addAll(rep);
    }

    private void createButton(String text) {
        JFXButton button = new JFXButton(text);
        button.setStyle("-fx-font-size: 9pt;" +
                "-fx-text-fill: #000000;" +
                "-fx-underline: true;");
        return;
    }



}
