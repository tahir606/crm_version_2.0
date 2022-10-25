package gui;

import ApiHandler.RequestHandler;
import JCode.CommonTasks;
import JCode.FileHelper;
import JCode.Toast;
import JCode.mysql.mySqlConn;
import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotesConstructor {

    TextArea noteTxt;
    JFXButton btnAdd;

    TabPane tabPane;
    Tab tab;
    VBox notes_list, main_box;
    mySqlConn sql;
    Email email;
    //    ContactProperty contact;
    Contact contact;
    Client client;
    Lead lead;
    Product product;
    private FileHelper fHelper = new FileHelper();


    public NotesConstructor(TabPane tabPane, Contact contact) {
        this.tabPane = tabPane;
        notes_list = new VBox();
        tab = new Tab("Notes");
        this.contact = contact;
    }

    public NotesConstructor(TabPane tabPane, Client client) {
        this.tabPane = tabPane;
        notes_list = new VBox();
        tab = new Tab("Notes");
        this.client = client;
    }

    public NotesConstructor(TabPane tabPane, Lead lead) {
        this.tabPane = tabPane;
        notes_list = new VBox();
        tab = new Tab("Notes");
        this.lead = lead;
    }

    public NotesConstructor(TabPane tabPane, Product product) {
        this.tabPane = tabPane;
        notes_list = new VBox();
        tab = new Tab("Notes");
        this.product = product;
    }

    public NotesConstructor(TabPane tabPane, Email email) {
        this.tabPane = tabPane;
        notes_list = new VBox();
        tab = new Tab("Notes");
        this.email = email;

    }

    public void constructingContactNotes(int choice) {
        notes_list.getChildren().clear();
        if (contact != null) {
            List<Note> noteList = new ArrayList<>();
            try {
                noteList = RequestHandler.listRequestHandler(RequestHandler.run("note/getNotesByContactId/" + contact.getContactID()), Note.class);
                if (noteList == null) {
                    noteList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Note noteOld : noteList)
                constructNote(noteOld, choice);

            createNew(choice);
        }

    }

    public void constructingClientNotes(int choice) {

        notes_list.getChildren().clear();
        if (client != null) {
            List<Note> noteList = new ArrayList<>();
            try {
                noteList = RequestHandler.listRequestHandler(RequestHandler.run("note/getNotesByClientId/" + client.getClientID()), Note.class);
                if (noteList == null) {
                    noteList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Note noteOld : noteList)
                constructNote(noteOld, choice);

            createNew(choice);
        }

    }

    public void constructingLeadNotes(int choice) {
        notes_list.getChildren().clear();
        if (lead != null) {
            List<Note> noteList = new ArrayList<>();
            try {
                noteList = RequestHandler.listRequestHandler(RequestHandler.run("note/getNotesByLeadId/" + lead.getLeadsId()), Note.class);
                if (noteList == null) {
                    noteList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Note note : noteList)
                constructNote(note, choice);

            createNew(choice);
        }

    }

    public void constructingProductNotes(int choice) {
        notes_list.getChildren().clear();
        if (product != null) {
            List<Note> noteList = new ArrayList<>();
            try {
                noteList = RequestHandler.listRequestHandler(RequestHandler.run("note/getNotesByProductId/" + product.getPsID()), Note.class);
                if (noteList == null) {
                    noteList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Note note : noteList)
                constructNote(note, choice);

            createNew(choice);
        }

    }

    public void constructingEmailNotes(int choice) {

        notes_list.getChildren().clear();
        if (email != null) {
            List<Note> noteList = new ArrayList<>();
            try {
                noteList = RequestHandler.listRequestHandler(RequestHandler.run("note/getNotesByEmailId/" + email.getCode()), Note.class);
                if (noteList == null) {
                    noteList = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Note noteOld : noteList)
                constructNote(noteOld, choice);
            createNew(choice);
        }
    }

    private void constructNote(Note note, int choice) {
        //The First Part
        HBox body = new HBox();
        body.getChildren().clear();
        body.setSpacing(5);
        TextArea area = new TextArea(note.getText());
        if (choice == 5) {
            area.setMaxWidth(210);
            area.setMinWidth(210);
        }
        area.setWrapText(true);
        area.setEditable(false);
        area.setMinHeight(50);
        area.setMaxHeight(50);
        area.setStyle("-fx-background-color: #fcfcfc;" +
                "-fx-background: transparent;");
        body.getChildren().add(area);
        //The Second Part
        HBox details = new HBox();
        details.getChildren().clear();
        details.setSpacing(10);
        details.setMinHeight(25);
        details.setPadding(new Insets(3));

        if (note == null) {
            return;
        }

        Label createdBy = new Label(note.getUsers().getFullName()),
                createdOn = new Label(CommonTasks.convertFormatWithOutTimeZone(note.getCreatedOn()));


        if (choice == 5) {      //        if Emails Clicked then set the width of label
            createdOn.setMinWidth(100);
            createdBy.setMinWidth(100);
        } else {                //         else remain same for all
            createdOn.setMinWidth(150);
            createdBy.setMinWidth(280);
        }
        JFXButton options = new JFXButton();
        Image image = new Image(getClass().getResourceAsStream("/res/img/options.png"));
        options.setGraphic(new ImageView(image));
        details.getChildren().addAll(createdOn, createdBy, options);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem delItem = new MenuItem("Delete"),
                editItem = new MenuItem("Edit");
        editItem.setOnAction(t -> {
//            if (note.getCreatedBy() == fHelper.ReadUserDetails().getUCODE()) {
            if (note.getCreatedBy() == FileHelper.ReadUserApiDetails().getUserCode()) {
                area.setEditable(true);
                area.setStyle("-fx-background: #fcfcfc;");
                area.requestFocus();
                JFXButton saveBtn = new JFXButton("Save"),
                        cancelBtn = new JFXButton("Cancel");
                if (choice == 5) {  //   if Emails Clicked then set the width of save and cancel button
                    saveBtn.setMinWidth(30);
                    cancelBtn.setMinWidth(30);
                } else {  //   remain same
                    saveBtn.setMinWidth(60);
                    cancelBtn.setMinWidth(60);
                }
                saveBtn.setButtonType(JFXButton.ButtonType.RAISED);
                saveBtn.setStyle("-fx-background-color: #e8f0ff;");
                cancelBtn.setButtonType(JFXButton.ButtonType.RAISED);
                cancelBtn.setStyle("-fx-background-color: #e8f0ff;");

                saveBtn.setOnAction(event -> {
                    note.setText(area.getText().toString());
                    switch (choice) {
                        case 1:
                        case 2:
                        case 5:
                        case 3:
                        case 4:
                            try {
                                RequestHandler.run("note/updateNote?noteCode=" + note.getNoteCode() + "&noteText=" + note.getText()).close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    generalConstructor(choice);
                });
                cancelBtn.setOnAction(event -> {
                    body.getChildren().remove(saveBtn);
                    body.getChildren().remove(cancelBtn);
                    area.setEditable(false);
                    area.setStyle("-fx-background-color: #fcfcfc;" +
                            "-fx-background: transparent;");
                });

                body.getChildren().addAll(saveBtn, cancelBtn);
            } else {
                Toast.makeText((Stage) area.getScene().getWindow(), "Note cannot be edit");
                return;
            }
        });
        delItem.setOnAction(t -> {
            if (note.getCreatedBy() == FileHelper.ReadUserApiDetails().getUserCode()) {
                switch (choice) {
                    case 1:
                    case 2:
                    case 5:
                    case 3:
                    case 4:
                        try {
                            RequestHandler.run("note/deleteNote/" + note.getNoteCode()).close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                generalConstructor(choice);
            } else {
                Toast.makeText((Stage) area.getScene().getWindow(), "Note cannot be deleted");
                return;
            }
        });
        contextMenu.getItems().addAll(editItem, delItem);
        options.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent me) -> {
            if (me.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(options, me.getScreenX(), me.getScreenY());
            } else {
                contextMenu.hide();
            }
        });

        VBox box = new VBox();
        box.getChildren().clear();
//            box.setStyle("-fx-border-color: #033300;");
        box.getChildren().addAll(body, details);
        notes_list.setSpacing(10);
        notes_list.getChildren().add(0, box);
    }


    public void createNew(int choice) {
        HBox addNew = new HBox();
        addNew.getChildren().clear();
        addNew.setSpacing(5);
        noteTxt = new TextArea();
        noteTxt.setMinHeight(50);
        noteTxt.setMaxHeight(50);
        if (choice == 5) {  //      if Emails Clicked then set the width of text area
            noteTxt.setMinWidth(260.0);
            noteTxt.setMaxWidth(260.0);
        }
        noteTxt.setPromptText("New Note");
        btnAdd = new JFXButton();
        btnAdd.setText("Add");
        btnAdd.setButtonType(JFXButton.ButtonType.RAISED);
        btnAdd.setStyle("-fx-background-color: #efefef;");
        addNew.getChildren().addAll(noteTxt, btnAdd);
        notes_list.getChildren().add(addNew);

        btnAdd.setOnAction(event -> {

            String note = noteTxt.getText();
            if (note.trim().equals("")) {
                Toast.makeText((Stage) noteTxt.getScene().getWindow(), "Note cannot be empty");
                return;
            } else {
                switch (choice) {
                    case 1:
                    case 5:
                    case 2:
                    case 3:
                    case 4:
                        try {
                            RequestHandler.post("note/addNote", RequestHandler.writeJSON(newNote(note))).close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    //                        sql.addNote(note, email);
                }
                generalConstructor(choice);
            }
        });
    }

    public Note newNote(String text) {
        Users users = FileHelper.ReadUserApiDetails();
        Note note = new Note();
        note.setText(text);
        if (email != null) {
            note.setEmailId(email.getCode());
        } else if (client != null) {
            note.setClientID(client.getClientID());
        } else if (contact != null) {
            note.setContactID(contact.getContactID());
        } else if (lead != null) {
            note.setLeadsId(lead.getLeadsId());
        } else if (product != null) {
            note.setPsID(product.getPsID());
        }
        note.setCreatedBy(users.getUserCode());

        return note;
    }

    public void generalConstructor(int choice) {
        switch (choice) {
            case 1: { //Contact
                constructingContactNotes(choice);
                break;
            }
            case 2: { //Clients
                constructingClientNotes(choice);
                break;
            }
            case 3: { //Clients
                constructingLeadNotes(choice);
                break;
            }
            case 4: {
                constructingProductNotes(choice);
                break;
            }
            case 5: {

                constructingEmailNotes(choice);
                break;
            }

        }
        notes_list.setPrefHeight(500);
        ScrollPane sp = new ScrollPane(notes_list);
        sp.getStyleClass().add("scroll-view");
        tab.setContent(sp);
        tabPane.getTabs().add(tab);
    }


}
