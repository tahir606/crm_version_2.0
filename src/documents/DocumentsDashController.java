package documents;

import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.Document;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DocumentsDashController implements Initializable {

    @FXML
    private ListView<Document> list_documents;
    @FXML
    private JFXButton add_document_btn;

    private mySqlConn sql;

    private List<Document> allDocs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        sql = new mySqlConn();
        init();
    }

    public void init() {

        allDocs = sql.getAllDocuments();

        ObservableList<Document> dataObj = FXCollections.observableArrayList(allDocs);
        list_documents.setItems(dataObj);

        list_documents.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(list_documents.getSelectionModel().getSelectedItem().getFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        Toast.makeText((Stage) add_document_btn.getScene().getWindow(), ex.getLocalizedMessage());
                    } catch (NullPointerException e) {
                        return;
                    }
                }
            }
        });

        //Right click menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteFile = new MenuItem("Delete File");
        deleteFile.setOnAction(t -> {
            sql.deleteDocument(list_documents.getSelectionModel().getSelectedItem());
            init();
        });
        contextMenu.getItems().add(deleteFile);

        list_documents.setContextMenu(contextMenu);
        list_documents.setOnContextMenuRequested(event -> event.consume());

        add_document_btn.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Attach File");
            File file = chooser.showOpenDialog(new Stage());
            if (file == null)
                return;
            try {
                sql.insertDocument(new Document(file));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText((Stage) add_document_btn.getScene().getWindow(),e.getLocalizedMessage());
            }

            init();
        });
    }
}
