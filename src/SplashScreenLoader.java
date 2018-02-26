import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SplashScreenLoader extends Preloader {

    private Stage splashScreen;

    @Override
    public void start(Stage stage) throws Exception {
        splashScreen = stage;
        splashScreen.setScene(createScene());
        splashScreen.show();
    }

    public Scene createScene() {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 200);
        return scene;
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification) {
//        if (notification == StateChangeNotification.Type.BEFORE_START) {
//            splashScreen.hide();
//        }
    }

//    @Override
//    public void handleStateChangeNotification(StateChangeNotification evt) {
//        if (evt.getType() == StateChangeNotification.Type.BEFORE_INIT) {
//            splashScreen.hide();
//        }
//    }

//    ProgressBar bar;
//    Stage stage;
//
//    private Scene createPreloaderScene() {
//        bar = new ProgressBar();
//        BorderPane p = new BorderPane();
//        p.setCenter(bar);
//        return new Scene(p, 300, 150);
//    }
//
//    public void start(Stage stage) throws Exception {
//        this.stage = stage;
//        stage.setScene(createPreloaderScene());
//        stage.show();
//    }
//
//    @Override
//    public void handleProgressNotification(ProgressNotification pn) {
//        bar.setProgress(pn.getProgress());
//    }

//    @Override
//    public void handleStateChangeNotification(StateChangeNotification evt) {
//        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
//            splashScreen.hide();
//        }
//    }

}