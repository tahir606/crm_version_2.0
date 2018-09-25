package documents;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import objects.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class DocumentsDashController implements Initializable {

    @FXML
    private ListView<Document> list_documents;
    @FXML
    private JFXButton add_document_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        add_document_btn.setOnAction(event -> {

        });
    }
}
