package JCode;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class trayHelper {

    public static TrayIcon trayIcon;

    java.awt.Image image = null;
    public static SystemTray tray;

    public trayHelper() {
    }

    public void createIcon(Stage stage) {
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/icon.png")));
    }

    public void createTrayIcon(Stage stage) {

        if (tray != null) {
            tray.remove(trayIcon);
        }

        image = new ImageIcon(this.getClass().getResource("/res/img/icon-tray.png")).getImage();

        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
        }

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/res/img/icon.png")));


        stage.setOnCloseRequest(event -> hide(stage));

        final ActionListener closeListener = e -> {
            Platform.exit();
            System.exit(0);
            tray.remove(trayIcon);
        };

        ActionListener showListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stage.show();
                    }
                });
            }
        };

        PopupMenu popup = new PopupMenu();

        MenuItem showItem = new MenuItem("Open");
        showItem.addActionListener(showListener);
        popup.add(showItem);

        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(closeListener);
        popup.add(closeItem);

        trayIcon = new TrayIcon(image, "BITS-CRM", popup);
        trayIcon.setImageAutoSize(true);

        trayIcon.addActionListener(showListener);

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();

                } else {
                    System.exit(0);
                }
            }
        });
    }

    public void removeTray() {
        tray.remove(trayIcon);
    }

    public void displayNotification(String caption, String msg) throws NullPointerException {
        trayIcon.displayMessage(caption, msg, TrayIcon.MessageType.INFO);
    }

}
