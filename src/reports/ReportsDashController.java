package reports;

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

    }

    private void populateReports() {
        vbox_reports.getChildren().addAll(createLabel(""));
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        return label;
    }

}
